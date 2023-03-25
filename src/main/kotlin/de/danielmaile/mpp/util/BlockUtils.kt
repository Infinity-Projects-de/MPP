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

package de.danielmaile.mpp.util

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.BlockPosition
import de.danielmaile.mpp.block.BlockType
import de.danielmaile.mpp.inst
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.entity.Player

/**
 * @return if the given block is a custom mpp block
 */
fun Block.isCustom(): Boolean {
    if (this.blockData !is NoteBlock) return false
    return BlockType.fromBlockData(this.blockData as NoteBlock) != null
}

/**
 * Sends a packet with the current block break animation to all players in a 1-chunk-range
 * with breaking [stage].
 *
 * @param stage The breaking stage between 0-9 (both inclusive).
 * A different number will cause the breaking texture to disappear.
 *
 */
fun Block.sendDestructionStagePacket(stage: Int) {
    val packetContainer = PacketContainer(PacketType.Play.Server.BLOCK_BREAK_ANIMATION)
    packetContainer.blockPositionModifier.write(0, BlockPosition(this.x, this.y, this.z))
    packetContainer.integers
        .write(0, this.getEntityID())
        .write(1, stage)

    // run at next tick to ensure it's not async
    Bukkit.getScheduler().runTask(
        inst(),
        Runnable {
            this.location.getNearbyEntitiesByType(Player::class.java, 16.0)
                .forEach {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(it, packetContainer)
                }
        }
    )
}

fun Block.getEntityID(): Int {
    return this.x and 0xFFF shl 20 or (this.z and 0xFFF) shl 8 or (this.y and 0xFF)
}

val Block.centerLocation: Location
    get() = this.location.add(0.5, 0.5, 0.5)
