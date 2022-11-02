package de.danielmaile.mpp.block

import de.danielmaile.mpp.item.ItemType
import de.danielmaile.mpp.item.MPP_ITEM_TAG_KEY
import de.danielmaile.mpp.util.centerLocation
import de.danielmaile.mpp.util.doesKeyExist
import de.danielmaile.mpp.util.isCustom
import org.bukkit.*
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot


class ListenerBlock : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (event.isCancelled) return

        val block = event.block
        val itemStack = event.itemInHand

        // Return if it's not a mpp item
        // If it's a vanilla noteblock make sure the place block has the default blockstate
        if (!itemStack.doesKeyExist(MPP_ITEM_TAG_KEY)) {
            if (itemStack.type == Material.NOTE_BLOCK) {
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
        // Prevent a noteblock from being powered
        if (event.changedType == Material.NOTE_BLOCK) {
            event.isCancelled = true
            return
        }

        // Prevent a block from changing the noteblock instrument
        if (event.block.getRelative(BlockFace.UP).type == Material.NOTE_BLOCK) {
            event.isCancelled = true
            event.block.state.update(true, false)
        }
    }

    // Prevent a player from playing a note
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onNotePlay(event: NotePlayEvent) {
        event.isCancelled = true
    }

    // Handle block placement against custom block
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInteract(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        val clickedBlock = event.clickedBlock ?: return
        if (clickedBlock.type != Material.NOTE_BLOCK) return

        event.isCancelled = true

        // Return if clicked block is no custom block
        if(!clickedBlock.isCustom()) {
            return
        }

        // Player is not trying to place a block
        val item = event.item ?: return
        if(!item.type.isBlock) {
            return
        }

        val placeBlock = clickedBlock.getRelative(event.blockFace)
        // Can't place block because there is already another block
        if(placeBlock.type != Material.AIR) {
            return
        }

        // Can't place block because there are entities inside the block
        if(!placeBlock.world.getNearbyLivingEntities(placeBlock.centerLocation, 0.8, 0.5, 0.8).isEmpty()) {
            return
        }

        // Call BlockPlaceEvent and return if it's cancelled
        val blockPlaceEvent = BlockPlaceEvent(placeBlock,  placeBlock.state, clickedBlock, item, event.player, true, EquipmentSlot.HAND)
        Bukkit.getPluginManager().callEvent(blockPlaceEvent)
        if(blockPlaceEvent.isCancelled) {
            return
        }

        val itemType = ItemType.fromTag(item)
        if(itemType == null) {
            // Place block if it's a non-custom block
            placeBlock.type = item.type
        } else {
            // If item has no corresponding block type return
            val blockType = itemType.placeBlockType ?: return

            // Place a noteblock with the correct block state
            placeBlock.type = Material.NOTE_BLOCK
            val blockData = placeBlock.blockData as NoteBlock
            blockData.instrument = blockType.instrument
            blockData.isPowered = blockType.isPowered
            blockData.note = blockType.note
            placeBlock.blockData = blockData

            // Play place sound
            placeBlock.world.playSound(placeBlock.location, blockType.placeSound, 1f, 1f)
        }

        // Remove item from inventory
        if(event.player.gameMode != GameMode.CREATIVE) {
            item.amount--
        }
    }

    // Prevent moving custom blocks with pistons
    @EventHandler
    fun onPistonExtends(event: BlockPistonExtendEvent) {
        if (event.blocks.stream().anyMatch { it.type == Material.NOTE_BLOCK }) {
            event.isCancelled = true
        }
    }

    // Prevent moving custom blocks with pistons
    @EventHandler
    fun onPistonRetract(event: BlockPistonRetractEvent) {
        if (event.blocks.stream().anyMatch { it.type == Material.NOTE_BLOCK }) {
            event.isCancelled = true
        }
    }

    // Drop the correct item when custom block gets exploded
    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        val customBlocks = event.blockList().filter { it.isCustom() }
        event.blockList().removeAll(customBlocks)
        customBlocks.forEach {
            val blockType = BlockType.fromBlockData(it.blockData as NoteBlock) ?: return@forEach
            val itemType = ItemType.fromPlaceBlockType(blockType) ?: return@forEach
            it.world.dropItemNaturally(it.location, itemType.getItemStack(1))
            it.type = Material.AIR
        }
    }

    // Prevent the breaking of custom blocks because it's done by the custom system
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.isCancelled = event.block.isCustom()
    }
}