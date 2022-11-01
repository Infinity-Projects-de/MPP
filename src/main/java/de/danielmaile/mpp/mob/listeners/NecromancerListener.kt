package de.danielmaile.mpp.mob.listeners

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.mob.MPPMob
import de.danielmaile.mpp.mob.getFromEntity
import de.danielmaile.mpp.mob.getLevelFromEntity
import org.bukkit.Effect
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.scheduler.BukkitRunnable

class NecromancerListener : MobListener(MPPMob.NECROMANCER, MPPMob.NECROMANCER_ELITE) {

    @EventHandler
    fun onDeath(event: EntityDeathEvent) {
        val died = getFromEntity(event.entity) ?: return
        val necromancer = event.entity.getNearbyEntities(5.0, 5.0, 5.0)
            .firstOrNull { e ->
                shouldListen(e)
            }
        necromancer?.let {
            val location = event.entity.location
            val levelDied = getLevelFromEntity(event.entity)
            object : BukkitRunnable() {
                override fun run() {
                    if (necromancer.isDead) return
                    died.summon(location, levelDied)
                    necromancer.world.playEffect(necromancer.location.add(0.0, 2.0, 0.0), Effect.ELECTRIC_SPARK, 2)
                    //TODO add particle trail to necromancer to show that he spawns it and maybe play a sound
                }
            }.runTaskLater(inst(), 100)
        }
    }
}