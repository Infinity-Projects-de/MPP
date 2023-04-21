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

import de.danielmaile.mpp.block.utils.hardness
import de.danielmaile.mpp.block.utils.isToolCorrect
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ItemRegistry
import de.danielmaile.mpp.item.items.Blocks
import de.danielmaile.mpp.item.items.Tools
import de.danielmaile.mpp.util.sendDestructionStagePacket
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import kotlin.math.floor
import kotlin.math.min

const val damagePerPhase = 1 / 9f

// ONLY block logic
class DamagedBlock(
    val block: Block,
    val blockType: BlockType?
) {
    private var totalDamage = 0f

    private var phase = -1
    private var ticks = 0

    private val hardness = blockType?.hardness ?: block.hardness

    fun tick(damage: Float) {
        ticks++
        hitSound()
        totalDamage += (damage / hardness)
        val currentPhase = min(floor(totalDamage / damagePerPhase).toInt(), 9)
        if (phase != currentPhase) {
            phase = currentPhase
            block.sendDestructionStagePacket(phase)
        }
    }

    val isBroken: Boolean
        get() = totalDamage > 1f

    fun breakBlock(tool: ItemStack) {
        var mppTool = ItemRegistry.getItemFromItemstack(tool)
        if (mppTool !is Tools) mppTool = null

        if (blockType == null) {
            if ((mppTool as? Tools)?.isToolCorrect(block) != false) {
                block.breakNaturally(tool, true, true)
                return
            }
        } else {
            if ((mppTool as? Tools)?.isToolCorrect(block) == true || tool.isToolCorrect(blockType)) {
                val item = Blocks.getBlockDrop(blockType) ?: return
                block.world.dropItemNaturally(block.location, item.itemStack(1))
            }
        }

        Bukkit.getScheduler().runTask(inst()) { ->
            breakSound()
            block.type = Material.AIR
        }
    }

    private fun breakSound() {
        val sound = blockType?.breakSound ?: block.blockSoundGroup.breakSound
        block.world.playSound(block.location, sound, 1f, 1f)
    }

    private fun hitSound() {
        val sound = blockType?.breakSound ?: // TODO: Should be hit sound
            block.blockSoundGroup.hitSound
        block.world.playSound(block.location, sound, 1f, 1f)
    }

    fun stop() {
        block.sendDestructionStagePacket(-1)
    }
}
