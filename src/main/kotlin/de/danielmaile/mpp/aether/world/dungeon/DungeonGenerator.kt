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

import de.danielmaile.mpp.aether.world.Prefab
import de.danielmaile.mpp.aether.world.PrefabType
import de.danielmaile.mpp.aether.world.dungeon.loot.DungeonChest
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.logInfo
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector
import java.util.*
import kotlin.math.floor

class DungeonGenerator(private val random: Random) {

    fun generateDungeon(location: Location, endPartChance: Float, partCap: Int) {
        Bukkit.getScheduler().runTaskAsynchronously(inst(), Runnable {
            val dungeon = Dungeon(generateOuterParts(toDungeonGridLocation(location), endPartChance, partCap), random)

            // if no valid monument location is found dungeon doesn't generate
            if (dungeon.monumentLocation == null) {
                return@Runnable
            }

            // check for other dungeons to avoid overlapping
            for (part in dungeon.outerParts) {
                for (otherDungeon in inst().worldManager.objectManager.getDungeons()) {
                    for (otherOuterPart in otherDungeon.outerParts) {
                        if (part.worldLocation == otherOuterPart.worldLocation) return@Runnable
                    }
                }
            }

            // add dungeon to object manager
            Bukkit.getScheduler().runTask(inst(), Runnable { inst().worldManager.objectManager.addNewDungeon(dungeon) })

            // instantiate prefabs
            Prefab(PrefabType.DUNGEON_MONUMENT, dungeon.monumentLocation!!, true).instantiate()
            for (part in dungeon.outerParts) {
                if (part.hasInnerParts()) {
                    for (innerPart in part.innerParts!!) {
                        Prefab(innerPart.innerType.prefabType, innerPart.worldLocation, false).instantiate()
                    }
                } else {
                    Prefab(part.outerType.prefabType!!, part.worldLocation, false).instantiate()
                }
            }

            // generate chests
            generateChests(dungeon)

            // print log to console
            logInfo("Generated new dungeon with " + dungeon.getSize() + " inner parts at monument location: " + dungeon.monumentLocation + ")")
        })
    }

    private fun generateChests(dungeon: Dungeon) {
        for (outerPart in dungeon.outerParts) {
            val world = outerPart.worldLocation.world
            val chestChecks = random.nextInt(
                inst().configManager.minDungeonChestChecks,
                inst().configManager.maxDungeonChestChecks + 1
            )
            for (i in 0 until chestChecks) {
                val chestLocation = outerPart.worldLocation.clone()
                    .add(random.nextInt(80).toDouble(), 0.0, random.nextInt(80).toDouble())
                val chest = world.getBlockAt(chestLocation)

                // check for air at chest location and floor below
                if (chest.type != Material.AIR) continue
                if (chest.getRelative(BlockFace.DOWN).type == Material.AIR) continue

                // check for block to place against
                val facing: BlockFace = if (chest.getRelative(BlockFace.EAST).type != Material.AIR) {
                    BlockFace.WEST
                } else if (chest.getRelative(BlockFace.WEST).type != Material.AIR) {
                    BlockFace.EAST
                } else if (chest.getRelative(BlockFace.SOUTH).type != Material.AIR) {
                    BlockFace.NORTH
                } else if (chest.getRelative(BlockFace.NORTH).type != Material.AIR) {
                    BlockFace.SOUTH
                } else continue

                Bukkit.getScheduler().runTask(inst(), Runnable {
                    DungeonChest(random).instantiate(chestLocation, facing)
                })
            }
        }
    }

    private fun toDungeonGridLocation(location: Location): Location {
        val dungeonLocation = location.clone()
        dungeonLocation.x = floor(dungeonLocation.x / 80) * 80
        dungeonLocation.z = floor(dungeonLocation.z / 80) * 80
        return dungeonLocation
    }

    private fun generateOuterParts(origin: Location, endPartChance: Float, partCap: Int): ArrayList<OuterPart> {
        val outerParts = ArrayList<OuterPart>()

        // first part EWSN
        val newParts = ArrayList<OuterPart>()
        newParts.add(
            OuterPart(OuterPartType.EWSN, Vector(0, 0, 0), origin.clone(), random, endPartChance.toDouble())
        )

        // add parts until no more paths lead to the outside of the dungeon
        while (newParts.isNotEmpty()) {
            outerParts.addAll(newParts)
            newParts.clear()

            for (outerPart in outerParts) {
                val newEndPartChance = if (outerParts.size + newParts.size > partCap) {
                    1f
                } else {
                    endPartChance
                }

                for (connectDirection in outerPart.outerType.getConnection().getConnectDirections()) {
                    val newPartPos = outerPart.relativePosition.clone().add(connectDirection.direction)
                    if (getPartAt(outerParts, newPartPos) != null || getPartAt(newParts, newPartPos) != null) continue

                    // get neighbours and check for ConnectionState
                    val eastNeighbour = getNeighbour(newPartPos, BlockFace.EAST, outerParts)
                    val westNeighbour = getNeighbour(newPartPos, BlockFace.WEST, outerParts)
                    val southNeighbour = getNeighbour(newPartPos, BlockFace.SOUTH, outerParts)
                    val northNeighbour = getNeighbour(newPartPos, BlockFace.NORTH, outerParts)
                    val newPartCon = Connection(
                        eastNeighbour?.outerType?.getConnection()?.getConnectionState(BlockFace.WEST)
                            ?: Connection.ConnectionState.DONT_CARE,
                        westNeighbour?.outerType?.getConnection()?.getConnectionState(BlockFace.EAST)
                            ?: Connection.ConnectionState.DONT_CARE,
                        southNeighbour?.outerType?.getConnection()?.getConnectionState(BlockFace.NORTH)
                            ?: Connection.ConnectionState.DONT_CARE,
                        northNeighbour?.outerType?.getConnection()?.getConnectionState(BlockFace.SOUTH)
                            ?: Connection.ConnectionState.DONT_CARE
                    )
                    newParts.add(getRandomOuterPart(newEndPartChance, newPartCon, newPartPos, origin))
                }
            }
        }
        return outerParts
    }

    // try to find neighbour part in list. If none is found return null
    private fun getNeighbour(location: Vector, blockFace: BlockFace, partList: ArrayList<OuterPart>): OuterPart? {
        return getPartAt(partList, location.clone().add(blockFace.direction))
    }

    private fun getPartAt(partList: ArrayList<OuterPart>, relativePosition: Vector): OuterPart? {
        return partList.firstOrNull { relativePosition == it.relativePosition }
    }

    /**
     * @param connection       the connections the new part should have
     * @param relativePosition the position of the new part
     * @param endPartChance    chance for the new Part to be an end part
     */
    private fun getRandomOuterPart(
        endPartChance: Float,
        connection: Connection,
        relativePosition: Vector,
        origin: Location
    ): OuterPart {
        val con: Connection = if (random.nextFloat() < endPartChance) {
            // set all don't care connections to closed to get end part
            connection.setDontCareToClosed()
        } else {
            connection
        }

        val validTypes = OuterPartType.values().filter { con.isValid(it.getConnection()) }.toList()
        return OuterPart(
            validTypes[random.nextInt(validTypes.size)], relativePosition,
            origin.clone().add(relativePosition.x * 16 * 5, 0.0, relativePosition.z * 16 * 5), random,
            endPartChance.toDouble()
        )

    }
}