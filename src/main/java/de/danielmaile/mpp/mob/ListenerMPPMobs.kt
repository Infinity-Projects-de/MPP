package de.danielmaile.mpp.mob

import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.persistence.PersistentDataType

class ListenerMPPMobs : Listener {

    //Handle health and update display name
    @EventHandler (priority = EventPriority.HIGHEST)
    fun onEntityDamage(event: EntityDamageEvent) {
        if(event.entity !is LivingEntity) return
        val entity = event.entity as LivingEntity

        if(!entity.persistentDataContainer.has(getMPPMobNameKey())) return

        var health = entity.persistentDataContainer.get(getMPPMobHealthKey(), PersistentDataType.DOUBLE)!!
        health -= event.finalDamage

        //Entity dies
        if(health < 0) {
            //Remove custom name before death to prevent console spamming
            entity.customName(null)
            return
        }

        //Set damage to 0 and set health of mob
        event.damage = 0.0
        entity.health = health.coerceAtMost(2048.0)
        entity.persistentDataContainer.set(getMPPMobHealthKey(), PersistentDataType.DOUBLE, health)

        //Update display name
        updateDisplayName(event.entity as LivingEntity)
    }
}