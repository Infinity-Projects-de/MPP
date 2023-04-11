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

package de.danielmaile.mpp.item.recipe

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ShapedRecipeTest {
    private val air = ItemStack(Material.AIR)
    private val dirt = ItemStack(Material.DIRT)

    @Test
    fun `Contains Recipes method should check that every recipe is contained (true)`() {
        val posCraft1: List<Array<ItemStack?>> = listOf(
            arrayOf(air, null, null, air, null, null, null, air, null),
            arrayOf(null, air, null, null, air, null, null, null, null),
            arrayOf(null, null, air, air, null, air, null, null, null),
            arrayOf(null, air, null, air, air, null, null, air, null),
            arrayOf(air, air, null, null, air, air, null, null, air),
            arrayOf(null, air, air, air, air, null, air, air, null)
        )

        val posCraft2: List<Array<ItemStack?>> = listOf(
            arrayOf(null, air, null, null, air, null, null, null, null),
            arrayOf(null, air, air, air, air, null, air, air, null),
            arrayOf(null, null, air, air, null, air, null, null, null),
            arrayOf(air, null, null, air, null, null, null, air, null),
            arrayOf(air, air, null, null, air, air, null, null, air),
            arrayOf(null, air, null, air, air, null, null, air, null)
        )

        assertTrue(containsRecipes(posCraft1, posCraft2))
    }

    @Test
    fun `Contains Recipes method should check that every recipe is contained (false)`() {
        val posCraft1: List<Array<ItemStack?>> = listOf(
            arrayOf(air, null, null, air, null, null, null, air, null),
            arrayOf(null, air, null, null, air, null, null, null, null),
            arrayOf(null, null, air, air, null, air, null, null, null),
            arrayOf(null, air, null, air, air, null, null, air, null),
            arrayOf(air, air, null, null, air, air, null, null, air),
            arrayOf(air, air, air, air, air, air, air, air, air) // Does not contain
        )

        val posCraft2: List<Array<ItemStack?>> = listOf(
            arrayOf(null, air, null, null, air, null, null, null, null),
            arrayOf(null, air, air, air, air, null, air, air, null),
            arrayOf(null, null, air, air, null, air, null, null, null),
            arrayOf(air, null, null, air, null, null, null, air, null),
            arrayOf(air, air, null, null, air, air, null, null, air),
            arrayOf(null, air, null, air, air, null, null, air, null)
        )

        assertFalse(containsRecipes(posCraft1, posCraft2))
    }

    @Test
    fun `A Simple 2x2 recipe should return 4 variants of the craft`() {
        val recipe = ShapedRecipe(air, arrayOf(air, air, null, air, air, null, null, null, null))
        val method = ShapedRecipe::class.java.getDeclaredMethod("getRecipeArrays")
        method.isAccessible = true
        val recipeArrays: List<Array<ItemStack?>> = method.invoke(recipe) as List<Array<ItemStack?>>

        val possibleCrafts: List<Array<ItemStack?>> = listOf(
            arrayOf(
                air, air, null,
                air, air, null,
                null, null, null
            ),
            arrayOf(
                null, air, air,
                null, air, air,
                null, null, null
            ),
            arrayOf(
                null, null, null,
                air, air, null,
                air, air, null
            ),
            arrayOf(
                null, null, null,
                null, air, air,
                null, air, air
            )
        )

        assertTrue(containsRecipes(possibleCrafts, recipeArrays))
    }

    @Test
    fun `A Complex 2x2 recipe should not be rotated and return 4 variants of the craft`() {
        val recipe = ShapedRecipe(air, arrayOf(air, air, null, dirt, dirt, null, null, null, null))
        val method = ShapedRecipe::class.java.getDeclaredMethod("getRecipeArrays")
        method.isAccessible = true
        val recipeArrays: List<Array<ItemStack?>> = method.invoke(recipe) as List<Array<ItemStack?>>

        val possibleCrafts: List<Array<ItemStack?>> = listOf(
            arrayOf(
                air, dirt, null,
                air, dirt, null,
                null, null, null
            ),
            arrayOf(
                null, air, dirt,
                null, air, dirt,
                null, null, null
            ),
            arrayOf(
                null, null, null,
                air, dirt, null,
                air, dirt, null
            ),
            arrayOf(
                null, null, null,
                null, air, dirt,
                null, air, dirt
            )
        )

        assertTrue(containsRecipes(possibleCrafts, recipeArrays))
    }

    @Test
    fun `A simple 3x3 recipe should return the same recipe`() {
        val air = ItemStack(Material.AIR)

        val recipe = ShapedRecipe(air, arrayOf(air, air, air, air, air, air, air, air, air))
        val method = ShapedRecipe::class.java.getDeclaredMethod("getRecipeArrays")
        method.isAccessible = true
        val recipeArrays: List<Array<ItemStack?>> = method.invoke(recipe) as List<Array<ItemStack?>>

        val possibleCrafts: List<Array<ItemStack?>> = listOf(
            arrayOf(
                air, air, air,
                air, air, air,
                air, air, air
            )
        )

        assertTrue(containsRecipes(possibleCrafts, recipeArrays))
    }

    @Ignore
    fun `A Simple 2x3 recipe should return 2 variants of the craft`() {
        val recipe = ShapedRecipe(air, arrayOf(air, air, air, air, air, air, null, null, null))
        val method = ShapedRecipe::class.java.getDeclaredMethod("getRecipeArrays")
        method.isAccessible = true
        val recipeArrays: List<Array<ItemStack?>> = method.invoke(recipe) as List<Array<ItemStack?>>

        val possibleCrafts: List<Array<ItemStack?>> = listOf(
            arrayOf(
                air, air, air,
                air, air, air,
                null, null, null
            ),
            arrayOf(
                null, null, null,
                air, air, air,
                air, air, air
            )
        )

        assertTrue(containsRecipes(possibleCrafts, recipeArrays))
    }

    @Test
    fun `A full kind of complex 3x3 recipe should not be rotated and should return the same recipe`() {
        val recipe = ShapedRecipe(air, arrayOf(air, dirt, air, dirt, air, air, air, air, dirt))
        val method = ShapedRecipe::class.java.getDeclaredMethod("getRecipeArrays")
        method.isAccessible = true
        val recipeArrays: List<Array<ItemStack?>> = method.invoke(recipe) as List<Array<ItemStack?>>

        val possibleCrafts: List<Array<ItemStack?>> = listOf(
            arrayOf(
                air, dirt, air,
                dirt, air, air,
                air, air, dirt
            )
        )

        assertTrue(containsRecipes(possibleCrafts, recipeArrays))
    }

    @Ignore
    fun `A complex 3x3 recipe should not be rotated and should return the same recipe`() {
        val recipe = ShapedRecipe(
            air,
            arrayOf(
                null, dirt, null,
                dirt, null, air,
                air, null, null
            )
        )
        val method = ShapedRecipe::class.java.getDeclaredMethod("getRecipeArrays")
        method.isAccessible = true
        val recipeArrays: List<Array<ItemStack?>> = method.invoke(recipe) as List<Array<ItemStack?>>

        val possibleCrafts: List<Array<ItemStack?>> = listOf(
            arrayOf(
                null, dirt, null,
                dirt, null, air,
                air, null, null
            )
        )

        assertTrue(containsRecipes(possibleCrafts, recipeArrays))
    }
    fun containsRecipes(expected: List<Array<ItemStack?>>, real: List<Array<ItemStack?>>): Boolean {
        for (recipe in real) {
            var contains = false
            for (realRecipe in expected) {
                for ((i, item) in recipe.withIndex()) {
                    if (realRecipe[i]?.type != item?.type) {
                        break
                    }
                    if (i == 8) {
                        contains = true
                    }
                }
            }
            if (!contains) {
                for (realRecipe in real) {
                    println("---")
                    for (row in 0 until 3) {
                        for (col in 0 until 3) {
                            print((realRecipe[row * 3 + col]?.type?.name ?: "NULL") + " ")
                        }
                        println()
                    }
                }
                return false
            }
        }
        return true
    }
}
