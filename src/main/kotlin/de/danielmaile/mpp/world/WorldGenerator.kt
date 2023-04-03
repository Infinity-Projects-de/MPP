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
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import org.joml.SimplexNoise
import java.util.*
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.max

class WorldGenerator : ChunkGenerator() {
    private val islandSize = 128 // Island size, is not relative to anything, the bigger the size the bigger the island
    private val middleHeight = 64f // Middle height of the map, where the center of the islands is normally placed
    private val octaves = 4 // Number of noise layers
    private val persistence = 0.25 // amplitude multiplier between noise layers, making it higher makes the terrain less regular
    private val lacunarity = 2f // frequency multiplier between noise layers, should help smoothing out a little bit
    private val threshold = 0.25 // after what value to generate islands ([-1,+1]), the lower, the more terrain is generated
    private val aetherGrass = Material.NOTE_BLOCK.createBlockData() {
            data ->
        val block = BlockType.AETHER_GRASS_BLOCK
        (data as NoteBlock).note = block.note
        data.isPowered = block.isPowered
        data.instrument = block.instrument
    }

    val aetherDirt = Material.NOTE_BLOCK.createBlockData() {
            data ->
        val block = BlockType.AETHER_DIRT
        (data as NoteBlock).note = block.note
        data.isPowered = block.isPowered
        data.instrument = block.instrument
    }

    val aetherStone = Material.NOTE_BLOCK.createBlockData() {
            data ->
        val block = BlockType.AETHER_STONE
        (data as NoteBlock).note = block.note
        data.isPowered = block.isPowered
        data.instrument = block.instrument
    }
    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        val startingX = chunkX * 16
        val startingZ = chunkZ * 16
        val minHeight = worldInfo.minHeight
        val maxHeight = worldInfo.maxHeight
        val maxDistance = max(middleHeight - maxHeight, middleHeight - minHeight)
        for (relativeX in 0..15) {
            val x = relativeX + startingX
            for (relativeZ in 0..15) {
                val z = relativeZ + startingZ
                var highestBlock = 0
                for (y in maxHeight downTo minHeight) {
                    var frequency = 1f / islandSize

                    val distance = middleHeight - y

                    val densityAggressiveness = 3 // 0 to 10, how much aggressive is the density
                    val density = exp(-abs(distance) / maxDistance * densityAggressiveness) // the higher the distance, the more faded will the value be

                    var noise = 0.0 // do not modify this
                    var amplitude = 1.0 // how much will the current layer affect the noise

                    for (i in 0..octaves) {
                        val value = SimplexNoise.noise(x * frequency, y * frequency, z * frequency)
                        noise += value * amplitude
                        amplitude *= persistence
                        frequency *= lacunarity
                    }
                    if (noise * density > threshold) {
                        if (highestBlock < y) {
                            highestBlock = y
                            chunkData.setBlock(relativeX, y, relativeZ, aetherGrass)
                        } else {
                            val distanceToTop = highestBlock - y
                            if (distanceToTop < 4) {
                                chunkData.setBlock(relativeX, y, relativeZ, aetherDirt)
                            } else {
                                chunkData.setBlock(relativeX, y, relativeZ, aetherStone)
                            }
                        }
                    }
                }
            }
        }
    }
}
