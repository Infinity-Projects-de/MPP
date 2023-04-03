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

package de.danielmaile.mpp.world

import de.danielmaile.mpp.block.BlockType
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import org.bukkit.util.noise.SimplexNoiseGenerator
import java.util.*
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.max

class WorldGenerator : ChunkGenerator() {

    /* TERRAIN DATA */
    private val middleHeight = 215f // Middle height of the map, where the center of the islands is normally placed
    private val islandSize = 150 // Island size, is not relative to anything, the bigger the size the bigger the island
    private val cloudSize = 50

    /* NOISE SETTINGS */
    private val simplex = SimplexNoiseGenerator(0)

    private val octaves = 4 // Number of noise layers
    private val persistence = 0.5 // amplitude multiplier between noise layers, making it higher makes the terrain less regular
    private val lacunarity = 2 // frequency multiplier between noise layers, should help smoothing out a little bit
    private val threshold = 0.25 // after what value to generate islands ([-1,+1]), the lower, the more terrain is generated
    private val densityAggressiveness = 7 // 0 to 10, how much aggressive is the density

    /* USED BLOCKS */
    private val aetherGrass = BlockType.AETHER_GRASS_BLOCK.blockData
    private val aetherDirt = BlockType.AETHER_DIRT.blockData
    private val aetherStone = BlockType.AETHER_STONE.blockData


    // TODO: To be improved
    override fun generateSurface(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        val minHeight = worldInfo.minHeight
        val maxHeight = worldInfo.maxHeight

        for (x in 0..15) {
            for (z in 0..15) {
                var highestBlock = 0
                for (y in maxHeight downTo minHeight) {
                    if (chunkData.getBlockData(x, y, z).material != Material.STONE) {
                        highestBlock = 0
                    } else {
                        if (highestBlock < y) {
                            highestBlock = y
                            chunkData.setBlock(x, y, z, aetherGrass)
                        } else {
                            val distanceToTop = highestBlock - y
                            if (distanceToTop < 4) {
                                chunkData.setBlock(x, y, z, aetherDirt)
                            } else {
                                chunkData.setBlock(x, y, z, aetherStone)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getDefaultBiomeProvider(worldInfo: WorldInfo): BiomeProvider {
        return AetherBiomeProvider()
    }

    override fun getDefaultPopulators(world: World): MutableList<BlockPopulator> {
        return mutableListOf(TreePopulator(), GrassPopulator())
    }

    private fun generateTerrain(chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        val maxHeight = chunkData.maxHeight
        val minHeight = chunkData.minHeight
        val maxDistance = max(middleHeight - maxHeight, middleHeight - minHeight)
        for (relX in 0..15) {
            val x = relX + chunkX * 16
            for (relZ in 0..15) {
                val z = relZ + chunkZ * 16
                for (y in minHeight until maxHeight) {
                    var frequency = 1.0 / islandSize

                    val distance = middleHeight - y
                    val density =
                        exp(-abs(distance) / maxDistance * densityAggressiveness) // the higher the distance, the more faded will the value be

                    var noise = 0.0 // do not modify this
                    var amplitude = 1.0 // how much will the current layer affect the noise

                    for (i in 0..octaves) {
                        val value = simplex.noise(x * frequency, y * frequency * 2, z * frequency)
                        noise += value * amplitude
                        amplitude *= persistence
                        frequency *= lacunarity
                    }

                    if (noise * density > threshold) {
                        chunkData.setBlock(relX, y, relZ, Material.STONE)
                    }
                }
            }
        }
    }

    private fun generateClouds(chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        val maxHeight = chunkData.maxHeight
        val minHeight = chunkData.minHeight
        val maxDistance = max(middleHeight - maxHeight, middleHeight - minHeight)

        for (relX in 0..15) {
            val x = relX + chunkX * 16
            for (relZ in 0..15) {
                val z = relZ + chunkZ * 16
                for (y in minHeight until maxHeight) {
                    val cloudFrequency = 1.0 / cloudSize

                    val distance = middleHeight - y
                    val density =
                        exp(-abs(distance) / maxDistance * densityAggressiveness / 2) // the higher the distance, the more faded will the value be

                    val noise = simplex.noise(x * cloudFrequency, y * cloudFrequency * 2, z * cloudFrequency)

                    if (noise * density > 0.80) {
                        chunkData.setBlock(relX, y, relZ, BlockType.CLOUD_SLOW_FALLING.blockData)
                    }
                }
            }
        }
    }

    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        generateTerrain(chunkX, chunkZ, chunkData)
        generateClouds(chunkX, chunkZ, chunkData)
    }
}
