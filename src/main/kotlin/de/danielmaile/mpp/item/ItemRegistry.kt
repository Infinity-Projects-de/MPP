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

object ItemRegistry {
    // id (mpp:name) to Item
    private val items = HashMap<String,Item>()

    fun registerItems(items: Array<Item>) {
        for (item in items) {
            item.register()
        }
    }
    fun registerItem(item: Item) {
        val id = "mpp:${item.name}"
        registerItem(id, item)
    }
    fun registerItem(id: String, item: Item) {
        if (items.containsKey(id) || items.containsValue(item)) {
            throw IllegalArgumentException("Item with id: $id is already registered")
        }

        items[id] = item
    }

    private fun getNumericID(id: String): Int {
        return id.hashCode()
    }

    private fun getItemNumericID(item: Item): Int {
        return getNumericID(getItemID(item))
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