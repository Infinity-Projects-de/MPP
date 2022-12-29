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

abstract class CraftingRecipe : Recipe() {

    class StickRecipe(
        result: ItemType,
        woodMaterial: ItemType
    ) : ToolRecipe() {

        private val stickTopLeft = ShapedRecipe(
            result, 4, listOf(
                woodMaterial, null, null,
                woodMaterial, null, null,
                null, null, null
            )
        ).spigotRecipes

        private val stickTopMiddle = ShapedRecipe(
            result, 4, listOf(
                null, woodMaterial, null,
                null, woodMaterial, null,
                null, null, null
            )
        ).spigotRecipes

        private val stickTopRight = ShapedRecipe(
            result, 4, listOf(
                null, null, woodMaterial,
                null, null, woodMaterial,
                null, null, null
            )
        ).spigotRecipes

        private val stickBottomLeft = ShapedRecipe(
            result, 4, listOf(
                null, null, null,
                woodMaterial, null, null,
                woodMaterial, null, null
            )
        ).spigotRecipes

        private val stickBottomMiddle = ShapedRecipe(
            result, 4, listOf(
                null, null, null,
                null, woodMaterial, null,
                null, woodMaterial, null
            )
        ).spigotRecipes

        private val stickBottomRight = ShapedRecipe(
            result, 4, listOf(
                null, null, null,
                null, null, woodMaterial,
                null, null, woodMaterial
            )
        ).spigotRecipes

        override val spigotRecipes: List<org.bukkit.inventory.Recipe>
            get() = arrayListOf(
                stickTopLeft,
                stickTopMiddle,
                stickTopRight,
                stickBottomLeft,
                stickBottomMiddle,
                stickBottomRight
            ).flatten()
    }
}