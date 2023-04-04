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

package de.danielmaile.mpp.item

import org.bukkit.Material

enum class Tools(
    override val material: Material,
    val toolTier: ToolTier,
    val toolType: ToolType
): Item {
    AETHER_WOODEN_AXE(Material.WOODEN_AXE, ToolTier.AETHER_WOOD, ToolType.AXE),
    AETHER_WOODEN_HOE(Material.WOODEN_HOE, ToolTier.AETHER_WOOD, ToolType.HOE),
    AETHER_WOODEN_PICKAXE(Material.WOODEN_PICKAXE, ToolTier.AETHER_WOOD, ToolType.PICKAXE),
    AETHER_WOODEN_SHOVEL(Material.WOODEN_SHOVEL, ToolTier.AETHER_WOOD, ToolType.SHOVEL),
    AETHER_STONE_AXE(Material.STONE_AXE, ToolTier.AETHER_STONE, ToolType.AXE),
    AETHER_STONE_HOE(Material.STONE_HOE, ToolTier.AETHER_STONE, ToolType.HOE),
    AETHER_STONE_PICKAXE(Material.STONE_PICKAXE, ToolTier.AETHER_STONE, ToolType.PICKAXE),
    AETHER_STONE_SHOVEL(Material.STONE_SHOVEL, ToolTier.AETHER_STONE, ToolType.SHOVEL),
    GRAVITITE_AXE(Material.NETHERITE_AXE, ToolTier.GRAVITIE, ToolType.AXE),
    GRAVITITE_HOE(Material.NETHERITE_HOE, ToolTier.GRAVITIE, ToolType.HOE),
    GRAVITITE_PICKAXE(Material.NETHERITE_PICKAXE, ToolTier.GRAVITIE, ToolType.PICKAXE),
    GRAVITITE_SHOVEL(Material.NETHERITE_SHOVEL, ToolTier.GRAVITIE, ToolType.SHOVEL),
    ZANITE_AXE(Material.DIAMOND_AXE, ToolTier.ZANITE, ToolType.AXE),
    ZANITE_HOE(Material.DIAMOND_HOE, ToolTier.ZANITE, ToolType.HOE),
    ZANITE_PICKAXE(Material.DIAMOND_PICKAXE, ToolTier.ZANITE, ToolType.PICKAXE),
    ZANITE_SHOVEL(Material.DIAMOND_SHOVEL, ToolTier.ZANITE, ToolType.SHOVEL);
    override fun register() {
        ItemRegistry.registerItem("mpp:$name", this)
    }
}