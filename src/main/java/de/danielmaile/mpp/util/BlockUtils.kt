package de.danielmaile.mpp.util

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.BlockPosition
import de.danielmaile.mpp.block.BlockType
import org.bukkit.block.Block
import org.bukkit.block.data.type.NoteBlock

/**
 * @return if the given block is a custom mpp block
 */
fun Block.isCustom(): Boolean {
    if (this.blockData !is NoteBlock) return false
    return BlockType.fromBlockData(this.blockData as NoteBlock) != null
}

/**
 * Sends a packet with the current block break animation to all players in a 1-chunk-range
 * with breaking [stage].
 *
 * @param stage The breaking stage between 0-9 (both inclusive).
 * A different number will cause the breaking texture to disappear.
 *
 */
fun Block.sendDestructionStagePacket(stage: Int) {
    val packetContainer = PacketContainer(PacketType.Play.Server.BLOCK_BREAK_ANIMATION)
    packetContainer.blockPositionModifier.write(0, BlockPosition(this.x, this.y, this.z))
    packetContainer.integers
        .write(0, this.getEntityID())
        .write(1, stage)

    this.location.getPlayersNearby(32.0)
        .forEach {
            ProtocolLibrary.getProtocolManager().sendServerPacket(it, packetContainer)
        }
}

fun Block.getEntityID(): Int {
    return this.x and 0xFFF shl 20 or (this.z and 0xFFF) shl 8 or (this.y and 0xFF)
}