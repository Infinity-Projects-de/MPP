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
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

object PacketHandler : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        injectPlayer(event.player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        removePlayer(event.player)
    }

    val listeners: HashMap<Method, PacketListener> = hashMapOf()
    fun registerListeners(listener: Any) {
        listener.javaClass.declaredMethods
            .filter { it.isAnnotationPresent(PacketListener::class.java) }
            .filter { it.parameterCount == 1 && PacketEvent::class.java.isAssignableFrom(it.parameterTypes[0]) }
            .forEach {
                val packetListenerAnnotation = it.getAnnotation(PacketListener::class.java)
                listeners[it] = packetListenerAnnotation
            }
    }

    private fun <T> getGenericType(clazz: Class<T>): Class<*> {
        return (clazz.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>
    }

    private fun removePlayer(player: Player) {
        val channel = (player as CraftPlayer).handle.connection.connection.channel
        channel.eventLoop().submit {
            channel.pipeline().remove(player.name)
        }
    }

    private fun injectPlayer(player: Player) {
        val channelDuplexHandler: ChannelDuplexHandler = object : ChannelDuplexHandler() {
            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                if (msg is Packet<*>) {
                    val packetEvent = PacketEvent<Packet<*>>(msg)

                    listeners.entries.sortedByDescending { -it.value.priority.ordinal }
                        .filter { !it.value.ignoreCancelled || !packetEvent.cancelled }
                        .filter { getGenericType(it.key.parameterTypes[0]).isInstance(msg) }
                        .forEach { it.key.invoke(packetEvent) }

                    if (packetEvent.cancelled) return
                }

                super.channelRead(ctx, msg)
            }

            override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                if (msg is Packet<*>) {
                    val packetEvent = PacketEvent<Packet<*>>(msg)

                    listeners.entries.sortedByDescending { -it.value.priority.ordinal }
                        .filter { !it.value.ignoreCancelled || !packetEvent.cancelled }
                        .filter { getGenericType(it.key.parameterTypes[0]).isInstance(msg) }
                        .forEach { it.key.invoke(packetEvent) }

                    if (packetEvent.cancelled) return
                }
                super.write(ctx, msg, promise)
            }
        }

        val pipeline = (player as CraftPlayer).handle.connection.connection.channel.pipeline()
        pipeline.addBefore("packet_handler", player.name, channelDuplexHandler)
    }
}
