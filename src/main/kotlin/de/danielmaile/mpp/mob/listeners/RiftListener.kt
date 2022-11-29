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
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import java.util.*

class RiftListener : MobListener(MPPMob.RIFT, MPPMob.RIFT_ELITE) {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onDamage(event: EntityDamageByEntityEvent) {
        if (!shouldListen(event.entity)) return
        if (event.damage >= (event.entity as LivingEntity).health) return

        val heights = intArrayOf(0, 1, -1, 2, -2, 3, -3, 4, -4)
        for (height in heights) {
            // make 8 attempts per height to teleport the rift
            for (i in 0..8) {
                val loc = getRandomLocationPlateau(
                    event.entity.location.add(0.0, height.toDouble(), 0.0)
                )
                if (canTeleport(loc.block)) {
                    event.entity.teleport(loc)
                    return
                }
            }
        }

    }

    private fun canTeleport(block: Block): Boolean {
        return block.isPassable && block.getRelative(BlockFace.DOWN).isSolid
                && block.getRelative(BlockFace.UP).isPassable
    }

    private fun getRandomLocationPlateau(location: Location): Location {
        return location.add(Random().nextDouble(20.0) - 10.0, 0.0, Random().nextDouble(20.0) - 10.0)
    }
}