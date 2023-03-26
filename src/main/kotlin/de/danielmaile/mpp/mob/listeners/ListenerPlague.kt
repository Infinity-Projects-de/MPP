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

import de.danielmaile.mpp.mob.MPPMob
import de.danielmaile.mpp.mob.getFromEntity
import de.danielmaile.mpp.mob.getLevelFromEntity
import org.bukkit.Effect
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Tameable
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDeathEvent

class ListenerPlague : ListenerMob(MPPMob.PLAGUE, MPPMob.PLAGUE_ELITE) {

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
                    // play plaque particle effect
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
