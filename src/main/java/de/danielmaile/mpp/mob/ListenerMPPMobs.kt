package de.danielmaile.mpp.mob

import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent

class ListenerMPPMobs : Listener {

    //Handle health and update display name
    @EventHandler (priority = EventPriority.HIGHEST)
    fun onEntityDamage(event: EntityDamageEvent) {
        if(event.entity !is LivingEntity) return
        val entity = event.entity as LivingEntity

        if(!entity.persistentDataContainer.has(getMPPMobNameKey())) return

        //Update display name
        updateDisplayName(event.entity as LivingEntity)
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    fun onCustomMobDeath(event: EntityDeathEvent) {
        if(!event.entity.persistentDataContainer.has(getMPPMobNameKey())) return

        //Remove custom name before death to prevent console spamming
        event.entity.customName(null)

        val mppMob = getFromEntity(event.entity)
        val level = getLevelFromEntity(event.entity)

        if(mppMob == MPPMob.MOTHER || mppMob == MPPMob.MOTHER_ELITE) {
            for(i in 1..3) {
                MPPMob.CHILD.summon(event.entity.location, level)
            }
        }
    }
}