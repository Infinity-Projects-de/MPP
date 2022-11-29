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

package de.danielmaile.mpp.item.funtion

import de.danielmaile.mpp.item.MPP_ITEM_TAG_KEY
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

    // when hit by lightning sword -> lightning strikes victim
    // when hit by flame or sun sword -> set victim on fire
    // when hit by ice sword -> give victim slowness and free effect
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
            ItemType.FIRE_SWORD, ItemType.SUN_SWORD -> {
                victim.fireTicks = 300
            }
            ItemType.ICE_SWORD -> {
                victim.freezeTicks = 40
                victim.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 40, 4, false, false))
            }
            else -> {}
        }
    }

    // prevent use of mpp items (iron horse armor material) on horses
    @EventHandler
    fun onSaddleEquip(event: InventoryClickEvent) {
        if (event.inventory !is HorseInventory) return
        val currentItem = event.currentItem ?: return
        if (!currentItem.doesKeyExist(MPP_ITEM_TAG_KEY)) return
        event.isCancelled = true
    }
}