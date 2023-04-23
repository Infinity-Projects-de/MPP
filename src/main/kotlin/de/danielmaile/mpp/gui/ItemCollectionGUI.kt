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
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.GuiItem
import org.bukkit.entity.Player

class ItemCollectionGUI {

    val gui = Gui
        .paginated()
        .title(inst().getLanguageManager().getComponent("gui.title.item_collection"))
        .rows(6)
        .pageSize(45)
        .disableAllInteractions()
        .create()

    init {
        // filler items
        for (i in 1..9) {
            gui.setItem(6, i, GuiItem(getFillerItem()))
        }

        // pagination
        gui.setItem(6, 2, GuiItem(getArrowLeftItem()) { gui.previous() })
        gui.setItem(6, 8, GuiItem(getArrowRightItem()) { gui.next() })

        // items
        gui.addItem(
            *ItemRegistry.getAllItems().map {
                GuiItem(it.itemStack(1)) { event ->
                    event.whoClicked.inventory.addItem(it.itemStack(1))
                }
            }.toTypedArray()
        )
    }

    fun open(player: Player) {
        gui.open(player)
    }
}
