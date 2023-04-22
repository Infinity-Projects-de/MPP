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
 *
 * This file contains (modified) code from others, which is licensed
 * under the GNU Lesser General Public License version 3:
 *
 * Copyright (c) 2023 xenodevs
 * Source: https://github.com/xenondevs/Nova
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MPP.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.danielmaile.mpp.block

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.items.Blocks
import de.danielmaile.mpp.packet.PacketListener
import de.danielmaile.mpp.util.ToolType
import de.danielmaile.mpp.util.getPotionEffectLevel
import de.danielmaile.mpp.util.isCustom
import de.danielmaile.mpp.util.isGrounded
import de.danielmaile.mpp.util.sendDestructionStagePacket
import de.danielmaile.mpp.util.sendPackets
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.potion.PotionEffectType
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round
import kotlin.random.Random

private val MINING_FATIGUE = MobEffectInstance(MobEffect.byId(4)!!, Integer.MAX_VALUE, 255, false, false, false)

object BlockBreakingService {

    @PacketListener(ignoreCancelled = true)
    fun onBlockDig(event: de.danielmaile.mpp.packet.PacketEvent<ServerboundPlayerActionPacket>) {
        val sequence = event.packet.sequence
        val blockPos = event.packet.pos

        when (event.packet.action) {
            Action.START_DESTROY_BLOCK -> {
                val block = event.player.world.getBlockAt(blockPos.x, blockPos.y, blockPos.z)
                if (block.isCustom()) {
                    damageBlock(block, event.player, sequence)
                }
            }
            Action.STOP_DESTROY_BLOCK, Action.ABORT_DESTROY_BLOCK -> {
                val block = event.player.world.getBlockAt(blockPos.x, blockPos.y, blockPos.z)
                stopDamaging(block)
            }
            else -> {
                return
            }
        }
    }

    fun init() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
            toRemoveDamageBlocks.forEach {
                damagedBlocks.remove(it)
            }
            toRemoveDamageBlocks.clear()
            damagedBlocks.forEach { (_, u) -> u.tick() }
        }, 1L, 1L)
    }

    private val damagedBlocks: HashMap<Location, DamagedBlock> = HashMap()

    // use second list to avoid ConcurrentModificationException
    private val toRemoveDamageBlocks = mutableListOf<Location>()

    fun damageBlock(block: Block, player: Player, sequence: Int) {
        val blockType = BlockType.fromBlockData(block.blockData as NoteBlock) ?: return

        // break block instant if player is in creative mode
        if (player.gameMode == GameMode.CREATIVE) {
            // run at next tick to ensure it's not async
            Bukkit.getScheduler().runTask(
                inst(),
                Runnable {
                    block.type = Material.AIR
                }
            )
            return
        }

        damagedBlocks.getOrPut(block.location) {
            DamagedBlock(
                block,
                blockType,
                player,
                player.inventory.itemInMainHand,
                sequence
            )
        }
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

            // break block and stop
            if (ticksPassed >= breakTime) {
                // run at next tick to ensure it's not async
                Bukkit.getScheduler().runTask(
                    inst(),
                    Runnable {
                        block.type = Material.AIR
                    }
                )

                playBreakSound()
                dropItem()
                damageTool()
                stop()
                return
            }

            // update block destruction stage
            val destructionStage = getDestructionStage()
            if (destructionStage != lastDestructionStage) {
                block.sendDestructionStagePacket(destructionStage)
                lastDestructionStage = destructionStage
            }

            // give player slow mining effect
            val effect = player.getPotionEffect(PotionEffectType.SLOW_DIGGING)
            val packet = if (effect != null) {
                // the player might actually have mining fatigue.
                // in this case, it is important to copy the hasIcon value to prevent it from disappearing.
                val effectInstance = MobEffectInstance(
                    MobEffect.byId(4)!!,
                    Int.MAX_VALUE,
                    255,
                    effect.isAmbient,
                    effect.hasParticles(),
                    effect.hasIcon()
                )
                ClientboundUpdateMobEffectPacket(player.entityId, effectInstance)
            } else {
                // the player does not have mining fatigue, we can use the default effect instance
                ClientboundUpdateMobEffectPacket(player.entityId, MINING_FATIGUE)
            }
            player.sendPackets(packet)
        }

        fun stop() {
            // remove client-predicted block states and show those sent by the server
            block.sendDestructionStagePacket(-1)
            player.sendPackets(ClientboundBlockChangedAckPacket(sequence))

            // remove slow mining effect from player
            val effect = player.getPotionEffect(PotionEffectType.SLOW_DIGGING)

            val packet = if (effect != null) {
                // if the player actually has mining fatigue, send the correct effect again
                val effectInstance = MobEffectInstance(
                    MobEffect.byId(4)!!,
                    effect.duration,
                    effect.amplifier,
                    effect.isAmbient,
                    effect.hasParticles(),
                    effect.hasIcon()
                )
                ClientboundUpdateMobEffectPacket(player.entityId, effectInstance)
            } else {
                // remove the effect
                ClientboundRemoveMobEffectPacket(player.entityId, MobEffect.byId(4)!!)
            }
            player.sendPackets(packet)

            // remove DamagedBlock instance from service
            toRemoveDamageBlocks.add(block.location)
        }

        private fun getDestructionStage(): Int {
            return round((ticksPassed.toFloat() / breakTime.toFloat()) * 9).toInt()
        }

        // drop item corresponding to the broken block
        private fun dropItem() {
            val itemType = Blocks.getBlockDrop(blockType) ?: return
            block.world.dropItemNaturally(block.location, itemType.itemStack(1))
        }

        /**
         * Calculates the breaking time in ticks based on the hardness of the block
         * and the current tool
         * Based on: https://minecraft.fandom.com/wiki/Breaking
         */
        private fun calculateBreakingTime(): Int {
            var speedMultiplier = 1.0

            // check if player has correct tool
            val toolType = ToolType.fromMaterial(itemInHand.type)
            val isCorrectTool = toolType != null && toolType == blockType.toolType
            if (isCorrectTool) {
                speedMultiplier = toolType!!.materialToSpeedMapping[itemInHand.type]!!.toDouble()
                val efficiencyLevel = itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED)
                if (efficiencyLevel > 0) {
                    speedMultiplier += (efficiencyLevel * efficiencyLevel) + 1
                }
            }

            // check if player has haste effect
            val hasteLevel = player.getPotionEffectLevel(PotionEffectType.FAST_DIGGING)
            if (hasteLevel > 0) {
                speedMultiplier *= 0.2 * hasteLevel + 1
            }

            // check if player has haste effect
            val miningFatigueLevel = player.getPotionEffectLevel(PotionEffectType.SLOW_DIGGING)
            if (miningFatigueLevel > 0) {
                speedMultiplier *= 0.3.pow(min(miningFatigueLevel, 4))
            }

            // check if player is in water and has no aqua affinity
            if (player.isInWater) {
                val helmet = player.inventory.helmet

                if (helmet == null || helmet.getEnchantmentLevel(Enchantment.WATER_WORKER) < 1) {
                    speedMultiplier /= 5
                }
            }

            // check if player is in air
            if (!player.isGrounded()) {
                speedMultiplier /= 5
            }

            val damage = (speedMultiplier / blockType.hardness) / 30
            return if (damage > 1) {
                0
            } else {
                ceil(1 / damage).toInt()
            }
        }

        private fun playBreakSound() {
            block.world.playSound(block.location, blockType.breakSound, 1f, 1f)
        }

        private fun damageTool() {
            // return if item is not damageable
            if (itemInHand.itemMeta !is Damageable) return

            // random chance to damage tool (based on vanilla behaviour)
            val durabilityLvl = itemInHand.getEnchantmentLevel(Enchantment.DURABILITY)
            if (Random.nextInt(0, durabilityLvl + 1) != 0) return

            val itemMeta = itemInHand.itemMeta as Damageable
            itemMeta.damage = itemMeta.damage + 1
            itemInHand.itemMeta = itemMeta
        }
    }
}
