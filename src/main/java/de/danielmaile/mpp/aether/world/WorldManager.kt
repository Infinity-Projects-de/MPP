package de.danielmaile.mpp.aether.world

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*


const val aetherWorldName = "world_aether"

class WorldManager {

    val world: World
    val objectManager: ObjectManager

    init {
        val worldCreator = WorldCreator(aetherWorldName)
        worldCreator.type(WorldType.FLAT)
        worldCreator.generator(VoidWorldGenerator())
        world = worldCreator.createWorld()!!
        objectManager = ObjectManager()
    }

    class VoidWorldGenerator : ChunkGenerator() {
        override fun generateSurface(
            worldInfo: WorldInfo,
            random: Random,
            chunkX: Int,
            chunkZ: Int,
            chunkData: ChunkData
        ) {
            for(x in 0..15) {
                for(z in 0..15) {
                    chunkData.setBlock( x, 200, z, Material.GRASS_BLOCK)
                }
            }
        }

        override fun generateBedrock(
            worldInfo: WorldInfo,
            random: Random,
            chunkX: Int,
            chunkZ: Int,
            chunkData: ChunkData
        ) {
        }

        override fun generateCaves(
            worldInfo: WorldInfo,
            random: Random,
            chunkX: Int,
            chunkZ: Int,
            chunkData: ChunkData
        ) {
        }
    }
}