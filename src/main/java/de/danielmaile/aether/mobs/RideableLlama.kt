package de.danielmaile.aether.mobs

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import de.danielmaile.aether.inst
import org.bukkit.entity.Llama
import org.bukkit.util.Vector

class RideableLlama(protocolManager: ProtocolManager) {
    init {
        protocolManager.addPacketListener(object :
            PacketAdapter(inst(), ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
            override fun onPacketReceiving(event: PacketEvent) {
                val packetContainer = event.packet
                val player = event.player
                val llama = player.vehicle ?: return

                if (llama !is Llama) return
                if (llama.location.world != inst().aetherWorld.getWorld()) return
                if (llama.inventory.decor == null) return

                //Jumping
                val jump = packetContainer.booleans.read(0)
                if (jump && llama.isOnGround()) {
                    llama.velocity = llama.velocity.clone().add(Vector(0.0, inst().configManager.llamaJumpHeight, 0.0))
                }

                //Calculate velocity
                val leftRight = packetContainer.float.read(0)
                val forwardBackward = packetContainer.float.read(1)

                val horizontal = Vector(-forwardBackward.toDouble(), 0.0, leftRight.toDouble())
                if (horizontal.length() > 0) {
                    //Turn to face of vector in direction which the player is facing
                    horizontal.rotateAroundY(Math.toRadians((-player.location.yaw + 90f).toDouble()))

                    //Scale vector
                    horizontal.normalize().multiply(inst().configManager.llamaSpeed)
                }

                val vertical = llama.getVelocity()
                vertical.setX(0)
                vertical.setZ(0)
                llama.setVelocity(horizontal.add(vertical))

                //Rotation
                llama.setRotation(player.eyeLocation.yaw, player.eyeLocation.pitch)
            }
        })
    }
}