package de.danielmaile.mpp.item

import org.bukkit.Instrument
import org.bukkit.Note
import org.bukkit.block.data.type.NoteBlock

enum class BlockType(
    val instrument: Instrument,
    val note: Note,
    val isPowered: Boolean
) {
    AETHER_OAK_LOG(Instrument.PIANO, Note(1), false),
    AETHER_SPRUCE_LOG(Instrument.PIANO, Note(2), false),
    AETHER_BIRCH_LOG(Instrument.PIANO, Note(3), false),
    AETHER_JUNGLE_LOG(Instrument.PIANO, Note(4), false),
    AETHER_ACACIA_LOG(Instrument.PIANO, Note(5), false),
    AETHER_DARK_OAK_LOG(Instrument.PIANO, Note(6), false),
    AETHER_OAK_PLANKS(Instrument.PIANO, Note(7), false),
    AETHER_SPRUCE_PLANKS(Instrument.PIANO, Note(8), false),
    AETHER_BIRCH_PLANKS(Instrument.PIANO, Note(9), false),
    AETHER_JUNGLE_PLANKS(Instrument.PIANO, Note(10), false),
    AETHER_ACACIA_PLANKS(Instrument.PIANO, Note(11), false),
    AETHER_DARK_OAK_PLANKS(Instrument.PIANO, Note(12), false),
    ZANITE_ORE(Instrument.PIANO, Note(13), false),
    GRAVITITE_ORE(Instrument.PIANO, Note(14), false),
    AETHER_STONE(Instrument.PIANO, Note(15), false),
    CLOUD_HEAL(Instrument.PIANO, Note(16), false),
    CLOUD_SLOW_FALLING(Instrument.PIANO, Note(17), false),
    CLOUD_SPEED(Instrument.PIANO, Note(18), false),
    CLOUD_JUMP(Instrument.PIANO, Note(19), false),
    CLOUD_HEAL2(Instrument.PIANO, Note(20), false),
    CLOUD_SPEED2(Instrument.PIANO, Note(21), false),
    CLOUD_JUMP2(Instrument.PIANO, Note(22), false);

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