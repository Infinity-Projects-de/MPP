package de.danielmaile.mpp.aether.world.dungeon

import org.bukkit.block.BlockFace

val END_PART_TYPES = listOf(PartType.S, PartType.N, PartType.E, PartType.W)

enum class PartType(val connection: Connection) {
    CLOSED(Connection.fromDirections()),

    S(Connection.fromDirections(BlockFace.SOUTH)),
    N(Connection.fromDirections(BlockFace.NORTH)),
    W(Connection.fromDirections(BlockFace.WEST)),
    E(Connection.fromDirections(BlockFace.EAST)),

    SN(Connection.fromDirections(BlockFace.SOUTH, BlockFace.NORTH)),
    WS(Connection.fromDirections(BlockFace.WEST, BlockFace.SOUTH)),
    ES(Connection.fromDirections(BlockFace.EAST, BlockFace.SOUTH)),
    WN(Connection.fromDirections(BlockFace.WEST, BlockFace.NORTH)),
    EN(Connection.fromDirections(BlockFace.EAST, BlockFace.NORTH)),
    EW(Connection.fromDirections(BlockFace.EAST, BlockFace.WEST)),

    EWS(Connection.fromDirections(BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH)),
    WSN(Connection.fromDirections(BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH)),
    ESN(Connection.fromDirections(BlockFace.EAST, BlockFace.SOUTH, BlockFace.NORTH)),
    EWN(Connection.fromDirections(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH)),

    EWSN(Connection.fromDirections(BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH));
}