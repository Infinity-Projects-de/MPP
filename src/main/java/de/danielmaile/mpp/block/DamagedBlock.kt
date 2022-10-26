package de.danielmaile.mpp.block

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ItemType
import de.danielmaile.mpp.util.sendDestructionStagePacket
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block

class DamagedBlock(
    private val block: Block,
    private val blockType: BlockType
) {

    private var taskID = -1
    private var lastAnimationStage = 0

    // Break duration in ticks
    private val duration = blockType.breakTime
    private var ticksPassed = 0

    fun startDestroying() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
            if(ticksPassed >= duration) {
                block.sendDestructionStagePacket(10)
                breakAndDropBlock()
                cancelDestroying()
                return@scheduleSyncRepeatingTask
            }

            val animationStage = getAnimationStage()
            if(animationStage != lastAnimationStage) {
                block.sendDestructionStagePacket(animationStage)
                lastAnimationStage = animationStage
            }
            ticksPassed++
        }, 1L, 1L)
    }

    fun cancelDestroying() {
        if (taskID == -1) return
        Bukkit.getScheduler().cancelTask(taskID)
        block.sendDestructionStagePacket(10)
    }

    private fun getAnimationStage(): Int {
        return ((ticksPassed.toDouble() / duration.toDouble()) * 9.0).toInt()
    }

    private fun breakAndDropBlock() {
        block.type = Material.AIR
        val itemType = ItemType.fromPlaceBlockType(blockType) ?: return
        block.world.dropItemNaturally(block.location, itemType.getItemStack(1))
    }
}