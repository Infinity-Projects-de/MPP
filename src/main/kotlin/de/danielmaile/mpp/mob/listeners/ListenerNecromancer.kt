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
import de.danielmaile.mpp.mob.getFromEntity
import de.danielmaile.mpp.mob.getLevelFromEntity
import org.bukkit.Effect
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.scheduler.BukkitRunnable

class ListenerNecromancer : ListenerMob(MPPMob.NECROMANCER, MPPMob.NECROMANCER_ELITE) {
    
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
                }
            }.runTaskLater(inst(), 100)
        }
    }
}
