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

import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent

class ListenerMPPMobs : Listener {

    // handle health and update display name
    @EventHandler (priority = EventPriority.MONITOR)
    fun onEntityDamage(event: EntityDamageEvent) {
        if(event.entity !is LivingEntity) return
        val entity = event.entity as LivingEntity

        // update display name
        updateDisplayName(entity, entity.health - event.damage)
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    fun onCustomMobDeath(event: EntityDeathEvent) {
        if(!event.entity.persistentDataContainer.has(getMPPMobNameKey())) return

        // remove custom name before death to prevent console spamming
        event.entity.customName(null)

        val mppMob = getFromEntity(event.entity)
        val level = getLevelFromEntity(event.entity)

        if(mppMob == MPPMob.MOTHER || mppMob == MPPMob.MOTHER_ELITE) {
            for(i in 1..3) {
                MPPMob.CHILD.summon(event.entity.location, level)
            }
        }
    }
}