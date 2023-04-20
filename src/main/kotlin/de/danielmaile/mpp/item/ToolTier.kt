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

package de.danielmaile.mpp.item

import de.danielmaile.mpp.item.items.Blocks
import de.danielmaile.mpp.item.items.Ingredients

// Copied minecraft default Tiers.java at net.minecraft.worlds.item.Tiers
enum class ToolTier(
    val miningLevel: Int,
    val itemDurability: Int,
    val miningSpeed: Float,
    val attackDamage: Float,
    val enchantability: Int, // Not really sure about this
    val toolIngredients: ToolIngredients
) {
    AETHER_WOOD(
        0,
        59,
        2.0F,
        0.0F,
        15,
        ToolIngredients(Ingredients.AETHER_STICK, Blocks.AETHER_PLANKS)
    ),
    AETHER_STONE(
        1,
        131,
        4.0F,
        1.0F,
        5,
        ToolIngredients(Ingredients.AETHER_STICK, Blocks.AETHER_STONE)
    ),
    ZANITE(
        3,
        1561,
        8.0F,
        3.0F,
        10,
        ToolIngredients(Ingredients.AETHER_STICK, Ingredients.ZANITE_STONE)
    ),
    GRAVITITE(
        4,
        2031,
        9.0F,
        4.0F,
        15,
        ToolIngredients(Ingredients.AETHER_STICK, Ingredients.GRAVITITE_PLATE)
    );

    class ToolIngredients(
        val stickIngredient: Ingredients?,
        val material: Item
    )
}
