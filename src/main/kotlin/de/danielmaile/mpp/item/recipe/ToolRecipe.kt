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

abstract class ToolRecipe : CraftingRecipe() {

    class SwordRecipe(
        result: ItemStack,
        ingotMaterial: ItemStack,
        stickMaterial: ItemStack
    ) : ToolRecipe() {

        private val swordLeft = ShapedRecipe(
            result,
            arrayOf(
                ingotMaterial, null, null,
                ingotMaterial, null, null,
                stickMaterial, null, null
            )
        ).spigotRecipes

        private val swordMiddle = ShapedRecipe(
            result,
            arrayOf(
                null, ingotMaterial, null,
                null, ingotMaterial, null,
                null, stickMaterial, null
            )
        ).spigotRecipes

        private val swordRight = ShapedRecipe(
            result,
            arrayOf(
                null, null, ingotMaterial,
                null, null, ingotMaterial,
                null, null, stickMaterial
            )
        ).spigotRecipes

        override val spigotRecipes: List<Recipe>
            get() = arrayListOf(swordLeft, swordMiddle, swordRight).flatten()
    }

    class PickaxeRecipe(
        private val result: ItemStack,
        private val ingotMaterial: ItemStack,
        private val stickMaterial: ItemStack
    ) : ToolRecipe() {

        override val spigotRecipes: List<Recipe>
            get() = ShapedRecipe(
                result,
                arrayOf(
                    ingotMaterial, ingotMaterial, ingotMaterial,
                    null, stickMaterial, null,
                    null, stickMaterial, null
                )
            ).spigotRecipes
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
        ).spigotRecipes

        private val axeRight = ShapedRecipe(
            result,
            arrayOf(
                null, ingotMaterial, ingotMaterial,
                null, stickMaterial, ingotMaterial,
                null, stickMaterial, null
            )
        ).spigotRecipes

        override val spigotRecipes: List<Recipe>
            get() = arrayListOf(axeLeft, axeRight).flatten()
    }

    class ShovelRecipe(
        result: ItemStack,
        ingotMaterial: ItemStack,
        stickMaterial: ItemStack
    ) : ToolRecipe() {

        private val shovelLeft = ShapedRecipe(
            result,
            arrayOf(
                ingotMaterial, null, null,
                stickMaterial, null, null,
                stickMaterial, null, null
            )
        ).spigotRecipes

        private val shovelMiddle = ShapedRecipe(
            result,
            arrayOf(
                null, ingotMaterial, null,
                null, stickMaterial, null,
                null, stickMaterial, null
            )
        ).spigotRecipes

        private val shovelRight = ShapedRecipe(
            result,
            arrayOf(
                null, null, ingotMaterial,
                null, null, stickMaterial,
                null, null, stickMaterial
            )
        ).spigotRecipes

        override val spigotRecipes: List<Recipe>
            get() = arrayListOf(shovelLeft, shovelMiddle, shovelRight).flatten()
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
        ).spigotRecipes

        private val hoeMiddleLeft = ShapedRecipe(
            result,
            arrayOf(
                ingotMaterial, ingotMaterial, null,
                null, stickMaterial, null,
                null, stickMaterial, null
            )
        ).spigotRecipes

        private val hoeMiddleRight = ShapedRecipe(
            result,
            arrayOf(
                null, ingotMaterial, ingotMaterial,
                null, stickMaterial, null,
                null, stickMaterial, null
            )
        ).spigotRecipes

        private val hoeRight = ShapedRecipe(
            result,
            arrayOf(
                null, ingotMaterial, ingotMaterial,
                null, null, stickMaterial,
                null, null, stickMaterial
            )
        ).spigotRecipes

        override val spigotRecipes: List<Recipe>
            get() = arrayListOf(hoeLeft, hoeMiddleLeft, hoeMiddleRight, hoeRight).flatten()
    }
}
