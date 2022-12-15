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

import de.danielmaile.mpp.util.ToolType
import org.bukkit.Instrument
import org.bukkit.Note
import org.bukkit.Sound
import org.bukkit.block.data.type.NoteBlock

enum class BlockType(
    val toolType: ToolType,
    val hardness: Float,
    val placeSound: Sound,
    val breakSound: Sound
) {

    // don't change order because existing items will break
    AETHER_LOG(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    FIRE_LOG(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    WATER_LOG(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    AIR_LOG(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    EARTH_LOG(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    AETHER_PLANKS(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    FIRE_PLANKS(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    WATER_PLANKS(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    AIR_PLANKS(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    EARTH_PLANKS(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    SILVER_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    TITANIUM_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    ZANITE_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    GRAVITITE_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    AETHER_STONE(ToolType.PICKAXE, 1.5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    CLOUD_HEAL(ToolType.SHOVEL, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SLOW_FALLING(ToolType.SHOVEL, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SPEED(ToolType.SHOVEL, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_JUMP(ToolType.SHOVEL, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_HEAL2(ToolType.SHOVEL, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SPEED2(ToolType.SHOVEL, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_JUMP2(ToolType.SHOVEL, 0.2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    AETHER_GRASS_BLOCK(ToolType.SHOVEL, 0.6f, Sound.BLOCK_GRASS_PLACE, Sound.BLOCK_GRASS_BREAK),
    AETHER_DIRT(ToolType.SHOVEL, 0.6f, Sound.BLOCK_ROOTED_DIRT_PLACE, Sound.BLOCK_ROOTED_DIRT_BREAK),
    CRYSTAL_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_AMETHYST_BLOCK_PLACE, Sound.BLOCK_AMETHYST_BLOCK_BREAK),
    FIRE_LEAVES(ToolType.HOE, 0.3f, Sound.BLOCK_AZALEA_LEAVES_PLACE, Sound.BLOCK_AZALEA_LEAVES_BREAK),
    WATER_LEAVES(ToolType.HOE, 0.3f, Sound.BLOCK_AZALEA_LEAVES_PLACE, Sound.BLOCK_AZALEA_LEAVES_BREAK),
    AIR_LEAVES(ToolType.HOE, 0.3f, Sound.BLOCK_AZALEA_LEAVES_PLACE, Sound.BLOCK_AZALEA_LEAVES_BREAK),
    EARTH_LEAVES(ToolType.HOE, 0.3f, Sound.BLOCK_AZALEA_LEAVES_PLACE, Sound.BLOCK_AZALEA_LEAVES_BREAK),
    CRACKED_AETHER_STONE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    AETHER_QUARTZ_PILLAR(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    AETHER_QUARTZ_BLOCK(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    ZANITE_BLOCK(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    GRAVITITE_BLOCK(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    CHISELED_AETHER_STONE_BRICKS(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    AETHER_STONE_BRICKS(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    ENDER_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    AMBER_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    THALLASIUM_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    AMETRINE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    PENDORITE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    NETHER_EMERALD_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    ENDERITE_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    CRACKED_ENDERITE_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    TUNGSTEN_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    TIN_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    NIKOLITE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    LEAD_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    BAUXITE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    CINNEBAR_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    GALANA_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    IRIDIUM_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    PERIDOT_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    PYRITE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    RUBY_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    SHELDONITE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    SAPPHIRE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    SODALITE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    SPHALERITE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    ZINC_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    CINCINNASITE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    AETHERIUM_ORE(ToolType.PICKAXE, 15f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    TERMINITE_ORE(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    DEEPSLATE_SILVER_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    DEEPSLATE_TUNGSTEN_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    DEEPSLATE_LEAD_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    DEEPSLATE_NIKOLITE_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    DEEPSLATE_TIN_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    DEEPSLATE_BAUXITE_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    DEEPSLATE_GALANA_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    DEEPSLATE_IRIDIUM_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    DEEPSLATE_PERIDOT_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    DEEPSLATE_ZINC_ORE(ToolType.PICKAXE, 10f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    NETHER_ZINC_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    NETHER_TUNGSTEN_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    NETHER_LEAD_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    NETHER_BAUXITE_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    NETHER_RUBY_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    NETHER_SAPPHIRE_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    NETHER_TIN_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    END_NIKOLITE_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    END_IRIDIUM_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    END_TUNGSTEN_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    END_TIN_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    END_LEAD_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    END_BAUXITE_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    END_CINNEBAR_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    END_GALANA_ORE(ToolType.PICKAXE, 8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    AETHER_THIN_QUARTZ_PILLAR(ToolType.PICKAXE, 5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK);

    val instrument: Instrument
    val note: Note
    val isPowered: Boolean

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