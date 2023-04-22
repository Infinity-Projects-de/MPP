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

package de.danielmaile.mpp.item.items

import de.danielmaile.mpp.block.BlockType
import de.danielmaile.mpp.item.Item
import de.danielmaile.mpp.item.recipe.ShapelessRecipe
import org.bukkit.Material

enum class Blocks(
    override val material: Material,
    val blockType: BlockType
) : Item {
    AETHER_LOG(Material.OAK_LOG, BlockType.AETHER_LOG),
    AETHER_PLANKS(
        Material.OAK_PLANKS,
        BlockType.AETHER_PLANKS
    ),
    AETHER_LEAVES(Material.STONE, BlockType.AETHER_LEAVES),
    AETHER_STONE(Material.STONE, BlockType.AETHER_STONE),
    AETHER_GRASS_BLOCK(Material.DIRT, BlockType.AETHER_GRASS_BLOCK),
    AETHER_DIRT(Material.DIRT, BlockType.AETHER_DIRT),
    CLOUD_SLOW_FALLING(Material.WHITE_WOOL, BlockType.CLOUD_SLOW_FALLING),
    ZANITE_ORE(Material.STONE, BlockType.ZANITE_ORE),
    GRAVITITE_ORE(Material.STONE, BlockType.GRAVITITE_ORE);

    override fun getRecipes(): List<org.bukkit.inventory.Recipe> {
        return when (this) {
            AETHER_PLANKS -> ShapelessRecipe(AETHER_PLANKS.itemStack(4), arrayOf(AETHER_LOG.itemStack(1)))
            else -> null
        }?.recipes ?: emptyList()
    }

    companion object {
        fun getBlockDrop(blockType: BlockType): Item? {
            for (block in values()) {
                if (block.blockType == blockType) return block
            }
            return null
        }
    }
}
