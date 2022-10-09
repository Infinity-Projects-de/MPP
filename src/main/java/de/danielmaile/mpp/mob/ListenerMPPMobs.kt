package de.danielmaile.mpp.mob

import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class ListenerMPPMobs : Listener {

    //Handle health and update display name
    @EventHandler (priority = EventPriority.HIGHEST)
    fun onEntityDamage(event: EntityDamageEvent) {
        if(event.entity !is LivingEntity) return
        val entity = event.entity as LivingEntity

        if(!entity.persistentDataContainer.has(getMPPMobNameKey())) return

        //Remove custom name before death to prevent console spamming
        if(entity.health - event.finalDamage < 0) {
            entity.customName(null)
            return
        }
        //Update display name
        updateDisplayName(event.entity as LivingEntity)
    }
}