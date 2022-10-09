package de.danielmaile.mpp.mob

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.mob.implementation.CustomZombie
import de.danielmaile.mpp.util.abbreviateNumber
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.persistence.PersistentDataType
import kotlin.math.ceil

enum class MPPMob(
    private val entityType: EntityType,
    val baseHPMultiplier: Float,
    val hpMultiplier: Float,
    val speedMultiplier: Float,
    val baseAttackDamageMultiplier: Float,
    val attackDamageMultiplier: Float,
    var level: Long
) {

    TANK(EntityType.ZOMBIE, 2.5f, 0.025f, 0.5f, 1.8f,0.001f, 1);

    fun summon(location: Location) {
        if(entityType == EntityType.ZOMBIE) {
            CustomZombie(this, location)
        } else {
            throw IllegalArgumentException("Entity Type $entityType not supported!")
        }
    }
}

fun updateDisplayName(entity: LivingEntity) {
    if(!entity.persistentDataContainer.has(getMPPMobNameKey())) return

    try {
        val mppMob = MPPMob.valueOf(entity.persistentDataContainer.get(getMPPMobNameKey(), PersistentDataType.STRING)!!)
        val level = entity.persistentDataContainer.get(getMPPMobLevelKey(), PersistentDataType.LONG)!!
        val health = ceil(entity.health / 2)

        val tagResolver = TagResolver.resolver(
            Placeholder.parsed("level", level.abbreviateNumber()),
            Placeholder.parsed("health", health.toLong().abbreviateNumber())
        )

        val displayName = inst().getLanguageManager().getComponent(
            "mobs.${mppMob.name}.name",
            tagResolver
        )

        entity.customName(displayName)
    } catch (exception: IllegalArgumentException) {
        return
    }
}

fun getMPPMobNameKey(): NamespacedKey {
    return NamespacedKey(inst(), "MPPMobName")
}

fun getMPPMobLevelKey(): NamespacedKey {
    return NamespacedKey(inst(), "MPPMobLevel")
}