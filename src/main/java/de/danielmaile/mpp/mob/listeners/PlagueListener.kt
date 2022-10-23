package de.danielmaile.mpp.mob.listeners

import de.danielmaile.mpp.mob.MPPMob
import de.danielmaile.mpp.mob.getFromEntity
import de.danielmaile.mpp.mob.getLevelFromEntity
import org.bukkit.Effect
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Tameable
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDeathEvent

class PlagueListener : MobListener(MPPMob.PLAGUE, MPPMob.PLAGUE_ELITE) {

    @EventHandler
    fun onDeath(event: EntityDeathEvent) {
        if (!shouldListen(event.entity)) return
        val plagueMobType = getFromEntity(event.entity)!!
        val level = getLevelFromEntity(event.entity)

        val nearbyEntities = event.entity.getNearbyEntities(5.0, 5.0, 5.0).filterIsInstance<LivingEntity>()
        nearbyEntities.forEach { e ->
            if (e.isDead || e is Player) return@forEach
            val mppMob = getFromEntity(e)
            if (mppMob == null) {
                if (e.customName() == null && (e !is Tameable || !e.isTamed)) {
                    e.remove()
                    plagueMobType.summon(e.location, level)
                    //Play plaque particle effect
                    e.world.playEffect(e.location.add(0.0, 2.0, 0.0), Effect.ZOMBIE_CONVERTED_VILLAGER, 1)
                    e.world.playEffect(e.location.add(0.0, 1.0, 0.0), Effect.SMOKE, 1)
                }
            } else {
                e.remove()
                plagueMobType.summon(e.location, getLevelFromEntity(e))
                e.world.playEffect(e.location.add(0.0, 2.0, 0.0), Effect.ZOMBIE_CONVERTED_VILLAGER, 1)
                e.world.playEffect(e.location.add(0.0, 1.0, 0.0), Effect.SMOKE, 1)
            }
        }
    }
}