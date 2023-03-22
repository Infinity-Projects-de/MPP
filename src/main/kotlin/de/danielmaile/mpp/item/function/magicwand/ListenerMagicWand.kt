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

package de.danielmaile.mpp.item.function.magicwand

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ItemType
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
            // change spell
            wand.nextSpell()
            val component = inst().getLanguageManager().getComponent("items.MAGIC_WAND.current_spell")
                .append(wand.currentSpell.displayName)
            event.player.sendActionBar(component)
            event.player.inventory.setItemInMainHand(wand.itemStack)
        } else if (event.action.isRightClick) {
            // fire magic wand
            wand.fire(event.player)
        }
    }
}