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

package de.danielmaile.mpp.item.function

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ArmorSet
import de.danielmaile.mpp.item.ItemType
import de.danielmaile.mpp.util.addPermEffect
import de.danielmaile.mpp.util.getEquippedArmorSet
import de.danielmaile.mpp.util.isGrounded
import de.danielmaile.mpp.util.setMaximumHealth
import org.bukkit.Bukkit
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityToggleGlideEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.potion.PotionEffectType

class ListenerArmor : Listener {

    private val boosted = ArrayList<Player>()

    init {
        // remove player from boosted list if he is on ground
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
            for (player: Player in Bukkit.getOnlinePlayers()) {
                if (boosted.contains(player) && !player.isFlying && player.isGrounded()) {
                    boosted.remove(player)
                }
            }
        }, 0L, 5L)
    }

    // when wearing zanite or gravitite or armor arrows bounce off
    @EventHandler
    fun onArrowHit(event: ProjectileHitEvent) {
        if (event.entity !is Arrow) return
        val arrow = event.entity as Arrow

        if (event.hitEntity !is Player) return
        val player = event.hitEntity as Player

        val equippedSet = player.getEquippedArmorSet()
        if (equippedSet != ArmorSet.ZANITE && equippedSet != ArmorSet.GRAVITITE) return

        arrow.velocity = arrow.velocity.normalize().multiply(-0.1)
        event.isCancelled = true
    }

    // when wearing gravitite armor player is immune to fall damage
    @EventHandler
    fun onFallDamage(event: EntityDamageEvent) {
        if (event.entity !is Player) return
        if (event.cause != EntityDamageEvent.DamageCause.FALL) return
        if ((event.entity as Player).getEquippedArmorSet() != ArmorSet.GRAVITITE) return
        event.isCancelled = true
    }

    @EventHandler
    fun onChangeArmor(event: PlayerArmorChangeEvent) {
        val player = event.player
        val oldItemType = ItemType.fromTag(event.oldItem)
        val newItemType = ItemType.fromTag(event.newItem)

        // player gets permanent slow fall when wearing valkyrie boots
        if (oldItemType == ItemType.VALKYRE_BOOTS) {
            player.removePotionEffect(PotionEffectType.SLOW_FALLING)
        }
        if (newItemType == ItemType.VALKYRE_BOOTS) {
            player.addPermEffect(PotionEffectType.SLOW_FALLING)
        }

        // player gets +2 health when wearing valkyrie ring
        if (oldItemType == ItemType.VALKYRE_RING) {
            player.setMaximumHealth(20.0)
        }
        if (newItemType == ItemType.VALKYRE_RING) {
            player.setMaximumHealth(24.0)
        }

        // player gets permanent regeneration fall when wearing full valkyrie armor set
        if (player.getEquippedArmorSet() == ArmorSet.VALKYRIE) {
            player.addPermEffect(PotionEffectType.REGENERATION)
        } else if (ArmorSet.VALKYRIE.contains(oldItemType)) {
            player.removePotionEffect(PotionEffectType.REGENERATION)
        }
    }

    // if player has full valkyre armor he can use elytra boost once per flight
    @EventHandler
    fun onSwapItem(event: PlayerSwapHandItemsEvent) {
        val player = event.player
        if (player.isGrounded()) return
        if (boosted.contains(player)) return
        if (player.getEquippedArmorSet() != ArmorSet.VALKYRIE) return
        event.isCancelled = true

        boosted.add(player)
        player.velocity = player.location.direction.multiply(5)
    }

    @EventHandler
    fun onFlightToggle(event: EntityToggleGlideEvent) {
        if (event.entity !is Player) return
        val player = event.entity as Player

        if (!event.isGliding) return
        if (player.getEquippedArmorSet() != ArmorSet.VALKYRIE) return

        player.sendActionBar(inst().getLanguageManager().getComponent("items.VALKYRE_WINGS.boost_info"))
    }
}