package de.danielmaile.mpp.block

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.wrappers.EnumWrappers
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ItemType
import de.danielmaile.mpp.util.*
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffectType
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.pow
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
                    if(block.isCustom()) {
                        damageBlock(block, event.player, sequence)
                    }
                }

                if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK || digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK) {
                    val block = event.player.world.getBlockAt(blockPosition.x, blockPosition.y, blockPosition.z)
                    stopDamaging(block)
                }
            }
        })

        // Handle ticks
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
            toRemoveDamageBlocks.forEach {
                damagedBlocks.remove(it)
            }
            toRemoveDamageBlocks.clear()
            damagedBlocks.forEach { (_, u) -> u.tick() }
        }, 1L, 1L)
    }

    private val damagedBlocks: HashMap<Location, DamagedBlock> = HashMap()

    // Use second list to avoid ConcurrentModificationException
    private val toRemoveDamageBlocks = mutableListOf<Location>()

    fun damageBlock(block: Block, player: Player, sequence: Int) {
        val blockType = BlockType.fromBlockData(block.blockData as NoteBlock) ?: return

        // Break block instant if player is in creative mode
        if(player.gameMode == GameMode.CREATIVE) {
            // Run at next tick to ensure it's not async
            Bukkit.getScheduler().runTask(inst(), Runnable {
                block.type = Material.AIR
            })
            return
        }

        damagedBlocks.getOrPut(block.location) { DamagedBlock(block, blockType, player, player.inventory.itemInMainHand, sequence) }
    }

    fun stopDamaging(block: Block) {
        val damagedBlock = damagedBlocks[block.location] ?: return
        damagedBlock.stop()
        toRemoveDamageBlocks.add(block.location)
    }

    private class DamagedBlock(
        private val block: Block,
        private val blockType: BlockType,
        private val player: Player,
        private val itemInHand: ItemStack,
        private val sequence: Int
    ) {

        private val breakTime = calculateBreakingTime()
        private var ticksPassed = 0
        private var lastDestructionStage = -1

        fun tick() {
            ticksPassed++

            // Break block and stop
            if (ticksPassed >= breakTime) {
                // Run at next tick to ensure it's not async
                Bukkit.getScheduler().runTask(inst(), Runnable {
                    block.type = Material.AIR
                })

                playBreakSound()
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
            toRemoveDamageBlocks.add(block.location)
        }

        private fun getDestructionStage(): Int {
            return round((ticksPassed.toFloat() / breakTime.toFloat()) * 9).toInt()
        }

        // Drop item corresponding to the broken block
        private fun dropItem() {
            val itemType = ItemType.fromPlaceBlockType(blockType) ?: return
            block.world.dropItemNaturally(block.location, itemType.getItemStack(1))
        }

        /**
         * Calculates the breaking time in ticks based on the hardness of the block
         * and the current tool
         * Based on: https://minecraft.fandom.com/wiki/Breaking
         */
        private fun calculateBreakingTime(): Int {
            var speedMultiplier = 1.0

            // Check if player has correct tool
            val toolType = ToolType.fromMaterial(itemInHand.type)
            val isCorrectTool = toolType != null && toolType == blockType.toolType
            if(isCorrectTool) {
                speedMultiplier = toolType!!.materialToSpeedMapping[itemInHand.type]!!.toDouble()
                val efficiencyLevel = itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED)
                if(efficiencyLevel > 0) {
                    speedMultiplier += (efficiencyLevel * efficiencyLevel) + 1
                }
            }

            // Check if player has haste effect
            val hasteLevel = player.getPotionEffectLevel(PotionEffectType.FAST_DIGGING)
            if(hasteLevel > 0) {
                speedMultiplier *= 0.2 * hasteLevel + 1
            }

            // Check if player has haste effect
            val miningFatigueLevel = player.getPotionEffectLevel(PotionEffectType.SLOW_DIGGING)
            if(miningFatigueLevel > 0) {
                speedMultiplier *= 0.3.pow(min(miningFatigueLevel, 4))
            }

            // Check if player is in water and has no aqua affinity
            if(player.isInWater) {
                val helmet = player.inventory.helmet

                if(helmet == null || helmet.getEnchantmentLevel(Enchantment.WATER_WORKER) < 1) {
                    speedMultiplier /= 5
                }
            }

            // Check if player is in air
            if(!player.isGrounded()) {
                speedMultiplier /= 5
            }

            val damage = (speedMultiplier / blockType.hardness) / 30
            return if(damage > 1) {
                0
            } else {
                ceil(1 / damage).toInt()
            }
        }

        private fun playBreakSound() {
            block.world.playSound(block.location, blockType.breakSound, 1f, 1f)
        }
    }
}