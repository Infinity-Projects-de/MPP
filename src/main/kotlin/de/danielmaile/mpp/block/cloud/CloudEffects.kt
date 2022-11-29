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

package de.danielmaile.mpp.block.cloud

import de.danielmaile.mpp.block.BlockType
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.getBlockBelow
import org.bukkit.Bukkit
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class CloudEffects {

    private val effects = mapOf(
        BlockType.CLOUD_HEAL to Pair(PotionEffectType.REGENERATION, 0),
        BlockType.CLOUD_SLOW_FALLING to Pair(PotionEffectType.SLOW_FALLING, 0),
        BlockType.CLOUD_SPEED to Pair(PotionEffectType.SPEED, 1),
        BlockType.CLOUD_JUMP to Pair(PotionEffectType.JUMP, 1),
        BlockType.CLOUD_HEAL2 to Pair(PotionEffectType.REGENERATION, 2),
        BlockType.CLOUD_SPEED2 to Pair(PotionEffectType.SPEED, 3),
        BlockType.CLOUD_JUMP2 to Pair(PotionEffectType.JUMP, 4)
    )

    init {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
            for (player in Bukkit.getOnlinePlayers()) {
                val block = player.getBlockBelow()
                if (block.blockData !is NoteBlock) continue
                val blockType = BlockType.fromBlockData(block.blockData as NoteBlock) ?: continue
                val effect = effects[blockType] ?: continue
                player.addPotionEffect(PotionEffect(effect.first, 60, effect.second, false, false))
            }
        }, 5L, 5L)
    }
}