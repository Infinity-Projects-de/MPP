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

import de.danielmaile.mpp.item.ItemType
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe

class ShapedRecipe(
    private val result: ItemType,
    private val amount: Int,
    private val ingredients: List<ItemType?>
) : CraftingRecipe() {

    override val spigotRecipes: List<Recipe>
        get() = listOf(getSpigotRecipe())

    private fun getSpigotRecipe(): Recipe {
        val shapedRecipe = ShapedRecipe(getRandomRecipeKey(), result.getItemStack(amount))

        // set shape
        val shape = charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'H', 'I', 'J')
        for (i in shape.indices) {
            if (ingredients[i] == null) {
                shape[i] = ' '
            }
        }
        val shapeString = String(shape)
        shapedRecipe.shape(shapeString.substring(0, 3), shapeString.substring(3, 6), shapeString.substring(6, 9))

        // set ingredients
        for (i in shape.indices) {
            val ingredient = ingredients[i]
            if (shape[i] != ' ' && ingredient != null) {
                shapedRecipe.setIngredient(shape[i], RecipeChoice.ExactChoice(ingredient.getItemStack()))
            }
        }
        return shapedRecipe
    }
}