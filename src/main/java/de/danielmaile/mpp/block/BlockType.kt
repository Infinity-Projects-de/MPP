package de.danielmaile.mpp.block

import org.bukkit.Instrument
import org.bukkit.Note
import org.bukkit.block.data.type.NoteBlock

enum class BlockType(
    val instrument: Instrument,
    val note: Note,
    val isPowered: Boolean,
    val breakTime: Int
) {
    AETHER_OAK_LOG(Instrument.PIANO, Note(1), false, 100),
    AETHER_SPRUCE_LOG(Instrument.PIANO, Note(2), false, 100),
    AETHER_BIRCH_LOG(Instrument.PIANO, Note(3), false, 100),
    AETHER_JUNGLE_LOG(Instrument.PIANO, Note(4), false, 100),
    AETHER_ACACIA_LOG(Instrument.PIANO, Note(5), false, 100),
    AETHER_DARK_OAK_LOG(Instrument.PIANO, Note(6), false, 100),
    AETHER_OAK_PLANKS(Instrument.PIANO, Note(7), false, 100),
    AETHER_SPRUCE_PLANKS(Instrument.PIANO, Note(8), false, 100),
    AETHER_BIRCH_PLANKS(Instrument.PIANO, Note(9), false, 100),
    AETHER_JUNGLE_PLANKS(Instrument.PIANO, Note(10), false, 100),
    AETHER_ACACIA_PLANKS(Instrument.PIANO, Note(11), false, 100),
    AETHER_DARK_OAK_PLANKS(Instrument.PIANO, Note(12), false, 100),
    ZANITE_ORE(Instrument.PIANO, Note(13), false, 100),
    GRAVITITE_ORE(Instrument.PIANO, Note(14), false, 100),
    AETHER_STONE(Instrument.PIANO, Note(15), false, 100),
    CLOUD_HEAL(Instrument.PIANO, Note(16), false, 100),
    CLOUD_SLOW_FALLING(Instrument.PIANO, Note(17), false, 100),
    CLOUD_SPEED(Instrument.PIANO, Note(18), false, 100),
    CLOUD_JUMP(Instrument.PIANO, Note(19), false, 100),
    CLOUD_HEAL2(Instrument.PIANO, Note(20), false, 100),
    CLOUD_SPEED2(Instrument.PIANO, Note(21), false, 100),
    CLOUD_JUMP2(Instrument.PIANO, Note(22), false, 100);

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