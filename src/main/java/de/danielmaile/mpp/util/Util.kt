package de.danielmaile.mpp.util

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ArmorSet
import de.danielmaile.mpp.item.ItemType
import org.bukkit.FluidCollisionMode
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.io.IOException
import java.io.InputStream
import java.text.CompactNumberFormat
import java.text.NumberFormat
import java.util.*


fun logInfo(message: String) {
    inst().logger.info(message)
}

fun logWarning(message: String) {
    inst().logger.warning(message)
}

fun logError(message: String) {
    inst().logger.severe(message)
}

@Throws(IOException::class)
fun getResource(fileName: String): InputStream? {
    return inst().javaClass.classLoader.getResourceAsStream(fileName)
}

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

fun Player.isGrounded(): Boolean {
    return this.getBlockBelow().type != Material.AIR
}

fun Player.getBlockBelow(): Block {
    return location.block.getRelative(BlockFace.DOWN)
}

fun Player.setMaximumHealth(value: Double) {
    getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = value
}

fun Player.addPermEffect(type: PotionEffectType) {
    addPotionEffect(PotionEffect(type, Int.MAX_VALUE, 0, false, false))
}

/**
 * @return nearest object (block or entity) in sight of player or null if none was found
 */
fun Player.getNearestObjectInSight(range: Int): Any? {
    val block = getTargetBlockExact(range, FluidCollisionMode.NEVER)
    val entity = getTargetEntity(range)

    val blockRange = if (block != null) location.distance(block.location) else Double.MAX_VALUE
    val entityRange = if (entity != null) location.distance(entity.location) else Double.MAX_VALUE

    return if (entity == null && block == null) null else (if (blockRange < entityRange) block else entity)
}

fun Player.getEquippedArmorSet(): ArmorSet? {
    val headType = ItemType.fromTag(this.equipment.helmet)
    val chestType = ItemType.fromTag(this.equipment.chestplate)
    val legsType = ItemType.fromTag(this.equipment.leggings)
    val feetType = ItemType.fromTag(this.equipment.boots)

    return ArmorSet.values().firstOrNull {
        headType == it.head && chestType == it.chest && legsType == it.legs && feetType == it.feet
    }
}

//Returns the block face of the direction the player is facing (only yaw)
fun Player.getDirection(): BlockFace {
    val yaw = this.location.yaw
    return if (yaw > 135 || yaw <= -135) BlockFace.NORTH else if (yaw > -135 && yaw <= -45) BlockFace.EAST else if (yaw > -45 && yaw <= 45) BlockFace.SOUTH else BlockFace.WEST
}

fun String.isLong(): Boolean {
    return try {
        java.lang.Long.parseLong(this)
        true
    } catch (exception: java.lang.NumberFormatException) {
        false
    }
}

fun Long.abbreviateNumber(): String {
    val locale = Locale.forLanguageTag(inst().config.getString("language_file"))
    return CompactNumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.SHORT).format(this).replace('\u00a0', ' ')
}