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

package de.danielmaile.mpp.aether.mob

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import de.danielmaile.mpp.aetherWorld
import de.danielmaile.mpp.inst
import org.bukkit.entity.Llama
import org.bukkit.util.Vector

/**
 * Makes llamas controllable by players using ProtocolLib.
 */
class RideableLlama {
    init {
        ProtocolLibrary.getProtocolManager().addPacketListener(object :
            PacketAdapter(inst(), ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
            override fun onPacketReceiving(event: PacketEvent) {
                val packetContainer = event.packet
                val player = event.player
                val llama = player.vehicle ?: return

                if (llama !is Llama) return
                if (llama.location.world != aetherWorld()) return
                if (llama.inventory.decor == null) return

                // jumping
                val jump = packetContainer.booleans.read(0)
                if (jump && llama.isOnGround()) {
                    llama.velocity = llama.velocity.clone().add(Vector(0.0, inst().configManager.llamaJumpHeight, 0.0))
                }

                // calculate velocity
                val leftRight = packetContainer.float.read(0)
                val forwardBackward = packetContainer.float.read(1)

                val horizontal = Vector(-forwardBackward.toDouble(), 0.0, leftRight.toDouble())
                if (horizontal.length() > 0) {
                    // turn to face of vector in direction which the player is facing
                    horizontal.rotateAroundY(Math.toRadians((-player.location.yaw + 90f).toDouble()))

                    // scale vector
                    horizontal.normalize().multiply(inst().configManager.llamaSpeed)
                }

                val vertical = llama.getVelocity()
                vertical.setX(0)
                vertical.setZ(0)
                llama.setVelocity(horizontal.add(vertical))

                // rotation
                llama.setRotation(player.eyeLocation.yaw, player.eyeLocation.pitch)
            }
        })
    }
}