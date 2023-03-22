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

package de.danielmaile.mpp.item.function.particle

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ItemType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class ListenerParticle : Listener {

    @EventHandler
    fun onChangeArmor(event: PlayerArmorChangeEvent) {
        val player = event.player
        val oldItemType = ItemType.fromTag(event.oldItem)
        val newItemType = ItemType.fromTag(event.newItem)

        // player gets ring above head when wearing valkyrie ring
        if (oldItemType == ItemType.VALKYRE_RING) {
            inst().particleManager.removeParticleType(player, ParticleManager.ParticleType.VALKYRE_RING)
        }
        if (newItemType == ItemType.VALKYRE_RING) {
            inst().particleManager.addParticleType(player, ParticleManager.ParticleType.VALKYRE_RING)
        }

        // player gets wings when wearing valkyrie wings
        if (oldItemType == ItemType.VALKYRE_WINGS) {
            inst().particleManager.removeParticleType(player, ParticleManager.ParticleType.VALKYRE_WINGS)
        }
        if (newItemType == ItemType.VALKYRE_WINGS) {
            inst().particleManager.addParticleType(player, ParticleManager.ParticleType.VALKYRE_WINGS)
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        inst().particleManager.removePlayer(event.player)
    }
}