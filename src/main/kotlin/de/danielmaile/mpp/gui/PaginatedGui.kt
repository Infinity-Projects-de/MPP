/*
 * This file is part of MPP.
 * Copyright (c) 2023 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.gui

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class PaginatedGui(private val title: Component, private val rows: Int, private val pageSize: Int) : Listener {

    private val inventories = mutableListOf<Inventory>()
    private val items = mutableListOf<ItemStack>()
    val totalPages: Int
        get() = (items.size + pageSize - 1) / pageSize

    fun addItem(item: ItemStack) {
        items.add(item)
    }

    fun build() {
        for (page in 0 until totalPages) {
            val inventory = Bukkit.createInventory(null, rows * 9, title)
            // filler items
            for (i in (rows - 1) * 9 until rows * 9) {
                inventory.setItem(i, getFillerItem())
            }

            // pagination
            inventory.setItem((rows * 9) - 8, getArrowLeftItem())
            inventory.setItem((rows * 9) - 2, getArrowRightItem())


            val startIndex = page * pageSize
            val endIndex = minOf(startIndex + pageSize, items.size)
            for (i in startIndex until endIndex) {
                inventory.addItem(items[i])
            }
            inventories.add(inventory)
        }
    }

    fun open(player: Player, page: Int = 0) {
        if (inventories.isNotEmpty()) {
            player.openInventory(inventories[page])
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder == null && event.view.title == title.toString()) {
            event.isCancelled = true
        }
    }
}
