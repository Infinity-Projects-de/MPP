package de.danielmaile.mpp.block

import de.danielmaile.mpp.item.ItemType
import de.danielmaile.mpp.item.MPP_ITEM_TAG_KEY
import de.danielmaile.mpp.util.doesKeyExist
import org.bukkit.Instrument
import org.bukkit.Material
import org.bukkit.Note
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPhysicsEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.block.NotePlayEvent

class ListenerBlock : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (event.isCancelled) return

        val block = event.block
        val itemStack = event.itemInHand

        // Return if it's not a mpp item
        // If it's a vanilla noteblock make sure the place block has the default blockstate
        if (!itemStack.doesKeyExist(MPP_ITEM_TAG_KEY)) {
            if(itemStack.type == Material.NOTE_BLOCK) {
                block.type = Material.NOTE_BLOCK
                val blockData = block.blockData as NoteBlock
                blockData.instrument = Instrument.PIANO
                blockData.isPowered = false
                blockData.note = Note(0)
                block.blockData = blockData
            }
            return
        }

        val itemType = ItemType.fromTag(itemStack) ?: return
        val blockType = itemType.placeBlockType

        // If item has no corresponding block type return
        if (blockType == null) {
            event.isCancelled = true
            return
        }

        // Place a noteblock with the correct block state
        block.type = Material.NOTE_BLOCK
        val blockData = block.blockData as NoteBlock
        blockData.instrument = blockType.instrument
        blockData.isPowered = blockType.isPowered
        blockData.note = blockType.note
        block.blockData = blockData
    }

    // Prevent the blockstate of a noteblock to change
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBlockPhysics(event: BlockPhysicsEvent) {
        //Prevent a noteblock from being powered
        if(event.changedType == Material.NOTE_BLOCK) {
            event.isCancelled = true
            return
        }

        // Prevent a block from changing the noteblock instrument
        if(event.block.getRelative(BlockFace.UP).type == Material.NOTE_BLOCK) {
            event.isCancelled = true
            event.block.state.update(true, false)
        }
    }

    // Prevent a player from changing the noteblock tone
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onNotePlay(event: NotePlayEvent) {
        event.isCancelled = true
    }
}