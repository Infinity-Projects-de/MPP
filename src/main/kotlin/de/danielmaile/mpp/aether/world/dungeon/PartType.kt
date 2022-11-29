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