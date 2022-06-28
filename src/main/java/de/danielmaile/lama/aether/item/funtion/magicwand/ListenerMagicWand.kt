package de.danielmaile.lama.aether.item.funtion.magicwand

import de.danielmaile.lama.aether.inst
import de.danielmaile.lama.aether.item.ItemType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class ListenerMagicWand : Listener {

    @EventHandler
    fun onItemUse(event: PlayerInteractEvent) {
        if (event.hand != EquipmentSlot.HAND) return
        if (ItemType.fromTag(event.item) != ItemType.MAGIC_WAND) return
        event.isCancelled = true

        val wand = MagicWand(event.item!!)
        if (event.action.isLeftClick) {
            //Change spell
            wand.nextSpell()
            val component = inst().getLanguageManager().getComponent("items.MAGIC_WAND.current_spell")
                .append(wand.currentSpell.displayName)
            event.player.sendActionBar(component)
            event.player.inventory.setItemInMainHand(wand.itemStack)
        } else if (event.action.isRightClick) {
            //Fire magic wand
            wand.fire(event.player)
        }
    }
}