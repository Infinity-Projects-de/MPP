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

package de.danielmaile.mpp.item

import de.danielmaile.mpp.inst
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
        val content = inventory.contents

        for (i in content.indices) {
            val inventoryItemStack = content[i] ?: continue
            val item = ItemRegistry.getItemFromItemstack(inventoryItemStack) ?: continue
            val itemStack = item.itemStack(inventoryItemStack.amount)

            if (inventoryItemStack == itemStack) continue
            inventory.setItem(i, itemStack)
        }
    }
}
