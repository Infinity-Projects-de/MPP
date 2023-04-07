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

    override val modelIDOffset: Int = 1300

    override val material: Material
        get() = when (toolType) {
            ToolType.AXE -> Material.WOODEN_AXE
            ToolType.PICKAXE -> Material.WOODEN_PICKAXE
            ToolType.HOE -> Material.WOODEN_HOE
            ToolType.SHOVEL -> Material.WOODEN_SHOVEL
        }
}
