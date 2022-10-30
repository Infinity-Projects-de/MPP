package de.danielmaile.mpp.block

import de.danielmaile.mpp.util.ToolType
import org.bukkit.Instrument
import org.bukkit.Note
import org.bukkit.block.data.type.NoteBlock

enum class BlockType(
    val instrument: Instrument,
    val note: Note,
    val isPowered: Boolean,
    val toolType: ToolType,
    val hardness: Float
) {
    AETHER_OAK_LOG(Instrument.PIANO, Note(1), false, ToolType.AXE, 2f),
    AETHER_SPRUCE_LOG(Instrument.PIANO, Note(2), false, ToolType.AXE,2f),
    AETHER_BIRCH_LOG(Instrument.PIANO, Note(3), false, ToolType.AXE,2f),
    AETHER_JUNGLE_LOG(Instrument.PIANO, Note(4), false, ToolType.AXE,2f),
    AETHER_ACACIA_LOG(Instrument.PIANO, Note(5), false, ToolType.AXE,2f),
    AETHER_DARK_OAK_LOG(Instrument.PIANO, Note(6), false, ToolType.AXE,2f),
    AETHER_OAK_PLANKS(Instrument.PIANO, Note(7), false, ToolType.AXE,2f),
    AETHER_SPRUCE_PLANKS(Instrument.PIANO, Note(8), false, ToolType.AXE,2f),
    AETHER_BIRCH_PLANKS(Instrument.PIANO, Note(9), false, ToolType.AXE,2f),
    AETHER_JUNGLE_PLANKS(Instrument.PIANO, Note(10), false, ToolType.AXE,2f),
    AETHER_ACACIA_PLANKS(Instrument.PIANO, Note(11), false, ToolType.AXE,2f),
    AETHER_DARK_OAK_PLANKS(Instrument.PIANO, Note(12), false, ToolType.AXE,2f),
    ZANITE_ORE(Instrument.PIANO, Note(13), false, ToolType.PICKAXE,5f),
    GRAVITITE_ORE(Instrument.PIANO, Note(14), false, ToolType.PICKAXE,8f),
    AETHER_STONE(Instrument.PIANO, Note(15), false, ToolType.PICKAXE, 1.5f),
    CLOUD_HEAL(Instrument.PIANO, Note(16), false, ToolType.SHOVEL, 2f),
    CLOUD_SLOW_FALLING(Instrument.PIANO, Note(17), false, ToolType.SHOVEL, 2f),
    CLOUD_SPEED(Instrument.PIANO, Note(18), false, ToolType.SHOVEL, 2f),
    CLOUD_JUMP(Instrument.PIANO, Note(19), false, ToolType.SHOVEL, 2f),
    CLOUD_HEAL2(Instrument.PIANO, Note(20), false, ToolType.SHOVEL, 2f),
    CLOUD_SPEED2(Instrument.PIANO, Note(21), false, ToolType.SHOVEL, 2f),
    CLOUD_JUMP2(Instrument.PIANO, Note(22), false, ToolType.SHOVEL, 2f),
    AETHER_GRASS(Instrument.PIANO, Note(23), false, ToolType.SHOVEL, 0.6f),
    AETHER_DIRT(Instrument.PIANO, Note(24), false, ToolType.SHOVEL, 0.6f);

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