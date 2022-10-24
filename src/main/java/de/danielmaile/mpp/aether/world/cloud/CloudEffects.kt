package de.danielmaile.mpp.aether.world.cloud

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.BlockType
import de.danielmaile.mpp.util.getBlockBelow
import org.bukkit.Bukkit
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class CloudEffects {

    private val effects = mapOf(
        BlockType.CLOUD_HEAL to Pair(PotionEffectType.REGENERATION, 0),
        BlockType.CLOUD_SLOW_FALLING to Pair(PotionEffectType.SLOW_FALLING, 0),
        BlockType.CLOUD_SPEED to Pair(PotionEffectType.SPEED, 1),
        BlockType.CLOUD_JUMP to Pair(PotionEffectType.JUMP, 1),
        BlockType.CLOUD_HEAL2 to Pair(PotionEffectType.REGENERATION, 2),
        BlockType.CLOUD_SPEED2 to Pair(PotionEffectType.SPEED, 3),
        BlockType.CLOUD_JUMP2 to Pair(PotionEffectType.JUMP, 4)
    )

    init {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
            for (player in Bukkit.getOnlinePlayers()) {
                val block = player.getBlockBelow()
                val blockType = BlockType.fromBlockData(block.blockData as NoteBlock) ?: continue
                val effect = effects[blockType] ?: continue
                player.addPotionEffect(PotionEffect(effect.first, 60, effect.second, false, false))
            }
        }, 5L, 5L)
    }
}