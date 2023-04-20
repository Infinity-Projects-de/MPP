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

package de.danielmaile.mpp.block

import de.danielmaile.mpp.item.items.Tools.ToolType
import org.bukkit.Instrument
import org.bukkit.Material
import org.bukkit.Note
import org.bukkit.Sound
import org.bukkit.block.data.BlockData
import org.bukkit.block.data.type.NoteBlock

enum class BlockType(
    val toolType: ToolType,
    var tier: Int,
    val hardness: Float,
    val placeSound: Sound,
    val breakSound: Sound
) {

    // don't change order because existing items will break
    AETHER_LOG(ToolType.AXE, 0, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    AETHER_PLANKS(ToolType.AXE, 0, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    AETHER_LEAVES(ToolType.AXE, 0, 2f, Sound.BLOCK_GRASS_PLACE, Sound.BLOCK_GRASS_BREAK),
    ZANITE_ORE(ToolType.PICKAXE, 1, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    GRAVITITE_ORE(ToolType.PICKAXE, 2, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    AETHER_STONE(ToolType.PICKAXE, 1, 1.5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    CLOUD_HEAL(ToolType.SHOVEL, 0, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SLOW_FALLING(ToolType.SHOVEL, 0, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SPEED(ToolType.SHOVEL, 0, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_JUMP(ToolType.SHOVEL, 0, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_HEAL2(ToolType.SHOVEL, 0, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SPEED2(ToolType.SHOVEL, 0, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_JUMP2(ToolType.SHOVEL, 0, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    AETHER_GRASS_BLOCK(ToolType.SHOVEL, 0, 0.6f, Sound.BLOCK_GRASS_PLACE, Sound.BLOCK_GRASS_BREAK),
    AETHER_DIRT(ToolType.SHOVEL, 0, 0.6f, Sound.BLOCK_ROOTED_DIRT_PLACE, Sound.BLOCK_ROOTED_DIRT_BREAK),
    ZANITE_BLOCK(ToolType.PICKAXE, 1, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    GRAVITITE_BLOCK(ToolType.PICKAXE, 2, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK);

    val instrument: Instrument
    val note: Note
    val isPowered: Boolean
    val blockData: BlockData
        get() = Material.NOTE_BLOCK.createBlockData().apply {
            this as NoteBlock
            note = this@BlockType.note
            isPowered = this@BlockType.isPowered
            instrument = this@BlockType.instrument
        }

    init {
        instrument = calculateInstrument()
        note = calculateNote()
        isPowered = calculateIsPowered()
    }

    private fun calculateInstrument(): Instrument {
        return Instrument.values()[this.ordinal / 50]
    }

    private fun calculateNote(): Note {
        // start with ordinal 1 because default note block uses state PIANO;Note(0);false
        return Note((this.ordinal + 1) % 25)
    }

    private fun calculateIsPowered(): Boolean {
        return this.ordinal % 50 >= 24
    }

    companion object {

        /**
         * @return Returns a blocktype based on the given noteblock data.
         * If none was found return null
         */
        @JvmStatic
        fun fromBlockData(noteBlockData: NoteBlock): BlockType? {
            return values().firstOrNull {
                it.instrument == noteBlockData.instrument && it.note == noteBlockData.note && it.isPowered == noteBlockData.isPowered
            }
        }
    }
}
