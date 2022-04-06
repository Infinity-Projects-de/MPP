package de.danielmaile.aether.util

import de.danielmaile.aether.inst
import org.bukkit.FluidCollisionMode
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.block.BlockFace
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.io.IOException
import java.io.InputStream

fun logInfo(message: String) {
    inst().logger.info(message)
}

fun logError(message: String) {
    inst().logger.severe(message)
}

@Throws(IOException::class)
fun getResource(fileName: String): InputStream? {
    return inst().javaClass.classLoader.getResourceAsStream(fileName)
}

fun ItemStack.hasKey(key: String): Boolean {
    return CraftItemStack.asNMSCopy(this).orCreateTag.contains(key)
}

fun ItemStack.getNBTString(key: String): String {
    return CraftItemStack.asNMSCopy(this).orCreateTag.getString(key)
}

fun ItemStack.setNBTString(key: String, value: String): ItemStack {
    val nmsStack = CraftItemStack.asNMSCopy(this)
    val compoundTag = nmsStack.orCreateTag
    compoundTag.putString(key, value)
    nmsStack.tag = compoundTag
    return CraftItemStack.asBukkitCopy(nmsStack)
}

fun Player.isGrounded(): Boolean {
    return location.block.getRelative(BlockFace.DOWN).type != Material.AIR
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