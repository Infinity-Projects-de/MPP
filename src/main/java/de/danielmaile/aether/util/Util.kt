package de.danielmaile.aether.util

import de.danielmaile.aether.Aether
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
    Aether.instance.logger.info(message)
}

fun logError(message: String) {
    Aether.instance.logger.severe(message)
}

@Throws(IOException::class)
fun getResource(fileName: String) : InputStream? {
    return Aether.instance.javaClass.classLoader.getResourceAsStream(fileName)
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
    return this.location.block.getRelative(BlockFace.DOWN).type != Material.AIR
}

fun Player.setMaximumHealth(value: Double) {
    this.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = value
}

fun Player.addPermEffect(type: PotionEffectType) {
    this.addPotionEffect(PotionEffect(type, Int.MAX_VALUE, 0, false, false))
}
