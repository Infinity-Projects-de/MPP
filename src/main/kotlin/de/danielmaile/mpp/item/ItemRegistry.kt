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

import de.danielmaile.mpp.util.getDataString
import org.bukkit.inventory.ItemStack

const val MPP_ITEM_TAG_KEY = "mpp_item"
const val MPP_ITEM_PREFIX = "mpp"

object ItemRegistry {
    // id (mpp:name) to Item
    private val items = HashMap<String,Item>()

    fun registerItems(items: Array<out Item>) {
        for (item in items) {
            item.register()
        }
    }

    fun getAllItems(): Array<out Item> {
        return items.values.toTypedArray()
    }

    fun registerItem(item: Item) {
        val id = "$MPP_ITEM_PREFIX:${item.name.lowercase()}"
        registerItem(id, item)
    }

    fun registerItem(id: String, item: Item) {
        if (items.containsKey(id) || items.containsValue(item)) {
            throw IllegalArgumentException("Item with id: $id is already registered")
        }

        items[id] = item
    }

    fun getIdFromItemstack(itemStack: ItemStack): String? {
        val itemTag = itemStack.getDataString(MPP_ITEM_TAG_KEY)?: return null
        return "$MPP_ITEM_PREFIX:${itemTag.lowercase()}"
    }
    fun getItemFromItemstack(itemStack: ItemStack): Item? {
        try {
            val id = getIdFromItemstack(itemStack)?: return null
            return getItemByID(id)
        } catch (ignore: NoSuchElementException) {
            return null
        }
    }



    private fun getModelID(id: String): Int {
        return getItemByID(id).modelID
    }

    private fun getItemID(item: Item): String {
        for (entry in items.entries) {
            if (entry.value == item) return entry.key
        }
        throw NoSuchElementException("Item has not been registered") // Maybe create a more suitable Exception
    }


    /* No real use tbh for neither of both methods */
    private fun getItemByNumericID(id: Int): Item {
        for (entry in items.entries) {
            if (entry.key.hashCode() == id) return entry.value
        }
        throw NoSuchElementException()
    }

    private fun getItemByID(id: String): Item {
        return items[id] ?: throw NoSuchElementException()
    }

}