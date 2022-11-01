package de.danielmaile.mpp.block

import de.danielmaile.mpp.util.ToolType
import org.bukkit.Instrument
import org.bukkit.Note
import org.bukkit.Sound
import org.bukkit.block.data.type.NoteBlock

enum class BlockType(
    val instrument: Instrument,
    val note: Note,
    val isPowered: Boolean,
    val toolType: ToolType,
    val hardness: Float,
    val placeSound: Sound,
    val breakSound: Sound
) {
    AETHER_LOG(Instrument.PIANO, Note(1), false, ToolType.AXE, 2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    FIRE_LOG(Instrument.PIANO, Note(2), false, ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    WATER_LOG(Instrument.PIANO, Note(3), false, ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    AIR_LOG(Instrument.PIANO, Note(4), false, ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    EARTH_LOG(Instrument.PIANO, Note(5), false, ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),

    AETHER_PLANKS(Instrument.PIANO, Note(6), false, ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    FIRE_PLANKS(Instrument.PIANO, Note(7), false, ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    WATER_PLANKS(Instrument.PIANO, Note(8), false, ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    AIR_PLANKS(Instrument.PIANO, Note(9), false, ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),
    EARTH_PLANKS(Instrument.PIANO, Note(10), false, ToolType.AXE,2f, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK),

    SILVER_ORE(Instrument.PIANO, Note(11), false, ToolType.PICKAXE,5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    TITANIUM_ORE(Instrument.PIANO, Note(12), false, ToolType.PICKAXE,5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    ZANITE_ORE(Instrument.PIANO, Note(13), false, ToolType.PICKAXE,5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    GRAVITITE_ORE(Instrument.PIANO, Note(14), false, ToolType.PICKAXE,8f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),

    AETHER_STONE(Instrument.PIANO, Note(15), false, ToolType.PICKAXE, 1.5f, Sound.BLOCK_STONE_PLACE, Sound.BLOCK_STONE_BREAK),
    CLOUD_HEAL(Instrument.PIANO, Note(16), false, ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SLOW_FALLING(Instrument.PIANO, Note(17), false, ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SPEED(Instrument.PIANO, Note(18), false, ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_JUMP(Instrument.PIANO, Note(19), false, ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_HEAL2(Instrument.PIANO, Note(20), false, ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_SPEED2(Instrument.PIANO, Note(21), false, ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    CLOUD_JUMP2(Instrument.PIANO, Note(22), false, ToolType.SHOVEL, 2f, Sound.BLOCK_WOOL_PLACE, Sound.BLOCK_WOOL_BREAK),
    AETHER_GRASS_BLOCK(Instrument.PIANO, Note(23), false, ToolType.SHOVEL, 0.6f, Sound.BLOCK_GRASS_PLACE, Sound.BLOCK_GRASS_BREAK),
    AETHER_DIRT(Instrument.PIANO, Note(24), false, ToolType.SHOVEL, 0.6f, Sound.BLOCK_ROOTED_DIRT_PLACE, Sound.BLOCK_ROOTED_DIRT_BREAK),

    CRYSTAL_ORE(Instrument.PIANO, Note(1), true, ToolType.PICKAXE,5f, Sound.BLOCK_AMETHYST_BLOCK_PLACE, Sound.BLOCK_AMETHYST_BLOCK_BREAK);

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