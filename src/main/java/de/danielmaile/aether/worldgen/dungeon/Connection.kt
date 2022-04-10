package de.danielmaile.aether.worldgen.dungeon

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

    fun getConnectionState(direction: Direction): ConnectionState {
        return when (direction) {
            Direction.EAST -> eastState
            Direction.WEST -> westState
            Direction.SOUTH -> southState
            Direction.NORTH -> northState
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

    fun getConnectDirections(): ArrayList<Direction> {
        val connectDirections = ArrayList<Direction>()
        if (eastState == ConnectionState.CONNECTED) connectDirections.add(Direction.EAST)
        if (westState == ConnectionState.CONNECTED) connectDirections.add(Direction.WEST)
        if (southState == ConnectionState.CONNECTED) connectDirections.add(Direction.SOUTH)
        if (northState == ConnectionState.CONNECTED) connectDirections.add(Direction.NORTH)
        return connectDirections
    }

    companion object {

        @JvmStatic
        fun fromDirections(vararg directions: Direction): Connection {
            return Connection(
                if (directions.contains(Direction.EAST)) ConnectionState.CONNECTED else ConnectionState.CLOSED,
                if (directions.contains(Direction.WEST)) ConnectionState.CONNECTED else ConnectionState.CLOSED,
                if (directions.contains(Direction.SOUTH)) ConnectionState.CONNECTED else ConnectionState.CLOSED,
                if (directions.contains(Direction.NORTH)) ConnectionState.CONNECTED else ConnectionState.CLOSED
            )
        }
    }
}