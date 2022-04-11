package de.danielmaile.aether.world.dungeon

import org.bukkit.block.BlockFace

data class Connection(
    val eastState: ConnectionState,
    val westState: ConnectionState,
    val southState: ConnectionState,
    val northState: ConnectionState
) {

    enum class ConnectionState {
        CONNECTED,  //Side should be connected
        CLOSED,  //Side shouldn't be connected
        DONT_CARE //Don't care if it's connected or not
    }

    fun getConnectionState(blockFace: BlockFace): ConnectionState {
        return when (blockFace) {
            BlockFace.EAST -> eastState
            BlockFace.WEST -> westState
            BlockFace.SOUTH -> southState
            BlockFace.NORTH -> northState
            else -> throw IllegalArgumentException()
        }
    }

    fun setDontCareToClosed(): Connection {
        return Connection(
            if (eastState == ConnectionState.DONT_CARE) ConnectionState.CLOSED else eastState,
            if (westState == ConnectionState.DONT_CARE) ConnectionState.CLOSED else westState,
            if (southState == ConnectionState.DONT_CARE) ConnectionState.CLOSED else southState,
            if (northState == ConnectionState.DONT_CARE) ConnectionState.CLOSED else northState
        )
    }

    /**
     * Check if other connection matches this connection.
     * Don't cares of this connection get ignored
     */
    fun isValid(other: Connection): Boolean {
        if (eastState != ConnectionState.DONT_CARE && eastState != other.eastState) return false
        if (westState != ConnectionState.DONT_CARE && westState != other.westState) return false
        if (southState != ConnectionState.DONT_CARE && southState != other.southState) return false
        if (northState != ConnectionState.DONT_CARE && northState != other.northState) return false
        return true
    }

    fun getConnectDirections(): ArrayList<BlockFace> {
        val connectDirections = ArrayList<BlockFace>()
        if (eastState == ConnectionState.CONNECTED) connectDirections.add(BlockFace.EAST)
        if (westState == ConnectionState.CONNECTED) connectDirections.add(BlockFace.WEST)
        if (southState == ConnectionState.CONNECTED) connectDirections.add(BlockFace.SOUTH)
        if (northState == ConnectionState.CONNECTED) connectDirections.add(BlockFace.NORTH)
        return connectDirections
    }

    companion object {

        @JvmStatic
        fun fromDirections(vararg blockFaces: BlockFace): Connection {
            return Connection(
                if (blockFaces.contains(BlockFace.EAST)) ConnectionState.CONNECTED else ConnectionState.CLOSED,
                if (blockFaces.contains(BlockFace.WEST)) ConnectionState.CONNECTED else ConnectionState.CLOSED,
                if (blockFaces.contains(BlockFace.SOUTH)) ConnectionState.CONNECTED else ConnectionState.CLOSED,
                if (blockFaces.contains(BlockFace.NORTH)) ConnectionState.CONNECTED else ConnectionState.CLOSED
            )
        }
    }
}