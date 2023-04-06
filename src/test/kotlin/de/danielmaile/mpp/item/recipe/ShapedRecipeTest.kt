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
import kotlin.test.Test
import kotlin.test.assertTrue

class ShapedRecipeTest {
    @Test
    fun `A 2x2 recipe should return 4 variants of the craft`() {
        val air = ItemStack(Material.AIR)

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
    fun `A 3x3 recipe should return the same recipe`() {
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

    fun containsRecipes(expected: List<Array<ItemStack?>>, real: List<Array<ItemStack?>>): Boolean {
        for (recipe in expected) {
            var contains = false
            for (realRecipe in real) {
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
                return false
            }
        }
        return true
    }
}
