package de.danielmaile.mpp.mob

import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import kotlin.random.Random

class MPPMobSpawnManager : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onEntitySpawn(event: CreatureSpawnEvent) {
        //Only affect mobs not spawned by plugins to avoid infinity loop
        if(event.spawnReason == CreatureSpawnEvent.SpawnReason.CUSTOM) return

        val location = event.location

        //Check for entity type and replace with custom mob
        //If entity type is zombie there is a chance for a pack to spawn
        when(event.entityType) {
            EntityType.ZOMBIE -> {
                //Spawn pack
                if(Random.nextDouble() < calculatePackSpawnChance(location)) {
                    val packSize = Random.nextInt(1, calculateMaxPackSize(location) + 1)
                    val mobPack = MPPMobPack(packSize, calculatePackBaseLevel(location), 0.25)
                    mobPack.summon(location)
                } else {
                    MPPMob.ZOMBIE.apply { level = calculatePackBaseLevel(location) }.summon(location)
                }
            }
            EntityType.SKELETON -> MPPMob.SKELETON.apply { level = calculatePackBaseLevel(location) }.summon(location)
            EntityType.CREEPER -> MPPMob.CREEPER.apply { level = calculatePackBaseLevel(location) }.summon(location)
            EntityType.SPIDER -> MPPMob.SPIDER.apply { level = calculatePackBaseLevel(location) }.summon(location)
            EntityType.ENDERMAN -> MPPMob.ENDERMAN.apply { level = calculatePackBaseLevel(location) }.summon(location)
            else -> return
        }
        event.isCancelled = true
    }

    //Calculate Pack spawn chance depending on distance to spawnj
    private fun calculatePackSpawnChance(location: Location): Double {
        return when(location.distance(location.world.spawnLocation).toLong()) {
            in 0..100 -> 0.05
            in 101..300 -> 0.1
            in 301..500 -> 0.21
            in 501..800 -> 0.32
            in 801..1200 -> 0.41
            in 1201..1500 -> 0.52
            in 1501..3500 -> 0.63
            in 3501..5000 -> 0.74
            else -> 0.85
        }
    }

    //Calculate Pack size depending on distance to spawn
    private fun calculateMaxPackSize(location: Location): Int {
        return when(location.distance(location.world.spawnLocation).toLong()) {
            in 0..100 -> 3
            in 101..300 -> 5
            in 301..500 -> 8
            in 501..800 -> 12
            in 801..1200 -> 15
            in 1201..1500 -> 28
            in 1501..3500 -> 32
            in 3501..5000 -> 35
            else -> 40
        }
    }

    //Calculate Pack base level depending on distance to spawn
    private fun calculatePackBaseLevel(location: Location): Long {
        val distanceToSpawn = location.distance(location.world.spawnLocation).toLong()
        return (distanceToSpawn / 50).coerceAtLeast(5)
    }
}