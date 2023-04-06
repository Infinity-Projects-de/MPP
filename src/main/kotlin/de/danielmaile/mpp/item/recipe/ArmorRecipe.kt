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

abstract class ArmorRecipe : CraftingRecipe() {

    class HelmetRecipe(
        result: ItemStack,
        ingotMaterial: ItemStack
    ) : ArmorRecipe() {

        private val helmetTop = ShapedRecipe(
            result,
            arrayOf(
                ingotMaterial, ingotMaterial, ingotMaterial,
                ingotMaterial, null, ingotMaterial,
                null, null, null
            )
        ).spigotRecipes

        private val helmetBottom = ShapedRecipe(
            result,
            arrayOf(
                null, null, null,
                ingotMaterial, ingotMaterial, ingotMaterial,
                ingotMaterial, null, ingotMaterial
            )
        ).spigotRecipes

        override val spigotRecipes: List<Recipe>
            get() = arrayListOf(helmetTop, helmetBottom).flatten()
    }

    class ChestplateRecipe(
        private val result: ItemStack,
        private val ingotMaterial: ItemStack
    ) : ArmorRecipe() {

        override val spigotRecipes: List<Recipe>
            get() = ShapedRecipe(
                result,
                arrayOf(
                    ingotMaterial, null, ingotMaterial,
                    ingotMaterial, ingotMaterial, ingotMaterial,
                    ingotMaterial, ingotMaterial, ingotMaterial
                )
            ).spigotRecipes
    }

    class LeggingsRecipe(
        private val result: ItemStack,
        private val ingotMaterial: ItemStack
    ) : ArmorRecipe() {

        override val spigotRecipes: List<Recipe>
            get() = ShapedRecipe(
                result,
                arrayOf(
                    ingotMaterial, ingotMaterial, ingotMaterial,
                    ingotMaterial, null, ingotMaterial,
                    ingotMaterial, null, ingotMaterial
                )
            ).spigotRecipes
    }

    class BootsRecipe(
        result: ItemStack,
        ingotMaterial: ItemStack
    ) : ArmorRecipe() {

        private val bootsTop = ShapedRecipe(
            result,
            arrayOf(
                ingotMaterial, null, ingotMaterial,
                ingotMaterial, null, ingotMaterial,
                null, null, null
            )
        ).spigotRecipes

        private val bootsBottom = ShapedRecipe(
            result,
            arrayOf(
                null, null, null,
                ingotMaterial, null, ingotMaterial,
                ingotMaterial, null, ingotMaterial
            )
        ).spigotRecipes

        override val spigotRecipes: List<Recipe>
            get() = arrayListOf(bootsTop, bootsBottom).flatten()
    }
}
