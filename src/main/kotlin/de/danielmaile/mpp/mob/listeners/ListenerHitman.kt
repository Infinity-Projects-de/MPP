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

import de.danielmaile.mpp.event.MPPMobSpawnEvent
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.mob.MPPMob
import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

class ListenerHitman : ListenerMob(MPPMob.HITMAN, MPPMob.HITMAN_ELITE) {

    private val hidden = ArrayList<Int>()

    init {
        for (world in inst().server.worlds)
            for (entity in world.livingEntities) {
                if (shouldListen(entity as LivingEntity)) {
                    hidden.add(entity.entityId)
                    for (player in inst().server.onlinePlayers)
                        player.hideEntity(inst(), entity)
                }
            }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        for (world in inst().server.worlds)
            for (entity in world.livingEntities) {
                if (hidden.contains(entity.entityId)) {
                    event.player.hideEntity(inst(), entity)
                }
            }
    }

    @EventHandler
    fun onMobSpawn(event: MPPMobSpawnEvent) {
        if (shouldListen(event.mob)) {
            hidden.add(event.entity.entityId)
            for (player in inst().server.onlinePlayers)
                player.hideEntity(inst(), event.entity)
        }
    }

    @EventHandler
    fun onMoveEntity(event: EntityMoveEvent) {
        // do not calc for small movements
        if (!event.hasChangedBlock() || !hidden.contains(event.entity.entityId)) return
        if (event.entity.getNearbyEntities(5.0, 5.0, 5.0).filterIsInstance<Player>().isNotEmpty()) {
            hidden.remove(event.entity.entityId)
            for (player in inst().server.onlinePlayers) {
                player.showEntity(inst(), event.entity)
            }
        }
    }

    @EventHandler
    fun onMovePlayer(event: PlayerMoveEvent) {
        // do not calc for small movements
        if (!event.hasChangedBlock()) return
        val nearby = event.player.getNearbyEntities(5.0, 5.0, 5.0).filterIsInstance<LivingEntity>()
        if (nearby.isNotEmpty()) {
            for (entity in nearby) {
                if (hidden.contains(entity.entityId)) {
                    hidden.remove(entity.entityId)
                    for (player in inst().server.onlinePlayers) {
                        player.showEntity(inst(), entity)
                    }
                }
            }
        }
    }
}
