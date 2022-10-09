package de.danielmaile.mpp.item

import org.bukkit.Material

enum class BlockType(val blockMaterial: Material, val itemDrop: ItemType) {
    AETHER_OAK_LOG(Material.OAK_LOG, ItemType.AETHER_OAK_LOG),
    AETHER_SPRUCE_LOG(Material.SPRUCE_LOG, ItemType.AETHER_SPRUCE_LOG),
    AETHER_BIRCH_LOG(Material.BIRCH_LOG, ItemType.AETHER_BIRCH_LOG),
    AETHER_JUNGLE_LOG(Material.JUNGLE_LOG, ItemType.AETHER_JUNGLE_LOG),
    AETHER_ACACIA_LOG(Material.ACACIA_LOG, ItemType.AETHER_ACACIA_LOG),
    AETHER_DARK_OAK_LOG(Material.DARK_OAK_LOG, ItemType.AETHER_DARK_OAK_LOG),
    AETHER_OAK_PLANKS(Material.OAK_PLANKS, ItemType.AETHER_OAK_PLANKS),
    AETHER_SPRUCE_PLANKS(Material.SPRUCE_PLANKS, ItemType.AETHER_SPRUCE_PLANKS),
    AETHER_BIRCH_PLANKS(Material.BIRCH_PLANKS, ItemType.AETHER_SPRUCE_PLANKS),
    AETHER_JUNGLE_PLANKS(Material.JUNGLE_PLANKS, ItemType.AETHER_JUNGLE_PLANKS),
    AETHER_ACACIA_PLANKS(Material.ACACIA_PLANKS, ItemType.AETHER_ACACIA_PLANKS),
    AETHER_DARK_OAK_PLANKS(Material.DARK_OAK_PLANKS, ItemType.AETHER_DARK_OAK_PLANKS),
    ZANITE_ORE(Material.DIAMOND_ORE, ItemType.ZANITE_STONE),
    GRAVITITE_ORE(Material.ANCIENT_DEBRIS, ItemType.GRAVITITE_ORE),
    AETHER_STONE(Material.STONE, ItemType.AETHER_STONE),
    CLOUD_HEAL(Material.PINK_STAINED_GLASS, ItemType.CLOUD_HEAL),
    CLOUD_SLOW_FALLING(Material.WHITE_STAINED_GLASS, ItemType.CLOUD_SLOW_FALLING),
    CLOUD_SPEED(Material.YELLOW_STAINED_GLASS, ItemType.CLOUD_SPEED),
    CLOUD_JUMP(Material.LIME_STAINED_GLASS, ItemType.CLOUD_JUMP),
    CLOUD_HEAL2(Material.RED_STAINED_GLASS, ItemType.CLOUD_HEAL2),
    CLOUD_SPEED2(Material.ORANGE_STAINED_GLASS, ItemType.CLOUD_SPEED2),
    CLOUD_JUMP2(Material.GREEN_STAINED_GLASS, ItemType.CLOUD_JUMP2);

    companion object {

        @JvmStatic
        fun fromMaterial(material: Material): BlockType? {
            return values().firstOrNull {
                it.blockMaterial == material
            }
        }
    }
}