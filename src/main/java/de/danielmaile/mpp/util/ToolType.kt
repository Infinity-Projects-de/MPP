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

        // Returns a tool type based on the given material.
        // If none was found return null
        @JvmStatic
        fun fromMaterial(material: Material): ToolType? {
            return values().firstOrNull {
                it.materialToSpeedMapping.containsKey(material)
            }
        }
    }
}