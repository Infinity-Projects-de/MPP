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
    @Deprecated("No reason of using this val that only uses the function")
    override val spigotRecipes: List<Recipe>
        get() = getRecipes()

    private val possibleIngredients = charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J')

    fun getRecipes(): List<Recipe> {
        val recipes: MutableList<Recipe> = mutableListOf()

        val ingredients = mainRecipe.distinct()

        val ingredientsToLetter: HashMap<ItemStack?, Char> = hashMapOf()
        for ((i, itemStack) in ingredients.withIndex()) {
            ingredientsToLetter[itemStack] = possibleIngredients[i]
        }

        for (recipeArray in getRecipeArrays()) {
            val shapedRecipe = ShapedRecipe(getRandomRecipeKey(), result)
            val recipeBuilder: StringBuilder = StringBuilder()
            for (itemStack in recipeArray) {
                recipeBuilder.append("${ingredientsToLetter[itemStack]} ")
            }
            val recipe = recipeBuilder.trim().toString()
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

    private fun getRecipeArrays(): List<Array<ItemStack?>> {
        var listOfRecipes: MutableList<Array<ItemStack?>> = mutableListOf()
        val size = getRecipeSize()
        println("${size.width}x${size.height}")
        var reducedRowRecipe: MutableList<ItemStack?> = mutableListOf()
        var reducedRecipe: MutableList<ItemStack?> = mutableListOf()

        if (size.height != 3) {
            for (row in 0 until 3) {
                var emptyRow = true
                for (col in 0 until 3) {
                    if (mainRecipe[row * 3 + col] != null) emptyRow = false
                }
                if (!emptyRow) {
                    for (col in 0 until 3) {
                        reducedRowRecipe.add(mainRecipe[row * 3 + col])
                    }
                }
            }
        } else {
            reducedRowRecipe = mainRecipe.toMutableList()
        }

        if (size.width != 3) {
            for (col in 0 until 3) {
                var emptyCol = true
                for (row in 0 until size.height) {
                    if (reducedRowRecipe[row * 3 + col] != null) emptyCol = false
                }

                if (!emptyCol) {
                    for (row in 0 until size.height) {
                        reducedRecipe.add(reducedRowRecipe[row * 3 + col])
                    }
                }
            }
        } else {
            reducedRecipe = reducedRowRecipe
        }

        val rowAmpliatedRecipes: MutableList<Array<ItemStack?>> = mutableListOf()

        if (size.height != 3) {
            for (i in 0..3 - size.height) {
                val amplifiedRecipe: MutableList<ItemStack?> = mutableListOf()

                for (row in 0 until 3) {
                    for (col in 0 until size.width) {
                        if (row - i < 0 || row - i >= size.height) {
                            amplifiedRecipe.add(null)
                        } else {
                            amplifiedRecipe.add(reducedRecipe[(row - i) * size.width + col])
                        }
                    }
                }
                rowAmpliatedRecipes.add(amplifiedRecipe.toTypedArray())
            }
        } else {
            rowAmpliatedRecipes.add(reducedRecipe.toTypedArray())
        }

        if (size.width != 3) {
            for (recipe in rowAmpliatedRecipes) {
                for (i in 0..3 - size.height) {
                    val amplifiedRecipe: MutableList<ItemStack?> = mutableListOf()
                    for (row in 0 until 3) {
                        for (col in 0 until 3) {
                            if (col - i < 0 || col - i >= size.width) {
                                amplifiedRecipe.add(null)
                            } else {
                                amplifiedRecipe.add(recipe[row * size.width + (col - i)])
                            }
                        }
                    }
                    listOfRecipes.add(amplifiedRecipe.toTypedArray())
                }
            }
        } else {
            listOfRecipes = rowAmpliatedRecipes
        }

        return listOfRecipes.toList()
    }

    private fun getRecipeSize(): RecipeSize {
        var firstRow = -1
        var lastRow = -1

        var firstCol = -1
        var lastCol = -1

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (mainRecipe[i * 3 + j] != null) {
                    if (firstRow == -1) firstRow = i
                    lastRow = i
                }
                if (mainRecipe[j * 3 + i] != null) {
                    if (firstCol == -1) firstCol = j
                    lastCol = j
                }
            }
        }

        return RecipeSize(lastRow - firstRow + 1, lastCol - firstCol + 1)
    }

    private class RecipeSize(val width: Int, val height: Int)
}
