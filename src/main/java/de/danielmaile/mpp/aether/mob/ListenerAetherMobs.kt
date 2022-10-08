package de.danielmaile.mpp.aether.mob

import de.danielmaile.mpp.aetherWorld
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.EntitiesLoadEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class ListenerAetherMobs : Listener {

    @EventHandler
    fun onEntitiesLoad(event: EntitiesLoadEvent) {
        if (event.world != aetherWorld()) return
        for (entity in event.entities) {
            if (!entity.isValid) continue
            if (entity !is LivingEntity) continue
            if (entity is Player) continue

            entity.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 0, false, false))
        }
    }
}