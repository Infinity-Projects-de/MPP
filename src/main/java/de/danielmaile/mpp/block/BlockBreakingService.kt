package de.danielmaile.mpp.block

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.wrappers.EnumWrappers
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ItemType
import de.danielmaile.mpp.util.sendDestructionStagePacket
import de.danielmaile.mpp.util.sendPackets
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import kotlin.math.round

private val MINING_FATIGUE = MobEffectInstance(MobEffect.byId(4), Integer.MAX_VALUE, 255, false, false, false)

object BlockBreakingService {

    fun init() {
        // Listen to player action packets
        ProtocolLibrary.getProtocolManager().addPacketListener(object :
            PacketAdapter(inst(), ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
            override fun onPacketReceiving(event: PacketEvent) {

                val sequence = event.packet.integers.read(0)
                val blockPosition = event.packet.blockPositionModifier.values[0]
                val digType = event.packet.playerDigTypes.values[0]

                if (digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                    val block = event.player.world.getBlockAt(blockPosition.x, blockPosition.y, blockPosition.z)
                    damageBlock(block, event.player, sequence)
                }

                if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK || digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK) {
                    val block = event.player.world.getBlockAt(blockPosition.x, blockPosition.y, blockPosition.z)
                    stopDamaging(block)
                }
            }
        })

        // Handle ticks
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
            // Use Iterator to avoid ConcurrentModificationException
            val iterator = damagedBlocks.iterator()
            while (iterator.hasNext()) {
                iterator.next().value.tick()
            }
        }, 1L, 1L)
    }

    private val damagedBlocks: HashMap<Location, DamagedBlock> = HashMap()

    fun damageBlock(block: Block, player: Player, sequence: Int) {
        if (block.blockData !is NoteBlock) return
        val blockType = BlockType.fromBlockData(block.blockData as NoteBlock) ?: return

        damagedBlocks.getOrPut(block.location) { DamagedBlock(block, blockType, player, sequence) }
    }

    fun stopDamaging(block: Block) {
        val damagedBlock = damagedBlocks[block.location] ?: return
        damagedBlock.stop()
        damagedBlocks.remove(block.location)
    }

    private class DamagedBlock(
        private val block: Block,
        private val blockType: BlockType,
        private val player: Player,
        private val sequence: Int
    ) {

        private val breakTime = blockType.breakTime
        private var ticksPassed = 0
        private var lastDestructionStage = -1

        fun tick() {
            ticksPassed++

            // Break block and stop
            if (ticksPassed >= breakTime) {
                dropItem()
                stop()
                return
            }

            // Update block destruction stage
            val destructionStage = getDestructionStage()
            if(destructionStage != lastDestructionStage) {
                block.sendDestructionStagePacket(destructionStage)
                lastDestructionStage = destructionStage
            }

            // Give player slow mining effect
            val effect = player.getPotionEffect(PotionEffectType.SLOW_DIGGING)
            val packet = if (effect != null) {
                // The player might actually have mining fatigue.
                // In this case, it is important to copy the hasIcon value to prevent it from disappearing.
                val effectInstance = MobEffectInstance(
                    MobEffect.byId(4),
                    Int.MAX_VALUE, 255,
                    effect.isAmbient, effect.hasParticles(), effect.hasIcon()
                )
                ClientboundUpdateMobEffectPacket(player.entityId, effectInstance)
            } else {
                // The player does not have mining fatigue, we can use the default effect instance
                ClientboundUpdateMobEffectPacket(player.entityId, MINING_FATIGUE)
            }
            player.sendPackets(packet)
        }

        fun stop() {
            // Remove client-predicted block states and show those sent by the server
            block.sendDestructionStagePacket(-1)
            player.sendPackets(ClientboundBlockChangedAckPacket(sequence))

            // Remove slow mining effect from player
            val effect = player.getPotionEffect(PotionEffectType.SLOW_DIGGING)
            val packet = if (effect != null) {
                // If the player actually has mining fatigue, send the correct effect again
                val effectInstance = MobEffectInstance(
                    MobEffect.byId(4),
                    effect.duration, effect.amplifier,
                    effect.isAmbient, effect.hasParticles(), effect.hasIcon()
                )
                ClientboundUpdateMobEffectPacket(player.entityId, effectInstance)
            } else {
                // Remove the effect
                ClientboundRemoveMobEffectPacket(player.entityId, MobEffect.byId(4))
            }
            player.sendPackets(packet)

            // Remove DamagedBlock instance from service
            damagedBlocks.remove(block.location)
        }

        private fun getDestructionStage(): Int {
            return round((ticksPassed.toFloat() / breakTime.toFloat()) * 9).toInt()
        }

        // Drop item corresponding to the broken block
        private fun dropItem() {
            block.type = Material.AIR
            val itemType = ItemType.fromPlaceBlockType(blockType) ?: return
            block.world.dropItemNaturally(block.location, itemType.getItemStack(1))
        }
    }
}