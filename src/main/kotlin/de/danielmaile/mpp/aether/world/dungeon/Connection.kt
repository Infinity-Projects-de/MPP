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

data class Connection(
    val eastState: ConnectionState,
    val westState: ConnectionState,
    val southState: ConnectionState,
    val northState: ConnectionState
) {

    enum class ConnectionState {
        CONNECTED,  // side should be connected
        CLOSED,  // side shouldn't be connected
        DONT_CARE // don't care if it's connected or not
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
     * Don't cares of this connection get ignored.
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