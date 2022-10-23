package de.danielmaile.mpp.event

import de.danielmaile.mpp.mob.MPPMob
import org.bukkit.entity.LivingEntity
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent

class MPPMobSpawnEvent(entity: LivingEntity, val mob: MPPMob) : EntityEvent(entity) {

    companion object {
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList? {
            return handlerList
        }
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    override fun getEntity(): LivingEntity {
        return super.getEntity() as LivingEntity
    }
}