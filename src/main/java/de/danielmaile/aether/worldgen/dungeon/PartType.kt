package de.danielmaile.aether.worldgen.dungeon

val END_PART_TYPES = listOf(PartType.S, PartType.N, PartType.E, PartType.W)

enum class PartType(val connection: Connection) {
    CLOSED(Connection.fromDirections()),

    S(Connection.fromDirections(Direction.SOUTH)),
    N(Connection.fromDirections(Direction.NORTH)),
    W(Connection.fromDirections(Direction.WEST)),
    E(Connection.fromDirections(Direction.EAST)),

    SN(Connection.fromDirections(Direction.SOUTH, Direction.NORTH)),
    WS(Connection.fromDirections(Direction.WEST, Direction.SOUTH)),
    ES(Connection.fromDirections(Direction.EAST, Direction.SOUTH)),
    WN(Connection.fromDirections(Direction.WEST, Direction.NORTH)),
    EN(Connection.fromDirections(Direction.EAST, Direction.NORTH)),
    EW(Connection.fromDirections(Direction.EAST, Direction.WEST)),

    EWS(Connection.fromDirections(Direction.EAST, Direction.WEST, Direction.SOUTH)),
    WSN(Connection.fromDirections(Direction.WEST, Direction.SOUTH, Direction.NORTH)),
    ESN(Connection.fromDirections(Direction.EAST, Direction.SOUTH, Direction.NORTH)),
    EWN(Connection.fromDirections(Direction.EAST, Direction.WEST, Direction.NORTH)),

    EWSN(Connection.fromDirections(Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH));
}