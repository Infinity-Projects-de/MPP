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

package de.danielmaile.mpp.mob.listeners

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.mob.MPPMob
import de.danielmaile.mpp.mob.getLevelFromEntity
import de.danielmaile.mpp.mob.updateDisplayName
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Vibration
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class HealerListener : MobListener(MPPMob.HEALER, MPPMob.HEALER_ELITE) {

    init {
        object : BukkitRunnable() {
            override fun run() {
                for (world in inst().server.worlds)
                    for (healer in world.livingEntities) {
                        if (!shouldListen(healer)) continue
                        val healAmount = 2.0 + ((getLevelFromEntity(healer) - 1).toDouble() / 5.0)
                        // heal itself
                        healer.health =
                            (healer.health + healAmount).coerceAtMost(healer.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value)
                        updateDisplayName(healer)

                        // heal nearby mobs
                        val nearbyEntities = healer.getNearbyEntities(5.0, 5.0, 5.0).filterIsInstance<LivingEntity>()
                        for (entity in nearbyEntities) {
                            if (entity is Player || entity.isDead) continue
                            val maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
                            if (maxHealth - entity.health > 0.1) {
                                val tickDelay = 10
                                // shoot healing beam to entity from healer
                                spawnBeams(healer.location, entity, tickDelay)

                                // heal and play heart when beam arrives
                                object : BukkitRunnable() {
                                    override fun run() {
                                        if (entity.isDead) return
                                        entity.health = (entity.health + healAmount).coerceAtMost(maxHealth)
                                        spawnHearts(entity.location.add(0.0, 2.0, 0.0))
                                        updateDisplayName(entity)
                                    }
                                }.runTaskLater(inst(), tickDelay.toLong())
                            }
                        }
                    }
            }
        }.runTaskTimer(inst(), 20, 20)
    }

    private fun spawnBeams(location: Location, target: Entity, beamTime: Int) {
        object : BukkitRunnable() {
            var count = 0

            override fun run() {
                val vibration = Vibration(Vibration.Destination.EntityDestination(target), beamTime)
                location.world.spawnParticle(
                    Particle.VIBRATION, location.clone().add(0.0, 1.6, 0.0), 1, vibration
                )

                if (count++ == 5) {
                    cancel()
                }
            }
        }.runTaskTimer(inst(), 0, 2)
    }

    private fun spawnHearts(location: Location) {
        object : BukkitRunnable() {
            var count = 0

            override fun run() {
                location.world.spawnParticle(
                    Particle.HEART,
                    location,
                    1
                )

                if (count++ == 5) {
                    cancel()
                }
            }
        }.runTaskTimer(inst(), 0, 2)
    }
}