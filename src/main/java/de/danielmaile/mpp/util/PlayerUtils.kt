package de.danielmaile.mpp.util

import de.danielmaile.mpp.item.ArmorSet
import de.danielmaile.mpp.item.ItemType
import net.minecraft.network.protocol.Packet
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.FluidCollisionMode
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

val Player.serverPlayer: ServerPlayer
    get() = (this as CraftPlayer).handle

val Player.connection: ServerGamePacketListenerImpl
    get() = serverPlayer.connection

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

// Returns the block face of the direction the player is facing (only yaw)
fun Player.getDirection(): BlockFace {
    val yaw = this.location.yaw
    return if (yaw > 135 || yaw <= -135) BlockFace.NORTH else if (yaw > -135 && yaw <= -45) BlockFace.EAST else if (yaw > -45 && yaw <= 45) BlockFace.SOUTH else BlockFace.WEST
}

// Sends all given packets to player
fun Player.sendPackets(vararg packets: Packet<*>) {
    val connection = connection
    packets.forEach { connection.send(it) }
}

fun Player.getPotionEffectLevel(potionEffectType: PotionEffectType): Int {
    val portionEffect = this.getPotionEffect(potionEffectType) ?: return 0
    return portionEffect.amplifier + 1
}