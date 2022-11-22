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

    // Don't change order because existing items will break
    AETHER_LOG(ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    FIRE_LOG(ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    WATER_LOG( ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    AIR_LOG(ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    EARTH_LOG(ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    AETHER_PLANKS(ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    FIRE_PLANKS(ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    WATER_PLANKS(ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    AIR_PLANKS(ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    EARTH_PLANKS(ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    SILVER_ORE(ToolType.PICKAXE,5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    TITANIUM_ORE(ToolType.PICKAXE,5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    ZANITE_ORE(ToolType.PICKAXE,5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    GRAVITITE_ORE(ToolType.PICKAXE,8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    AETHER_STONE(ToolType.PICKAXE, 1.5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    CLOUD_HEAL(ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SLOW_FALLING(ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SPEED(ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_JUMP(ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_HEAL2(ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SPEED2(ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_JUMP2(ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    AETHER_GRASS_BLOCK(ToolType.SHOVEL, 0.6f, Sound.BLOCK_GRASS_PLACE, Sound.BLOCK_GRASS_BREAK),
    AETHER_DIRT(ToolType.SHOVEL, 0.6f, Sound.BLOCK_ROOTED_DIRT_PLACE, Sound.BLOCK_ROOTED_DIRT_BREAK),
    CRYSTAL_ORE(ToolType.PICKAXE,5f, Sound.BLOCK_AMETHYST_BLOCK_PLACE, Sound.BLOCK_AMETHYST_BLOCK_BREAK),
    FIRE_LEAVES(ToolType.HOE,0.5f, Sound.BLOCK_AZALEA_LEAVES_PLACE, Sound.BLOCK_AZALEA_LEAVES_BREAK),
    WATER_LEAVES(ToolType.HOE,0.5f, Sound.BLOCK_AZALEA_LEAVES_PLACE, Sound.BLOCK_AZALEA_LEAVES_BREAK),
    AIR_LEAVES(ToolType.HOE,0.5f, Sound.BLOCK_AZALEA_LEAVES_PLACE, Sound.BLOCK_AZALEA_LEAVES_BREAK),
    EARTH_LEAVES(ToolType.HOE,0.5f, Sound.BLOCK_AZALEA_LEAVES_PLACE, Sound.BLOCK_AZALEA_LEAVES_BREAK);

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
        // Start with ordinal 1 because default note block uses state PIANO;Note(0);false
        return Note((this.ordinal + 1) % 25)
    }

    private fun calculateIsPowered(): Boolean {
        return this.ordinal % 50 >= 24
    }

    companion object {

        // Returns a blocktype based on the given noteblock data.
        // If none was found return null
        @JvmStatic
        fun fromBlockData(noteBlockData: NoteBlock): BlockType? {
            return values().firstOrNull {
                it.instrument == noteBlockData.instrument && it.note == noteBlockData.note && it.isPowered == noteBlockData.isPowered
            }
        }
    }
}