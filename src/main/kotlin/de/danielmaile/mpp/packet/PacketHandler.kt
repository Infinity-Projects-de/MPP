/*
 * This file is part of MPP.
 * Copyright (c) 2023 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.packet

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.network.protocol.Packet
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

object PacketHandler : Listener {

    val PACKET_PREFIX = "mpp"

    // Bukkit event handlers

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        injectPlayer(event.player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        removePlayer(event.player)
    }

    // Listener registration

    val listeners: HashSet<Listener<*>> = hashSetOf()

    fun registerListeners(listener: Any) {
        listener.javaClass.declaredMethods
            .filter { it.isAnnotationPresent(PacketListener::class.java) }
            .filter { it.parameterCount == 1 && PacketEvent::class.java.isAssignableFrom(it.parameterTypes[0]) }
            .forEach {
                val packetListenerAnnotation = it.getAnnotation(PacketListener::class.java)
                val genericType = getGenericType(it.parameterTypes[0])
                listeners.add(Listener(genericType, it, packetListenerAnnotation))
            }
    }

    private fun <T> getGenericType(clazz: Class<T>): Class<*> {
        return (clazz.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>
    }

    // Packet handling

    /**
     * Injects a packet listener to a player.
     * @param player The player to be injected with
     */
    private fun injectPlayer(player: Player) {
        val channelDuplexHandler: ChannelDuplexHandler = object : ChannelDuplexHandler() {
            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                if (handlePacket(msg, player)) {
                    super.channelRead(ctx, msg)
                }
            }

            override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                if (msg != null && !handlePacket(msg, player)) return
                super.write(ctx, msg, promise)
            }
        }

        val pipeline = (player as CraftPlayer).handle.connection.connection.channel.pipeline()
        pipeline.addBefore("packet_handler", "$PACKET_PREFIX:${player.name}", channelDuplexHandler)
    }

    /**
     * Removes the injected packet listener from the player
     */

    private fun removePlayer(player: Player) {
        val channel = (player as CraftPlayer).handle.connection.connection.channel
        channel.eventLoop().submit {
            channel.pipeline().remove("$PACKET_PREFIX:${player.name}")
        }
    }

    /**
     * Sends a packet to its specific listeners
     * @param packet The packet to be sent
     * @return Whether the packet should continue or not (false = cancelled)
     */
    private fun handlePacket(packet: Any, player: Player): Boolean {
        if (packet is Packet<*>) {
            val packetEvent = PacketEvent(packet, player)
            listeners.sortedByDescending { -it.packetListener.priority.ordinal }
                .filter { it.listeningToPacket.isInstance(packet) }
                .forEach {
                    if (!it.packetListener.ignoreCancelled || !packetEvent.cancelled) {
                        it.method.invoke(packetEvent)
                    }
                }

            return !packetEvent.cancelled
        }
        return true
    }

    // Lifecycle methods

    fun enable(plugin: Plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin)
        Bukkit.getOnlinePlayers().forEach(::injectPlayer)
    }

    fun destroy() {
        Bukkit.getOnlinePlayers().forEach(::removePlayer)
    }

    // Listener data class
    data class Listener<T>(
        val listeningToPacket: Class<T>,
        val method: Method,
        val packetListener: PacketListener
    )
}
