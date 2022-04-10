package de.danielmaile.aether.item

import de.danielmaile.aether.util.hasKey
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent

class ListenerCrafting : Listener {

    //Check for crafting of vanilla items with aether items
    @EventHandler
    fun onCraftPrepare(event: PrepareItemCraftEvent) {
        val result = event.inventory.result ?: return
        if (result.hasKey(AETHER_ITEM_TAG_KEY)) return
        val ingredients = event.inventory.contents ?: return

        for (ingredient in ingredients) {
            if (ingredient == null || ingredient.type == Material.AIR) continue
            if (ingredient.hasKey(AETHER_ITEM_TAG_KEY)) {
                event.inventory.result = null
                return
            }
        }
    }
}