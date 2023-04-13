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

import de.danielmaile.mpp.item.recipe.CraftingRecipe
import de.danielmaile.mpp.item.recipe.FurnaceRecipe
import de.danielmaile.mpp.item.recipe.Recipe
import org.bukkit.Material

enum class Ingredients(
    override val material: Material,
    val recipe: Recipe?
) : Item {
    AETHER_STICK(
        Material.STICK,
        CraftingRecipe.StickRecipe(AETHER_STICK.itemStack(4), Blocks.AETHER_PLANKS.itemStack(1))
    ),
    ZANITE_STONE(
        Material.DIAMOND,
        FurnaceRecipe(ZANITE_STONE.itemStack(1), Blocks.ZANITE_ORE.itemStack(1), 1f, 1)
    ),
    GRAVITITE_PLATE(
        Material.NETHERITE_SCRAP,
        FurnaceRecipe(GRAVITITE_PLATE.itemStack(1), Blocks.GRAVITITE_ORE.itemStack(1), 1f, 1)
    );

    constructor(material: Material) : this(material, null)

    override fun getRecipes(): List<org.bukkit.inventory.Recipe> {
        return recipe?.recipes ?: emptyList()
    }
}
