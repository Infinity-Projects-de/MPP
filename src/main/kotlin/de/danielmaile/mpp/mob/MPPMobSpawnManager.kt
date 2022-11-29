/*
 * This file is part of MPP.
 * Copyright (c) 2022 by it's authors. All rights reserved.
 * MPP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MPP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MPP.  If not, see <http://www.gnu.org/licenses/>.
 */

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
        // only affect mobs not spawned by plugins to avoid infinity loop
        if(event.spawnReason == CreatureSpawnEvent.SpawnReason.CUSTOM) return

        val location = event.location

        // check for entity type and replace with custom mob
        // if entity type is zombie there is a chance for a pack to spawn
        when(event.entityType) {
            EntityType.ZOMBIE -> {
                // spawn pack
                if(Random.nextDouble() < calculatePackSpawnChance(location)) {
                    val packSize = Random.nextInt(1, calculateMaxPackSize(location) + 1)
                    val mobPack = MPPMobPack(packSize, calculatePackBaseLevel(location), 0.25)
                    mobPack.summon(location)
                } else {
                    MPPMob.ZOMBIE.summon(location, calculatePackBaseLevel(location))
                }
            }
            EntityType.SKELETON -> MPPMob.SKELETON.summon(location, calculatePackBaseLevel(location))
            EntityType.CREEPER -> MPPMob.CREEPER.summon(location, calculatePackBaseLevel(location))
            EntityType.SPIDER -> MPPMob.SPIDER.summon(location, calculatePackBaseLevel(location))
            EntityType.ENDERMAN -> MPPMob.ENDERMAN.summon(location, calculatePackBaseLevel(location))
            else -> return
        }
        event.isCancelled = true
    }

    // calculate Pack spawn chance depending on distance to spawn
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

    // calculate Pack size depending on distance to spawn
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

    // calculate Pack base level depending on distance to spawn
    private fun calculatePackBaseLevel(location: Location): Long {
        val distanceToSpawn = location.distance(location.world.spawnLocation).toLong()
        return (distanceToSpawn / 50).coerceAtLeast(5)
    }
}