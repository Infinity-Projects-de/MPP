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

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ItemRegistry
import de.danielmaile.mpp.util.getArrowLeftItem
import de.danielmaile.mpp.util.getArrowRightItem
import de.danielmaile.mpp.util.getFillerItem
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class ItemCollectionGUI : Listener {

    private val gui: PaginatedGui
    private val playerGuis = mutableMapOf<Player, Int>()

    init {
        gui = PaginatedGui(inst().getLanguageManager().getComponent("gui.title.item_collection"), 6, 45)
        ItemRegistry.getAllItems().forEach { gui.addItem(it.itemStack(1)) }
        gui.build()
    }

    fun open(player: Player) {
        gui.open(player, 0)
        playerGuis[player] = 0
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        if (playerGuis.containsKey(player)) {
            event.isCancelled = true
            val currentPage = playerGuis[player]!!
            when (event.currentItem) {
                getArrowLeftItem() -> {
                    if (currentPage > 0) {
                        gui.open(player, currentPage - 1)
                        playerGuis[player] = currentPage - 1
                    }
                }
                getArrowRightItem() -> {
                    if (currentPage < gui.totalPages - 1) {
                        gui.open(player, currentPage + 1)
                        playerGuis[player] = currentPage + 1
                    }
                }
                else -> {
                    event.whoClicked.inventory.addItem(event.currentItem ?: return)
                }
            }
        }
    }
}

fun registerEvents() {
    Bukkit.getPluginManager().registerEvents(ItemCollectionGUI(), inst())
}
