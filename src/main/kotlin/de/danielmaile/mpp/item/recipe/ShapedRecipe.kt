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
import org.bukkit.inventory.RecipeChoice.ExactChoice
import org.bukkit.inventory.ShapedRecipe

class ShapedRecipe(
    private val result: ItemStack,
    private val mainRecipe: Array<ItemStack?>
) : CraftingRecipe() {
    private data class RecipeSize(val width: Int, val height: Int)

    // Constants
    private companion object {
        const val POSSIBLE_INGREDIENTS = "ABCDEFGHI"
    }

    // Properties
    private val size: RecipeSize by lazy {
        var firstRow = -1
        var lastRow = -1

        var firstCol = -1
        var lastCol = -1

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (mainRecipe[i * 3 + j] != null) {
                    if (firstCol == -1) firstCol = i
                    lastCol = i
                }
                if (mainRecipe[j * 3 + i] != null) {
                    if (firstRow == -1) firstRow = i
                    lastRow = i
                }
            }
        }

        RecipeSize(lastRow - firstRow + 1, lastCol - firstCol + 1)
    }

    override val recipes: List<Recipe>
        get() {
            val recipes: MutableList<Recipe> = mutableListOf()

            val ingredients = mainRecipe.distinct()

            val ingredientsToLetter = ingredients.associateWith { POSSIBLE_INGREDIENTS[ingredients.indexOf(it)] }

            for (recipeArray in getRecipeArrays()) {
                val shapedRecipe = ShapedRecipe(getRandomRecipeKey(), result)
                val recipe = recipeArray.joinToString(separator = " ") { "${ingredientsToLetter[it]}" }.trim()
                shapedRecipe.shape(recipe.substring(0, 3), recipe.substring(3, 6), recipe.substring(6, 9))
                for (item in ingredientsToLetter.keys) {
                    if (item != null) {
                        ingredientsToLetter[item]?.let { shapedRecipe.setIngredient(it, ExactChoice(item)) }
                    }
                }

                recipes.add(shapedRecipe)
            }
            return recipes.toList()
        }

    // Recipe reduction methods

    /**
     * Reduces the main recipe by removing empty rows and columns
     */
    private fun reduceRecipe(): Array<ItemStack?> {
        val reducedRecipe: MutableList<ItemStack?> = MutableList(size.width * size.height) { null }
        var newRow = 0
        for (row in 0 until 3) {
            if (!isRowEmpty(row)) {
                var newCol = 0
                for (col in 0 until 3) {
                    if (!isColumnEmpty(col)) {
                        val i = row * 3 + col
                        val newI = newRow * size.width + newCol
                        reducedRecipe[newI] = mainRecipe[i]
                        newCol++
                    }
                }
                newRow++
            }
        }

        return reducedRecipe.toTypedArray()
    }

    /**
     * Checks if a row is empty in the main (provided) recipe
     */
    private fun isRowEmpty(row: Int): Boolean {
        return (0 until 3).all { col -> mainRecipe[row * 3 + col] == null }
    }

    /**
     * Checks if a column is empty in the main (provided) recipe
     */
    private fun isColumnEmpty(col: Int): Boolean {
        return (0 until 3).all { row -> mainRecipe[col + row * 3] == null }
    }

    // Recipe amplification methods

    /**
     * Amplifies the recipe by calculating the different possibilities by using offsets
     * @param reducedRecipe The reduced recipe, without empty rows or columns
     * @return The different possible recipes
     */
    private fun amplifyRecipe(reducedRecipe: Array<ItemStack?>): List<Array<ItemStack?>> {
        val listOfRecipes: MutableList<Array<ItemStack?>> = mutableListOf()

        for (rowOffset in 0..3 - size.height) {
            for (colOffset in 0..3 - size.width) {
                listOfRecipes.add(amplifySingleRecipe(reducedRecipe, rowOffset, colOffset))
            }
        }

        return listOfRecipes
    }

    /**
     * Amplifies a single possibility given both row and column offsets
     * @param reducedRecipe The reduced recipe without any row or column
     * @return A recipe possibility with the extra rows and columns calculated by the offsets
     */
    private fun amplifySingleRecipe(reducedRecipe: Array<ItemStack?>, rowOffset: Int, colOffset: Int): Array<ItemStack?> {
        val amplifiedRecipe: MutableList<ItemStack?> = MutableList(9) { null }
        for (row in 0 until 3) {
            for (col in 0 until 3) {
                val doesRowFit = row - rowOffset in 0 until size.height
                val doesColFit = col - colOffset in 0 until size.width

                if (doesRowFit && doesColFit) {
                    val idx = (row - rowOffset) * size.width + (col - colOffset)
                    val newIdx = row * 3 + col
                    amplifiedRecipe[newIdx] = reducedRecipe[idx]
                }
            }
        }
        return amplifiedRecipe.toTypedArray()
    }

    // Recipe generation methods
    /**
     * Reduces and amplifies the recipe
     * @return All the possible recipe variations
     */
    private fun getRecipeArrays(): List<Array<ItemStack?>> {
        return amplifyRecipe(reduceRecipe())
    }
}
