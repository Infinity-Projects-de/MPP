package de.danielmaile.mpp.mob.listeners

import de.danielmaile.mpp.mob.MPPMob
import de.danielmaile.mpp.mob.getFromEntity
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Listener

abstract class MobListener(private vararg val mobs: MPPMob) : Listener {

    fun shouldListen(entity: Entity?): Boolean {
        if (entity !is LivingEntity) return false
        val mob = getFromEntity(entity) ?: return false
        return mobs.contains(mob)
    }

    fun shouldListen(mob: MPPMob?): Boolean {
        if (mob == null) return false
        return mobs.contains(mob)
    }

}