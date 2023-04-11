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

import de.danielmaile.mpp.block.BlockType
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.packet.PacketHandler
import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
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
    val packet = ClientboundBlockDestructionPacket(getEntityID(), BlockPos(x, y, z), stage)

    // run at next tick to ensure it's not async
    Bukkit.getScheduler().runTask(
        inst(),
        Runnable {
            this.location.getNearbyEntitiesByType(Player::class.java, 16.0)
                .forEach {
                    PacketHandler.sendPacket(it, packet)
                }
        }
    )
}

fun Block.getEntityID(): Int {
    return this.x and 0xFFF shl 20 or (this.z and 0xFFF) shl 8 or (this.y and 0xFF)
}

val Block.centerLocation: Location
    get() = this.location.add(0.5, 0.5, 0.5)
