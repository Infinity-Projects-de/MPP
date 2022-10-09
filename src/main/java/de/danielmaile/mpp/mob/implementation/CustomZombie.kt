package de.danielmaile.mpp.mob.implementation

import de.danielmaile.mpp.mob.*
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.monster.Zombie
import org.bukkit.Location
import org.bukkit.attribute.Attribute
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.entity.LivingEntity
import org.bukkit.persistence.PersistentDataType

class CustomZombie(
    mppMob: MPPMob,
    location: Location) : Zombie(EntityType.ZOMBIE, (location.world as CraftWorld).handle) {

    init {
        val bukkitEntity = this.bukkitEntity as LivingEntity
        setPos(location.x, location.y, location.z)

        //Set health based on level
        val baseHealth = bukkitEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue * mppMob.baseHPMultiplier
        val maxHealth = baseHealth * (1 + (mppMob.level * mppMob.hpMultiplier))
        bukkitEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = maxHealth
        bukkitEntity.health = maxHealth

        //Set movement speed
        val movementSpeed = bukkitEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)!!.baseValue * mppMob.speedMultiplier
        bukkitEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)!!.baseValue = movementSpeed

        //Set attack damage based on level
        val baseDamage = bukkitEntity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)!!.baseValue * mppMob.baseAttackDamageMultiplier
        val attackDamage = baseDamage * (1 + (mppMob.level * mppMob.attackDamageMultiplier))
        bukkitEntity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)!!.baseValue = attackDamage

        //Set persistent data
        bukkitEntity.persistentDataContainer.set(getMPPMobNameKey(), PersistentDataType.STRING, mppMob.name)
        bukkitEntity.persistentDataContainer.set(getMPPMobLevelKey(), PersistentDataType.LONG, mppMob.level)

        //Spawn entity
        (location.world as CraftWorld).handle.addFreshEntity(this)

        //Update display name
        updateDisplayName(bukkitEntity)
    }
}