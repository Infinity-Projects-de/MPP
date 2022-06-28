package de.danielmaile.lama.aether.world.cloud

import de.danielmaile.lama.aether.inst
import de.danielmaile.lama.aether.item.BlockType
import de.danielmaile.lama.aether.util.getBlockBelow
import de.danielmaile.lama.aether.util.isSurroundedByAirOrMaterial
import org.bukkit.Bukkit
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class CloudEffects {

    private val effects = mapOf(
        BlockType.CLOUD_HEAL.blockMaterial to Pair(PotionEffectType.REGENERATION, 0),
        BlockType.CLOUD_SLOW_FALLING.blockMaterial to Pair(PotionEffectType.SLOW_FALLING, 0),
        BlockType.CLOUD_SPEED.blockMaterial to Pair(PotionEffectType.SPEED, 1),
        BlockType.CLOUD_JUMP.blockMaterial to Pair(PotionEffectType.JUMP, 1),
        BlockType.CLOUD_HEAL2.blockMaterial to Pair(PotionEffectType.REGENERATION, 2),
        BlockType.CLOUD_SPEED2.blockMaterial to Pair(PotionEffectType.SPEED, 3),
        BlockType.CLOUD_JUMP2.blockMaterial to Pair(PotionEffectType.JUMP, 4)
    )

    init {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
            for (player in Bukkit.getOnlinePlayers()) {
                if (player.world != inst().aetherWorld.getWorld()) continue

                val block = player.getBlockBelow()
                if (!block.isSurroundedByAirOrMaterial(effects.keys)) continue

                val effect = effects[player.getBlockBelow().type] ?: continue
                player.addPotionEffect(PotionEffect(effect.first, 60, effect.second, false, false))
            }
        }, 5L, 5L)
    }
}