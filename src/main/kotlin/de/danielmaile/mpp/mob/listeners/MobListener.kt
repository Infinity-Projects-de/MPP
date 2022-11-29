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
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Listener

abstract class MobListener(private vararg val mobs: MPPMob) : Listener {

    fun shouldListen(entity: Entity?): Boolean {
        if (entity !is LivingEntity) return false
        val mob = getFromEntity(entity) ?: return false
        return mobs.contains(mob)
    }

    fun shouldListen(mob: MPPMob?): Boolean {
        if (mob == null) return false
        return mobs.contains(mob)
    }

}