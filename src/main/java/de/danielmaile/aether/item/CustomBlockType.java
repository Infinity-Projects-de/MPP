package de.danielmaile.aether.item;

import org.bukkit.Material;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum CustomBlockType
{
    AETHER_OAK_LOG(Material.OAK_LOG, CustomItemType.AETHER_OAK_LOG),
    AETHER_SPRUCE_LOG(Material.SPRUCE_LOG, CustomItemType.AETHER_SPRUCE_LOG),
    AETHER_BIRCH_LOG(Material.BIRCH_LOG, CustomItemType.AETHER_BIRCH_LOG),
    AETHER_JUNGLE_LOG(Material.JUNGLE_LOG, CustomItemType.AETHER_JUNGLE_LOG),
    AETHER_ACACIA_LOG(Material.ACACIA_LOG, CustomItemType.AETHER_ACACIA_LOG),
    AETHER_DARK_OAK_LOG(Material.DARK_OAK_LOG, CustomItemType.AETHER_DARK_OAK_LOG),
    AETHER_OAK_PLANKS(Material.OAK_PLANKS, CustomItemType.AETHER_OAK_PLANKS),
    AETHER_SPRUCE_PLANKS(Material.SPRUCE_PLANKS, CustomItemType.AETHER_SPRUCE_PLANKS),
    AETHER_BIRCH_PLANKS(Material.BIRCH_PLANKS, CustomItemType.AETHER_SPRUCE_PLANKS),
    AETHER_JUNGLE_PLANKS(Material.JUNGLE_PLANKS, CustomItemType.AETHER_JUNGLE_PLANKS),
    AETHER_ACACIA_PLANKS(Material.ACACIA_PLANKS, CustomItemType.AETHER_ACACIA_PLANKS),
    AETHER_DARK_OAK_PLANKS(Material.DARK_OAK_PLANKS, CustomItemType.AETHER_DARK_OAK_PLANKS),
    ZANITE_ORE(Material.DIAMOND_ORE, CustomItemType.ZANITE_STONE),
    GRAVITITE_ORE(Material.ANCIENT_DEBRIS, CustomItemType.GRAVITITE_ORE),
    AETHER_STONE(Material.STONE, CustomItemType.AETHER_STONE);

    private final Material blockMaterial;

    public CustomItemType getItemDrop()
    {
        return itemDrop;
    }

    private final CustomItemType itemDrop;

    CustomBlockType(Material blockMaterial, CustomItemType itemDrop)
    {
        this.blockMaterial = blockMaterial;
        this.itemDrop = itemDrop;
    }

    @Nullable
    public static CustomBlockType getFromMaterial(Material material)
    {
        return Arrays.stream(CustomBlockType.values())
                .filter(customBlockType -> customBlockType.blockMaterial == material)
                .findFirst().orElse(null);
    }
}
