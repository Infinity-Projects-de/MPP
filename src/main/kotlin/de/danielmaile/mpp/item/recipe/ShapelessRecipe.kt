/*
 * This file is part of MPP.
 * Copyright (c) 2022 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.item.recipe

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapelessRecipe

class ShapelessRecipe(
    private val result: ItemStack,
    private val ingredients: List<ItemStack?>
) : CraftingRecipe() {

    override val spigotRecipes: List<Recipe>
        get() = listOf(getSpigotRecipe())

    private fun getSpigotRecipe(): Recipe {
        val shapelessRecipe = ShapelessRecipe(getRandomRecipeKey(), result)
        for (itemType in ingredients) {
            if (itemType == null) continue
            shapelessRecipe.addIngredient(RecipeChoice.ExactChoice(itemType))
        }
        return shapelessRecipe
    }
}