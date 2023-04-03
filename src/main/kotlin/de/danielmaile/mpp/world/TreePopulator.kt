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
import org.bukkit.block.data.type.Leaves
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.LimitedRegion
import org.bukkit.generator.WorldInfo
import java.util.*


class TreePopulator: BlockPopulator() {
    private val aetherGrass = Material.NOTE_BLOCK.createBlockData() { data ->
        val block = BlockType.AETHER_GRASS_BLOCK
        (data as NoteBlock).note = block.note
        data.isPowered = block.isPowered
        data.instrument = block.instrument
    }
    val aetherLog = Material.NOTE_BLOCK.createBlockData() { data ->
        val block = BlockType.AETHER_LOG
        (data as NoteBlock).note = block.note
        data.isPowered = block.isPowered
        data.instrument = block.instrument
    }
    val aetherLeaves = Material.NOTE_BLOCK.createBlockData() { data ->
        val block = BlockType.AETHER_LEAVES
        (data as NoteBlock).note = block.note
        data.isPowered = block.isPowered
        data.instrument = block.instrument
    }
    override fun populate(
        worldInfo: WorldInfo,
        random: Random,
        chunkX: Int,
        chunkZ: Int,
        limitedRegion: LimitedRegion
    ) {
        val x = random.nextInt(16) + chunkX * 16
        val z = random.nextInt(16) + chunkZ * 16
        var y = worldInfo.maxHeight -1
        while (!limitedRegion.getBlockData(x, y, z).matches(aetherGrass) && y > worldInfo.minHeight) y--

        if (limitedRegion.getBlockData(x, y, z).matches(aetherGrass)) {
            if (!canGenerateTree(limitedRegion,x,y+1,z)) {
                return
            }
            generateTree(limitedRegion, x,y+1,z)
        }
    }

    private fun canGenerateTree(limitedRegion: LimitedRegion, x: Int, y: Int, z: Int): Boolean {
        for (nx in -2 .. 2) {
            for (nz in -2 .. 2) {
                for (ny in 0 .. 6) {
                    if (!limitedRegion.isInRegion(x+nx,y+ny,z+nz)) {
                        return false
                    }
                    if (!limitedRegion.getType(x+nx, y+ny, z+nz).isAir) {
                        return false
                    }
                }
            }
        }
        if (limitedRegion.getBlockData(x,y-1,z).matches(aetherGrass)) {
            return true
        }
        return false
    }


    // CUSTOM TREE
    private fun generateTree(limitedRegion: LimitedRegion, x: Int, y: Int, z: Int) {
        for (ny in 0 until 5) {
            limitedRegion.setBlockData(x, y + ny, z, aetherLog)
        }
        for (nx in -2 .. 2) {
            for (nz in -2 .. 2) {
                for (ny in 3 .. 6) {
                    if (limitedRegion.getType(x+nx, y+ny, z+nz).isAir) {
                        limitedRegion.setBlockData(x+nx,y+ny,z+nz,Material.OAK_LEAVES.createBlockData().apply {
                            (this as Leaves).isPersistent = true
                        })
                    }
                }
            }
        }
    }
}
