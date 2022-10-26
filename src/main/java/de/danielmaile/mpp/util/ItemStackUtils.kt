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