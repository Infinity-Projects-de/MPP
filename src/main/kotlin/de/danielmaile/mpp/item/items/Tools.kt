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

package de.danielmaile.mpp.item.items

import de.danielmaile.mpp.item.Item
import de.danielmaile.mpp.item.ToolTier
import de.danielmaile.mpp.item.recipe.recipes.ToolRecipe
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe

enum class Tools(
    val toolTier: ToolTier,
    val toolType: ToolType
) : Item {
    AETHER_WOODEN_AXE(ToolTier.AETHER_WOOD, ToolType.AXE),
    AETHER_WOODEN_HOE(ToolTier.AETHER_WOOD, ToolType.HOE),
    AETHER_WOODEN_PICKAXE(ToolTier.AETHER_WOOD, ToolType.PICKAXE),
    AETHER_WOODEN_SHOVEL(ToolTier.AETHER_WOOD, ToolType.SHOVEL),
    AETHER_STONE_AXE(ToolTier.AETHER_STONE, ToolType.AXE),
    AETHER_STONE_HOE(ToolTier.AETHER_STONE, ToolType.HOE),
    AETHER_STONE_PICKAXE(ToolTier.AETHER_STONE, ToolType.PICKAXE),
    AETHER_STONE_SHOVEL(ToolTier.AETHER_STONE, ToolType.SHOVEL),
    GRAVITITE_AXE(ToolTier.GRAVITITE, ToolType.AXE),
    GRAVITITE_HOE(ToolTier.GRAVITITE, ToolType.HOE),
    GRAVITITE_PICKAXE(ToolTier.GRAVITITE, ToolType.PICKAXE),
    GRAVITITE_SHOVEL(ToolTier.GRAVITITE, ToolType.SHOVEL),
    ZANITE_AXE(ToolTier.ZANITE, ToolType.AXE),
    ZANITE_HOE(ToolTier.ZANITE, ToolType.HOE),
    ZANITE_PICKAXE(ToolTier.ZANITE, ToolType.PICKAXE),
    ZANITE_SHOVEL(ToolTier.ZANITE, ToolType.SHOVEL);

    override val material: Material
        get() = when (toolType) {
            ToolType.AXE -> Material.WOODEN_AXE
            ToolType.PICKAXE -> Material.WOODEN_PICKAXE
            ToolType.HOE -> Material.WOODEN_HOE
            ToolType.SHOVEL -> Material.WOODEN_SHOVEL
        }

    override fun getRecipes(): List<Recipe> {
        val ingredients = toolTier.toolIngredients
        val stick = ingredients.stickIngredient?.itemStack?.let { it(1) } ?: ItemStack(Material.STICK)
        val material = ingredients.material.itemStack(1)
        val itemStack = this.itemStack(1)

        val recipes: List<Recipe> = when (toolType) {
            ToolType.AXE -> ToolRecipe.AxeRecipe(itemStack, material, stick)
            ToolType.PICKAXE -> ToolRecipe.PickaxeRecipe(itemStack, material, stick)
            ToolType.SHOVEL -> ToolRecipe.ShovelRecipe(itemStack, material, stick)
            ToolType.HOE -> ToolRecipe.HoeRecipe(itemStack, material, stick)
        }.recipes

        return recipes
    }

    enum class ToolType {
        AXE,
        PICKAXE,
        SHOVEL,
        HOE
    }
}
