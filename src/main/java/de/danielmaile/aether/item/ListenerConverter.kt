package de.danielmaile.aether.item

import de.danielmaile.aether.inst
import de.danielmaile.aether.item.ItemType.Companion.fromTag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.Inventory

class ListenerConverter : Listener {

    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        if (inst().configManager.itemConverterEnabled) {
            checkAndReplace(event.inventory)
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (inst().configManager.itemConverterEnabled) {
            checkAndReplace(event.player.inventory)
            checkAndReplace(event.player.enderChest)
        }
    }

    private fun checkAndReplace(inventory: Inventory) {
        val content = inventory.contents ?: return

        for (i in content.indices) {
            val itemStack = content[i] ?: continue
            val type = fromTag(itemStack) ?: continue
            val typeStack = type.getItemStack(itemStack.amount)

            if (itemStack == typeStack) continue
            inventory.setItem(i, typeStack)
        }
    }
}