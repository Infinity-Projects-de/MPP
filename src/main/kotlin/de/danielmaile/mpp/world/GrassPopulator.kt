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
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.LimitedRegion
import org.bukkit.generator.WorldInfo
import java.util.*

class GrassPopulator: BlockPopulator() {

    // CREDITS TO https://www.spigotmc.org/members/cooljwb.154564/

    private val aetherGrass = Material.NOTE_BLOCK.createBlockData() { data ->
        val block = BlockType.AETHER_GRASS_BLOCK
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
        for (iteration in 0 until 10) {
            val x = random.nextInt(16) + chunkX * 16
            val z = random.nextInt(16) + chunkZ * 16
            var y = worldInfo.maxHeight-1
            while (!limitedRegion.getBlockData(x, y, z).matches(aetherGrass) && y > worldInfo.minHeight) y--

            if (limitedRegion.getBlockData(x, y, z).matches(aetherGrass)) {
                if (limitedRegion.isInRegion(x,y+1,z)) {
                    limitedRegion.setType(x, y + 1, z, Material.GRASS)
                }
            }
        }
    }
}