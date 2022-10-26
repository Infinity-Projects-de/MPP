package de.danielmaile.mpp.block

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.wrappers.EnumWrappers
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.isCustom
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class ListenerBlockBreak : Listener {

    // Listen for block digging packets
    init {
        ProtocolLibrary.getProtocolManager().addPacketListener(object :
            PacketAdapter(inst(), ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
            override fun onPacketReceiving(event: PacketEvent) {
                val blockPosition = event.packet.blockPositionModifier.values[0]
                val block = event.player.world.getBlockAt(blockPosition.x, blockPosition.y, blockPosition.z)
                val digType = event.packet.playerDigTypes.values[0]

                if(digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                    DamagedBlocksService.startDamaging(block)
                } else if(digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK) {
                    DamagedBlocksService.stopDamaging(block)
                }
            }
        })
    }

    // Prevent breaking of custom block
    // because it's handled by the custom system
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.player.gameMode == GameMode.CREATIVE) return
        event.isCancelled = event.block.isCustom()
    }
}