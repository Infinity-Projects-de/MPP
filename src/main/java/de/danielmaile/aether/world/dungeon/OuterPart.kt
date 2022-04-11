package de.danielmaile.aether.world.dungeon

import org.bukkit.Location
import org.bukkit.util.Vector
import java.util.Random

class OuterPart(val outerType: OuterPartType, val relativePosition: Vector, val worldLocation: Location, random: Random, endPartChance: Double) {

    //Outer part has 25 inner parts (or 0)
    val innerParts: ArrayList<InnerPart>?

    init {
        innerParts = generateInnerParts(random, endPartChance)
    }

    private fun generateInnerParts(random: Random, endPartChance: Double): ArrayList<InnerPart>? {
        if (outerType.prefabType != null) return null

        //Generate middle cross depending on outer connections
        val parts = ArrayList<InnerPart>()
        val outerConnectDirections = outerType.getConnection().getConnectDirections()
        val newParts = getCross(random, endPartChance, outerConnectDirections)

        //Add parts until no more paths lead to the outside of the dungeon
        while (newParts.isNotEmpty()) {
            parts.addAll(newParts)
            newParts.clear()
            for (innerPart in parts) {
                for (connectDirection in innerPart.innerType.getConnection().getConnectDirections()) {
                    val newPartPos = innerPart.relativePosition.clone().add(connectDirection.relativePos)
                    if (getPartAt(parts, newPartPos) != null || getPartAt(newParts, newPartPos) != null) continue

                    //Get neighbours and check for ConnectionState
                    val eastNeighbour = getNeighbour(newPartPos, Direction.EAST, parts, newParts)
                    val westNeighbour = getNeighbour(newPartPos, Direction.WEST, parts, newParts)
                    val southNeighbour = getNeighbour(newPartPos, Direction.SOUTH, parts, newParts)
                    val northNeighbour = getNeighbour(newPartPos, Direction.NORTH, parts, newParts)
                    val newPartCon = Connection(
                        eastNeighbour?.innerType?.getConnection()?.getConnectionState(Direction.WEST)
                            ?: Connection.ConnectionState.DONT_CARE,
                        westNeighbour?.innerType?.getConnection()?.getConnectionState(Direction.EAST)
                            ?: Connection.ConnectionState.DONT_CARE,
                        southNeighbour?.innerType?.getConnection()?.getConnectionState(Direction.NORTH)
                            ?: Connection.ConnectionState.DONT_CARE,
                        northNeighbour?.innerType?.getConnection()?.getConnectionState(Direction.SOUTH)
                            ?: Connection.ConnectionState.DONT_CARE
                    )
                    newParts.add(getRandomInnerPart(random, endPartChance, newPartCon, newPartPos))
                }
            }
        }

        return parts
    }

    //Try to find neighbour part in two lists. If none is found return null
    private fun getNeighbour(location: Vector, direction: Direction, partList1: ArrayList<InnerPart>, partList2: ArrayList<InnerPart>): InnerPart? {
        val neighbour = getPartAt(partList1, location.clone().add(direction.relativePos))
        return neighbour ?: getPartAt(partList2, location.clone().add(direction.relativePos))
    }

    private fun getPartAt(partList: ArrayList<InnerPart>, relativePosition: Vector): InnerPart? {
        //If relative position is out of bounds, return closed part
        if (relativePosition.x < 0 || relativePosition.x > 4 || relativePosition.z < 0 || relativePosition.z > 4) {
            return InnerPart(InnerPartType.CLOSED, relativePosition, this)
        }

        //Else try to find part in list. If no part is found return null
        return partList.firstOrNull { relativePosition == it.relativePosition }
    }

    private fun getCross(random: Random, endPartChance: Double, outerConnectDirections: ArrayList<Direction>): ArrayList<InnerPart> {
        val parts = ArrayList<InnerPart>()

        //Center part (2,2)
        parts.add(InnerPart(InnerPartType.EWSN, Vector(2, 0, 2), this))
        //East center part (4, 2)
        parts.add(
            getRandomInnerPart(random, endPartChance,
                Connection(
                    if (outerConnectDirections.contains(Direction.EAST)) Connection.ConnectionState.CONNECTED else Connection.ConnectionState.CLOSED,
                    Connection.ConnectionState.CONNECTED,
                    Connection.ConnectionState.DONT_CARE,
                    Connection.ConnectionState.DONT_CARE),
                Vector(4, 0, 2)
            )
        )
        //West center part (0, 2)
        parts.add(
            getRandomInnerPart(random, endPartChance,
                Connection(
                    Connection.ConnectionState.CONNECTED,
                    if (outerConnectDirections.contains(Direction.WEST)) Connection.ConnectionState.CONNECTED else Connection.ConnectionState.CLOSED,
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE),
                Vector(0, 0, 2)
            )
        )
        //South center part (2, 4)
        parts.add(
            getRandomInnerPart(random, endPartChance,
                Connection(
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE,
                    if (outerConnectDirections.contains(Direction.SOUTH)) Connection.ConnectionState.CONNECTED else Connection.ConnectionState.CLOSED,
                    Connection.ConnectionState.CONNECTED),
                Vector(2, 0, 4)
            )
        )
        //North center part (2, 0)
        parts.add(
            getRandomInnerPart(random, endPartChance,
                Connection(
                    Connection.ConnectionState.DONT_CARE,
                    Connection.ConnectionState.DONT_CARE,
                    Connection.ConnectionState.CONNECTED,
                    if (outerConnectDirections.contains(Direction.NORTH)) Connection.ConnectionState.CONNECTED else Connection.ConnectionState.CLOSED),
                Vector(2, 0, 0)
            )
        )
        //East inner center part (3, 2)
        parts.add(
            getRandomInnerPart(random, endPartChance,
                Connection(
                    Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED,
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE),
                Vector(3, 0, 2)
            )
        )
        //West inner center part (1, 2)
        parts.add(
            getRandomInnerPart(random, endPartChance,
                Connection(
                    Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED,
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE),
                Vector(1, 0, 2)
            )
        )
        //South inner center part (2, 3)
        parts.add(
            getRandomInnerPart(random, endPartChance,
                Connection(
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE,
                    Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED),
                Vector(2, 0, 3)
            )
        )
        //North inner center part (2, 1)
        parts.add(
            getRandomInnerPart(random, endPartChance,
                Connection(
                    Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE,
                    Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED),
                Vector(2, 0, 1)
            )
        )

        return parts
    }

    private fun getRandomInnerPart(random: Random, endPartChance: Double, connection: Connection, relativePosition: Vector): InnerPart {
        val con = if (random.nextFloat() < endPartChance) {
            //Set all don't care connections to closed to get end part
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