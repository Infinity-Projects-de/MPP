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

import de.danielmaile.mpp.util.doesKeyExist
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent

class ListenerCrafting : Listener {

    // check for crafting of vanilla items with mpp items
    @EventHandler
    fun onCraftPrepare(event: PrepareItemCraftEvent) {
        val result = event.inventory.result ?: return
        if (result.doesKeyExist(MPP_ITEM_TAG_KEY)) return
        val ingredients = event.inventory.contents

        for (ingredient in ingredients) {
            if (ingredient == null || ingredient.type == Material.AIR) continue
            if (ingredient.doesKeyExist(MPP_ITEM_TAG_KEY)) {
                event.inventory.result = null
                return
            }
        }
    }
}