/*
 * This file is part of MPP.
 * Copyright (c) 2022-2023 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.item.recipe.recipes

import de.danielmaile.mpp.item.recipe.CraftingRecipe
import de.danielmaile.mpp.item.recipe.ShapedRecipe
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe

abstract class ToolRecipe : CraftingRecipe() {

    class SwordRecipe(
        private val result: ItemStack,
        private val ingotMaterial: ItemStack,
        private val stickMaterial: ItemStack
    ) : ToolRecipe() {

        override val recipes: List<Recipe>
            get() = ShapedRecipe(
                result,
                arrayOf(
                    ingotMaterial, null, null,
                    ingotMaterial, null, null,
                    stickMaterial, null, null
                )
            ).recipes
    }

    class PickaxeRecipe(
        private val result: ItemStack,
        private val ingotMaterial: ItemStack,
        private val stickMaterial: ItemStack
    ) : ToolRecipe() {

        override val recipes: List<Recipe>
            get() = ShapedRecipe(
                result,
                arrayOf(
                    ingotMaterial, ingotMaterial, ingotMaterial,
                    null, stickMaterial, null,
                    null, stickMaterial, null
                )
            ).recipes
    }

    class AxeRecipe(
        result: ItemStack,
        ingotMaterial: ItemStack,
        stickMaterial: ItemStack
    ) : ToolRecipe() {

        private val axeLeft = ShapedRecipe(
            result,
            arrayOf(
                ingotMaterial, ingotMaterial, null,
                ingotMaterial, stickMaterial, null,
                null, stickMaterial, null
            )
        ).recipes

        private val axeRight = ShapedRecipe(
            result,
            arrayOf(
                ingotMaterial, ingotMaterial, null,
                stickMaterial, ingotMaterial, null,
                stickMaterial, null, null
            )
        ).recipes

        override val recipes: List<Recipe>
            get() = arrayListOf(axeLeft, axeRight).flatten()
    }

    class ShovelRecipe(
        private val result: ItemStack,
        private val ingotMaterial: ItemStack,
        private val stickMaterial: ItemStack
    ) : ToolRecipe() {

        override val recipes: List<Recipe>
            get() = ShapedRecipe(
                result,
                arrayOf(
                    ingotMaterial, null, null,
                    stickMaterial, null, null,
                    stickMaterial, null, null
                )
            ).recipes
    }

    class HoeRecipe(
        result: ItemStack,
        ingotMaterial: ItemStack,
        stickMaterial: ItemStack
    ) : ToolRecipe() {

        private val hoeLeft = ShapedRecipe(
            result,
            arrayOf(
                ingotMaterial, ingotMaterial, null,
                stickMaterial, null, null,
                stickMaterial, null, null
            )
        ).recipes

        private val hoeRight = ShapedRecipe(
            result,
            arrayOf(
                ingotMaterial, ingotMaterial, null,
                null, stickMaterial, null,
                null, stickMaterial, null
            )
        ).recipes

        override val recipes: List<Recipe>
            get() = arrayListOf(hoeLeft, hoeRight).flatten()
    }
}
