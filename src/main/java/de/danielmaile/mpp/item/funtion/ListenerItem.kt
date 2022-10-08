package de.danielmaile.mpp.item.funtion

import de.danielmaile.mpp.item.AETHER_ITEM_TAG_KEY
import de.danielmaile.mpp.item.ItemType
import de.danielmaile.mpp.util.doesKeyExist
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.HorseInventory
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class ListenerItem : Listener {

    //When hit by lightning sword -> lightning strikes victim
    //When hit by flame or sun sword -> set victim on fire
    //When hit by ice sword -> give victim slowness and free effect
    @EventHandler
    fun onSwordDamage(event: EntityDamageByEntityEvent) {
        if (event.entity !is LivingEntity) return
        val victim = event.entity as LivingEntity

        if (event.damager !is Player) return
        val player = event.damager as Player

        val itemInUse: ItemStack = player.inventory.itemInMainHand
        val itemType = ItemType.fromTag(itemInUse) ?: return

        when (itemType) {
            ItemType.LIGHTNING_SWORD -> {
                victim.world.strikeLightning(victim.location)
            }
            ItemType.FLAME_SWORD, ItemType.SUN_SWORD -> {
                victim.fireTicks = 300
            }
            ItemType.ICE_SWORD -> {
                victim.freezeTicks = 40
                victim.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 40, 4, false, false))
            }
            else -> {}
        }
    }

    //Prevent use of aether items (iron horse armor material) on horses
    @EventHandler
    fun onSaddleEquip(event: InventoryClickEvent) {
        if (event.inventory !is HorseInventory) return
        val currentItem = event.currentItem ?: return
        if (!currentItem.doesKeyExist(AETHER_ITEM_TAG_KEY)) return
        event.isCancelled = true
    }
}