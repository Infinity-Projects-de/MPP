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

import de.danielmaile.mpp.item.ItemRegistry
import de.danielmaile.mpp.item.MPP_ITEM_TAG_KEY
import de.danielmaile.mpp.item.items.Blocks
import de.danielmaile.mpp.util.centerLocation
import de.danielmaile.mpp.util.doesKeyExist
import de.danielmaile.mpp.util.isCustom
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Instrument
import org.bukkit.Material
import org.bukkit.Note
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPhysicsEvent
import org.bukkit.event.block.BlockPistonExtendEvent
import org.bukkit.event.block.BlockPistonRetractEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.block.NotePlayEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class ListenerBlock : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (event.isCancelled) return

        val block = event.block
        val itemStack = event.itemInHand

        // return if it's not a mpp item
        // if it's a vanilla noteblock make sure the place block has the default block state
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

        val item = ItemRegistry.getItemFromItemstack(itemStack) ?: return
        if (item !is Blocks) {
            event.isCancelled = true
            return
        }
        val blockType = item.blockType

        // place a noteblock with the correct block state
        block.type = Material.NOTE_BLOCK
        val blockData = block.blockData as NoteBlock
        blockData.instrument = blockType.instrument
        blockData.isPowered = blockType.isPowered
        blockData.note = blockType.note
        block.blockData = blockData
    }

    // prevent the blockstate of a noteblock to change
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBlockPhysics(event: BlockPhysicsEvent) {
        // prevent a noteblock from being powered
        if (event.changedType == Material.NOTE_BLOCK) {
            event.isCancelled = true
            return
        }

        // prevent a block from changing the noteblock instrument
        if (event.block.getRelative(BlockFace.UP).type == Material.NOTE_BLOCK) {
            event.isCancelled = true
            event.block.state.update(true, false)
        }
    }

    // prevent a player from playing a note
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onNotePlay(event: NotePlayEvent) {
        event.isCancelled = true
    }

    // handle block placement against custom block
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInteract(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        val clickedBlock = event.clickedBlock ?: return
        if (clickedBlock.type != Material.NOTE_BLOCK) return

        event.isCancelled = true

        // return if clicked block is no custom block
        if (!clickedBlock.isCustom()) {
            return
        }

        // player is not trying to place a block
        val itemStack = event.item ?: return
        if (!itemStack.type.isBlock) {
            return
        }

        val placeBlock = clickedBlock.getRelative(event.blockFace)
        // can't place block because there is already another block
        if (placeBlock.type != Material.AIR) {
            return
        }

        // can't place block because there are entities inside the block
        if (!placeBlock.world.getNearbyLivingEntities(placeBlock.centerLocation, 0.8, 0.5, 0.8).isEmpty()) {
            return
        }

        // call BlockPlaceEvent and return if it's cancelled
        val blockPlaceEvent =
            BlockPlaceEvent(placeBlock, placeBlock.state, clickedBlock, itemStack, event.player, true, EquipmentSlot.HAND)
        Bukkit.getPluginManager().callEvent(blockPlaceEvent)
        if (blockPlaceEvent.isCancelled) {
            return
        }

        val item = ItemRegistry.getItemFromItemstack(itemStack)
        if (item == null) {
            // place block if it's a non-custom block
            placeBlock.type = itemStack.type
        } else {
            // if item has no corresponding block type return
            if (item !is Blocks) return
            val blockType = item.blockType

            // place a noteblock with the correct block state
            placeBlock.type = Material.NOTE_BLOCK
            val blockData = placeBlock.blockData as NoteBlock
            blockData.instrument = blockType.instrument
            blockData.isPowered = blockType.isPowered
            blockData.note = blockType.note
            placeBlock.blockData = blockData

            // play place sound
            placeBlock.world.playSound(placeBlock.location, blockType.placeSound, 1f, 1f)
        }

        // remove item from inventory
        if (event.player.gameMode != GameMode.CREATIVE) {
            itemStack.amount--
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
        event.blockList().removeAll(customBlocks.toSet())
        customBlocks.forEach {
            val blockType = BlockType.fromBlockData(it.blockData as NoteBlock) ?: return@forEach
            val itemType = Blocks.getBlockDrop(blockType) ?: return@forEach
            it.world.dropItemNaturally(it.location, itemType.itemStack(1))
            it.type = Material.AIR
        }
    }

    // Prevent the breaking of custom blocks because it's done by the custom system
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.isCancelled = event.block.isCustom()
    }
}
