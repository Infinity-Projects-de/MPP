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

abstract class ArmorRecipe : CraftingRecipe() {

    class HelmetRecipe(
        private val result: ItemStack,
        private val ingotMaterial: ItemStack
    ) : ArmorRecipe() {
        override val recipes: List<Recipe>
            get() = ShapedRecipe(
                result,
                arrayOf(
                    ingotMaterial, ingotMaterial, ingotMaterial,
                    ingotMaterial, null, ingotMaterial,
                    null, null, null
                )
            ).recipes
    }

    class ChestplateRecipe(
        private val result: ItemStack,
        private val ingotMaterial: ItemStack
    ) : ArmorRecipe() {

        override val recipes: List<Recipe>
            get() = ShapedRecipe(
                result,
                arrayOf(
                    ingotMaterial, null, ingotMaterial,
                    ingotMaterial, ingotMaterial, ingotMaterial,
                    ingotMaterial, ingotMaterial, ingotMaterial
                )
            ).recipes
    }

    class LeggingsRecipe(
        private val result: ItemStack,
        private val ingotMaterial: ItemStack
    ) : ArmorRecipe() {
        override val recipes: List<Recipe>
            get() = ShapedRecipe(
                result,
                arrayOf(
                    ingotMaterial, ingotMaterial, ingotMaterial,
                    ingotMaterial, null, ingotMaterial,
                    ingotMaterial, null, ingotMaterial
                )
            ).recipes
    }

    class BootsRecipe(
        private val result: ItemStack,
        private val ingotMaterial: ItemStack
    ) : ArmorRecipe() {
        override val recipes: List<Recipe>
            get() = ShapedRecipe(
                result,
                arrayOf(
                    ingotMaterial, ingotMaterial, ingotMaterial,
                    ingotMaterial, null, ingotMaterial,
                    null, null, null
                )
            ).recipes
    }
}
