package de.danielmaile.lama.aether.item

import de.danielmaile.lama.aether.inst
import de.danielmaile.lama.aether.util.hasKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class ListenerBlock : Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return

        val block = event.block
        if (block.world != inst().aetherWorld.getWorld()) return

        val location = block.location
        val blockType = BlockType.fromMaterial(block.type) ?: return

        block.world.dropItemNaturally(location, blockType.itemDrop.getItemStack())
        event.isDropItems = false
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        if (event.isCancelled) return

        val block = event.block
        val itemStack = event.itemInHand

        if (block.world == inst().aetherWorld.getWorld()) {
            if (itemStack.hasKey(AETHER_ITEM_TAG_KEY)) {
                //Convert Item to correct material
                val itemType = ItemType.fromTag(itemStack)
                if (itemType != null) {
                    //Change material but keep orientation
                    val data = block.blockData
                    block.type = itemType.placeMaterial!!
                    block.blockData = data
                }
            } else {
                //Check if block which a player tries to place is already used by aether block
                val blockType = BlockType.fromMaterial(itemStack.type)
                if (blockType != null) {
                    event.isCancelled = true
                }
            }
        } else {
            //Aether items can't be placed in other worlds
            if (itemStack.hasKey(AETHER_ITEM_TAG_KEY)) {
                event.isCancelled = true
            }
        }
    }
}