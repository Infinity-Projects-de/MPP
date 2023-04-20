/*
 * This file is part of MPP.
 * Copyright (c) 2023 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.block

import de.danielmaile.mpp.util.sendDestructionStagePacket
import org.bukkit.block.Block
import kotlin.math.floor

const val damagePerPhase = 1 / 9f
class DamagedBlock(
    val block: Block,
    val blockType: BlockType?,
    private val damagePerTick: Float
) {
    private var totalDamage = 0f

    private var phase = 0
    private var ticks = 0

    fun tick() {
        ticks++
        totalDamage += damagePerTick
        if (totalDamage > 1) {
            breakBlock()
            return
        }
        val currentPhase = floor(totalDamage / damagePerPhase).toInt()
        if (phase != currentPhase) {
            phase = currentPhase
            block.sendDestructionStagePacket(phase)
        }
        playMiningSound()
    }

    private fun breakBlock() {
        playBreakSound()
        damageTool()
        stop()
    }

    fun playMiningSound() {
        val sound = blockType?.breakSound // TODO: MUST BE HIT
            ?: block.blockSoundGroup.hitSound
        block.world.playSound(block.location, sound, 1f, 1f)
    }

    fun playBreakSound() {
        val sound = blockType?.breakSound ?: block.blockSoundGroup.breakSound
        block.world.playSound(block.location, sound, 1f, 1f)
    }

    fun damageTool() {
    }

    fun stop() {
    }
}
