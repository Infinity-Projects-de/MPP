package de.danielmaile.mpp.block

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.data.type.NoteBlock

object DamagedBlocksService {

    private val damagedBlocks: HashMap<Location, DamagedBlock> = HashMap()

    fun startDamaging(block: Block) {
        if (block.blockData !is NoteBlock) return
        val blockType = BlockType.fromBlockData(block.blockData as NoteBlock) ?: return

        damagedBlocks.getOrPut(block.location) { DamagedBlock(block, blockType) }.startDestroying()
    }

    fun stopDamaging(block: Block) {
        val damagedBlock = damagedBlocks[block.location] ?: return
        damagedBlock.cancelDestroying()
        damagedBlocks.remove(block.location)
    }
}