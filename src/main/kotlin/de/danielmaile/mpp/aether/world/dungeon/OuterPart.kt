/*
 * This file is part of MPP.
 * Copyright (c) 2022 by it's authors. All rights reserved.
 * MPP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MPP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MPP.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.danielmaile.mpp.aether.world.dungeon

import de.danielmaile.mpp.util.converter.LocationConverter
import de.danielmaile.mpp.util.converter.VectorConverter
import jakarta.persistence.*
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector
import java.util.*

@Entity
class OuterPart(
    @Enumerated(EnumType.STRING)
    val outerType: OuterPartType,

    @Column(nullable = false)
    @Convert(converter = VectorConverter::class)
    val relativePosition: Vector,

    @Column(nullable = false)
    @Convert(converter = LocationConverter::class)
    val worldLocation: Location,

    @Transient
    private val random: Random,

    @Transient
    private val endPartChance: Double,

    // outer part has 25 inner parts (or 0)
    @Column(nullable = true)
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var innerParts: List<InnerPart>? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null
) {

    init {
        innerParts = generateInnerParts(random, endPartChance)
    }

    private fun generateInnerParts(random: Random, endPartChance: Double): ArrayList<InnerPart>? {
        if (outerType.prefabType != null) return null

        // generate middle cross depending on outer connections
        val parts = ArrayList<InnerPart>()
        val outerConnectDirections = outerType.getConnection().getConnectDirections()
        val newParts = getCross(random, endPartChance, outerConnectDirections)

        // add parts until no more paths lead to the outside of the dungeon
        while (newParts.isNotEmpty()) {
            parts.addAll(newParts)
            newParts.clear()
            for (innerPart in parts) {
                for (connectDirection in innerPart.innerType.getConnection().getConnectDirections()) {
                    val newPartPos = innerPart.relativePosition.clone().add(connectDirection.direction)
                    if (getPartAt(parts, newPartPos) != null || getPartAt(newParts, newPartPos) != null) continue

                    // get neighbours and check for ConnectionState
                    val eastNeighbour = getNeighbour(newPartPos, BlockFace.EAST, parts, newParts)
                    val westNeighbour = getNeighbour(newPartPos, BlockFace.WEST, parts, newParts)
                    val southNeighbour = getNeighbour(newPartPos, BlockFace.SOUTH, parts, newParts)
                    val northNeighbour = getNeighbour(newPartPos, BlockFace.NORTH, parts, newParts)
                    val newPartCon = Connection(
                        eastNeighbour?.innerType?.getConnection()?.getConnectionState(BlockFace.WEST)
                            ?: Connection.ConnectionState.DONT_CARE,
                        westNeighbour?.innerType?.getConnection()?.getConnectionState(BlockFace.EAST)
                            ?: Connection.ConnectionState.DONT_CARE,
                        southNeighbour?.innerType?.getConnection()?.getConnectionState(BlockFace.NORTH)
                            ?: Connection.ConnectionState.DONT_CARE,
                        northNeighbour?.innerType?.getConnection()?.getConnectionState(BlockFace.SOUTH)
                            ?: Connection.ConnectionState.DONT_CARE
                    )
                    newParts.add(getRandomInnerPart(random, endPartChance, newPartCon, newPartPos))
                }
            }
        }

        return parts
    }

    /**
     * Tries to find neighbour part in two lists.
     * @return the neighbour parts or null is none is found.
     */
    private fun getNeighbour(
        location: Vector,
        blockFace: BlockFace,
        partList1: ArrayList<InnerPart>,
        partList2: ArrayList<InnerPart>
    ): InnerPart? {
        val neighbour = getPartAt(partList1, location.clone().add(blockFace.direction))
        return neighbour ?: getPartAt(partList2, location.clone().add(blockFace.direction))
    }

    private fun getPartAt(partList: ArrayList<InnerPart>, relativePosition: Vector): InnerPart? {
        // if relative position is out of bounds, return closed part
        if (relativePosition.x < 0 || relativePosition.x > 4 || relativePosition.z < 0 || relativePosition.z > 4) {
            return InnerPart(InnerPartType.CLOSED, relativePosition, this)
        }

        // else try to find part in list. If no part is found return null
        return partList.firstOrNull { relativePosition == it.relativePosition }
    }

    private fun getCross(
        random: Random,
        endPartChance: Double,
        outerConnectDirections: ArrayList<BlockFace>
    ): ArrayList<InnerPart> {
        val parts = ArrayList<InnerPart>()

        // center part (2,2)
        parts.add(InnerPart(InnerPartType.EWSN, Vector(2, 0, 2), this))
        // east center part (4, 2)
        parts.add(
            getRandomInnerPart(
                random, endPartChance,
                Connection(
                    if (outerConnectDirections.contains(BlockFace.EAST)) Connection.ConnectionState.CONNECTED else Connection.ConnectionState.CLOSED,
                    Connection.ConnectionState.CONNECTED,
                    Connection.ConnectionState.DONT_CARE,
                    Connection.ConnectionState.DONT_CARE
                ),
                Vector(4, 0, 2)
            )
        )
        // west center part (0, 2)
        parts.add(
            getRandomInnerPart(
                random, endPartChance,
                Connection(
                    Connection.ConnectionState.CONNECTED,
                    if (outerConnectDirections.contains(BlockFace.WEST)) Connection.ConnectionState.CONNECTED else Connection.ConnectionState.CLOSED,
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE
                ),
                Vector(0, 0, 2)
            )
        )
        // south center part (2, 4)
        parts.add(
            getRandomInnerPart(
                random, endPartChance,
                Connection(
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE,
                    if (outerConnectDirections.contains(BlockFace.SOUTH)) Connection.ConnectionState.CONNECTED else Connection.ConnectionState.CLOSED,
                    Connection.ConnectionState.CONNECTED
                ),
                Vector(2, 0, 4)
            )
        )
        // north center part (2, 0)
        parts.add(
            getRandomInnerPart(
                random, endPartChance,
                Connection(
                    Connection.ConnectionState.DONT_CARE,
                    Connection.ConnectionState.DONT_CARE,
                    Connection.ConnectionState.CONNECTED,
                    if (outerConnectDirections.contains(BlockFace.NORTH)) Connection.ConnectionState.CONNECTED else Connection.ConnectionState.CLOSED
                ),
                Vector(2, 0, 0)
            )
        )
        // east inner center part (3, 2)
        parts.add(
            getRandomInnerPart(
                random, endPartChance,
                Connection(
                    Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED,
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE
                ),
                Vector(3, 0, 2)
            )
        )
        // west inner center part (1, 2)
        parts.add(
            getRandomInnerPart(
                random, endPartChance,
                Connection(
                    Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED,
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE
                ),
                Vector(1, 0, 2)
            )
        )
        // south inner center part (2, 3)
        parts.add(
            getRandomInnerPart(
                random, endPartChance,
                Connection(
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE,
                    Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED
                ),
                Vector(2, 0, 3)
            )
        )
        // north inner center part (2, 1)
        parts.add(
            getRandomInnerPart(
                random, endPartChance,
                Connection(
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE,
                    Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED
                ),
                Vector(2, 0, 1)
            )
        )

        return parts
    }

    private fun getRandomInnerPart(
        random: Random,
        endPartChance: Double,
        connection: Connection,
        relativePosition: Vector
    ): InnerPart {
        val con = if (random.nextFloat() < endPartChance) {
            // set all don't care connections to closed to get end part
            connection.setDontCareToClosed()
        } else {
            connection
        }

        val validTypes = InnerPartType.values().filter { con.isValid(it.getConnection()) }.toList()
        return InnerPart(validTypes[random.nextInt(validTypes.size)], relativePosition, this)
    }

    fun hasInnerParts(): Boolean {
        return innerParts != null
    }
}