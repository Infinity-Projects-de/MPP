package de.danielmaile.aether.world

import org.bukkit.Material
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.LimitedRegion
import org.bukkit.generator.WorldInfo
import java.util.*

class BedrockPopulator : BlockPopulator() {

    override fun populate(
        worldInfo: WorldInfo,
        random: Random,
        chunkX: Int,
        chunkZ: Int,
        limitedRegion: LimitedRegion
    ) {
        for (x in chunkX * 16 until (chunkX + 1) * 16) {
            for (z in chunkZ * 16 until (chunkZ + 1) * 16) {
                limitedRegion.setType(x, -256, z, Material.BEDROCK)
            }
        }
    }
}