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

package de.danielmaile.mpp.item

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.setDataString
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

interface Item {
    val name: String
    val material: Material
    fun register() {
        ItemRegistry.registerItem(this)
    }

    private val displayName: Component
        get() = inst().getLanguageManager().getComponent("items.$name.name")
            .decoration(TextDecoration.ITALIC, false)

    private val description: List<Component>
        get() = inst().getLanguageManager().getComponentList("items.$name.description")
            .map { it -> it.decoration(TextDecoration.ITALIC, false) }

    val modelID: Int
        get() = name.hashCode() // FIXME: Method has not been tested

    val itemStack: (Int) -> ItemStack
        get() = {
            ItemStack(material).apply {
                itemMeta = itemMeta.apply {
                    displayName(this@Item.displayName)
                    lore(description)
                    setCustomModelData(modelID)
                    modifySpecialItemMeta(this)
                }
                setDataString(MPP_ITEM_TAG_KEY, name)
                amount = it
            }
        }

    fun modifySpecialItemMeta(itemMeta: ItemMeta) {
    }
}
