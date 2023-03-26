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

package de.danielmaile.mpp.util

import org.bukkit.Material

enum class ToolType(val materialToSpeedMapping: Map<Material, Int>) {

    SHOVEL(
        mapOf(
            Material.WOODEN_SHOVEL to 2,
            Material.STONE_SHOVEL to 4,
            Material.IRON_SHOVEL to 6,
            Material.DIAMOND_SHOVEL to 8,
            Material.NETHERITE_SHOVEL to 9,
            Material.GOLDEN_SHOVEL to 12
        )
    ),
    PICKAXE(
        mapOf(
            Material.WOODEN_PICKAXE to 2,
            Material.STONE_PICKAXE to 4,
            Material.IRON_PICKAXE to 6,
            Material.DIAMOND_PICKAXE to 8,
            Material.NETHERITE_PICKAXE to 9,
            Material.GOLDEN_PICKAXE to 12
        )
    ),
    AXE(
        mapOf(
            Material.WOODEN_AXE to 2,
            Material.STONE_AXE to 4,
            Material.IRON_AXE to 6,
            Material.DIAMOND_AXE to 8,
            Material.NETHERITE_AXE to 9,
            Material.GOLDEN_AXE to 12
        )
    ),
    HOE(
        mapOf(
            Material.WOODEN_HOE to 2,
            Material.STONE_HOE to 4,
            Material.IRON_HOE to 6,
            Material.DIAMOND_HOE to 8,
            Material.NETHERITE_HOE to 9,
            Material.GOLDEN_HOE to 12
        )
    );

    companion object {

        /**
         * @return a tool type based on the given material or null if none was found.
         */
        @JvmStatic
        fun fromMaterial(material: Material): ToolType? {
            return values().firstOrNull {
                it.materialToSpeedMapping.containsKey(material)
            }
        }
    }
}
