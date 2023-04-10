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
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket
import net.minecraft.network.protocol.game.ClientboundSetTimePacket
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PacketHandler : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        injectPlayer(event.player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        removePlayer(event.player)
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
                if (msg is ServerboundPlayerActionPacket) {
                    Bukkit.getServer().consoleSender.sendMessage("Packet read: $msg")
                    player.sendMessage("Action: ${msg.action.name}")
                    player.sendMessage("Position: ${msg.pos.x} ${msg.pos.y} ${msg.pos.z}")
                    player.sendMessage("---")
                }
                super.channelRead(ctx, msg)
            }

            override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                if (msg !is ClientboundSetTimePacket && msg !is ClientboundSetEntityMotionPacket && msg !is ClientboundMoveEntityPacket && msg !is ClientboundSystemChatPacket) {
                    if (msg is ClientboundBlockDestructionPacket) {
                        player.sendMessage("Ignored clientbound destruction packet of progress ${msg.progress}")

                        return
                    }
                    Bukkit.getServer().consoleSender.sendMessage("Packet write: $msg")
                }
                super.write(ctx, msg, promise)
            }
        }

        val pipeline = (player as CraftPlayer).handle.connection.connection.channel.pipeline()
        pipeline.addBefore("packet_handler", player.name, channelDuplexHandler)
    }
}
