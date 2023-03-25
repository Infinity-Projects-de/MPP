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

package de.danielmaile.mpp.util

import de.danielmaile.mpp.inst
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun ItemStack.doesKeyExist(key: String): Boolean {
    val namespacedKey = NamespacedKey(inst(), key)
    return this.doesKeyExist(namespacedKey)
}

fun ItemStack.getDataString(key: String): String? {
    val namespacedKey = NamespacedKey(inst(), key)
    return this.getData(namespacedKey, PersistentDataType.STRING)
}

fun ItemStack.setDataString(key: String, value: String) {
    val namespacedKey = NamespacedKey(inst(), key)
    this.setData(namespacedKey, PersistentDataType.STRING, value)
}

private fun ItemStack.doesKeyExist(key: NamespacedKey): Boolean {
    val itemMeta = this.itemMeta ?: return false
    return itemMeta.persistentDataContainer.has(key)
}

private fun <T, Z> ItemStack.getData(key: NamespacedKey, type: PersistentDataType<T, Z>): Z? {
    val itemMeta = this.itemMeta ?: return null
    return itemMeta.persistentDataContainer.get(key, type)
}

private fun <T, Z : Any> ItemStack.setData(key: NamespacedKey, type: PersistentDataType<T, Z>, value: Z) {
    val itemMeta = this.itemMeta ?: return
    itemMeta.persistentDataContainer.set(key, type, value)
    this.itemMeta = itemMeta
}

fun ItemStack.setUnbreakable() {
    val meta = this.itemMeta
    meta.isUnbreakable = true
    this.itemMeta = meta
}
