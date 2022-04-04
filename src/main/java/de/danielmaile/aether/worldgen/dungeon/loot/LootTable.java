package de.danielmaile.aether.worldgen.dungeon.loot;

import de.danielmaile.aether.item.ItemType;
import org.bukkit.Material;

import java.util.Random;

public enum LootTable
{
    NORMAL(0.7, 3, 8,
            new Loot(0.6, ItemType.AETHER_STONE, 3, 16),
            new Loot(0.6, ItemType.AETHER_STICK, 1, 5),
            new Loot(0.4, ItemType.AETHER_WOODEN_PICKAXE, 1, 2),
            new Loot(0.4, ItemType.AETHER_WOODEN_SHOVEL, 1, 2),
            new Loot(0.4, ItemType.AETHER_WOODEN_AXE, 1, 2),
            new Loot(0.3, Material.BREAD, 1, 30),
            new Loot(0.3, Material.COOKED_BEEF, 1, 12),
            new Loot(0.6, Material.STONE, 1, 14),
            new Loot(0.6, Material.STICK, 1, 13),
            new Loot(0.4, ItemType.AETHER_WOODEN_SWORD, 1, 2),
            new Loot(0.4, ItemType.AETHER_STONE_SWORD, 1, 2),
            new Loot(0.4, ItemType.AETHER_STONE_PICKAXE, 1, 2),
            new Loot(0.4, ItemType.AETHER_STONE_SHOVEL, 1, 2),
            new Loot(0.4, Material.IRON_HELMET, 1, 2),
            new Loot(0.4, Material.IRON_CHESTPLATE, 1, 1),
            new Loot(0.4, Material.IRON_LEGGINGS, 1, 1),
            new Loot(0.4, Material.IRON_BOOTS, 1, 1),
            new Loot(0.4, Material.DIAMOND_CHESTPLATE, 1, 1)),
    RARE(0.1, 1, 2,
            new Loot(0.6, ItemType.AETHER_STONE, 3, 16),
            new Loot(0.6, ItemType.AETHER_STICK, 1, 5),
            new Loot(0.4, ItemType.AETHER_WOODEN_PICKAXE, 1, 2),
            new Loot(0.4, ItemType.AETHER_WOODEN_SHOVEL, 1, 2),
            new Loot(0.4, ItemType.AETHER_WOODEN_AXE, 1, 2),
            new Loot(0.3, Material.BREAD, 1, 30),
            new Loot(0.3, Material.COOKED_BEEF, 1, 12),
            new Loot(0.6, Material.STONE, 1, 14),
            new Loot(0.6, Material.STICK, 1, 13),
            new Loot(0.4, ItemType.AETHER_WOODEN_SWORD, 1, 2),
            new Loot(0.4, ItemType.AETHER_STONE_SWORD, 1, 2),
            new Loot(0.4, ItemType.AETHER_STONE_PICKAXE, 1, 2),
            new Loot(0.4, ItemType.AETHER_STONE_SHOVEL, 1, 2),
            new Loot(0.4, Material.IRON_HELMET, 1, 2),
            new Loot(0.4, Material.IRON_CHESTPLATE, 1, 1),
            new Loot(0.4, Material.IRON_LEGGINGS, 1, 1),
            new Loot(0.4, Material.IRON_BOOTS, 1, 1),
            new Loot(0.4, Material.DIAMOND_CHESTPLATE, 1, 1),
            new Loot(0.3, ItemType.ZANITE_STONE, 1, 2),
            new Loot(0.2, ItemType.GRAVITITE_ORE, 1, 2),
            new Loot(0.05, ItemType.FIRE_ESSENCE, 1, 1),
            new Loot(0.05, ItemType.ICE_ESSENCE, 1, 1),
            new Loot(0.05, ItemType.LIGHTNING_ESSENCE, 1, 1));

    private final double weight;
    private final int minItemStacks;
    private final int maxItemStacks;
    private final Loot[] lootList;

    LootTable(double weight, int minItemStacks, int maxItemStacks, Loot... lootList)
    {
        this.weight = weight;
        this.minItemStacks = minItemStacks;
        this.maxItemStacks = maxItemStacks;
        this.lootList = lootList;
    }

    public static double getTotalWeight()
    {
        double totalWeight = 0;
        for (LootTable table : values())
        {
            totalWeight += table.getWeight();
        }
        return totalWeight;
    }

    public double getTotalLootWeight()
    {
        double totalWeight = 0;
        for (Loot loot : lootList)
        {
            totalWeight += loot.getWeight();
        }
        return totalWeight;
    }

    public LootTable.Loot getRandomLoot(Random random)
    {
        double totalWeight = getTotalLootWeight();
        int index = 0;
        for (double randomWeight = random.nextDouble() * totalWeight; index < lootList.length - 1; ++index)
        {
            randomWeight -= lootList[index].getWeight();
            if (randomWeight <= 0.0) break;
        }

        return lootList[index];
    }

    public double getWeight()
    {
        return weight;
    }

    public int getMinItemStacks()
    {
        return minItemStacks;
    }

    public int getMaxItemStacks()
    {
        return maxItemStacks;
    }

    public static class Loot
    {
        private final double weight;
        private final Material material;
        private final ItemType itemType;
        private final int minAmount;
        private final int maxAmount;

        public Loot(double weight, Material material, int minAmount, int maxAmount)
        {
            this.weight = weight;
            this.material = material;
            this.itemType = null;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
        }

        public Loot(double weight, ItemType itemType, int minAmount, int maxAmount)
        {
            this.weight = weight;
            this.material = null;
            this.itemType = itemType;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
        }

        public boolean isCustomItem()
        {
            return itemType != null;
        }

        public int getMaxAmount()
        {
            return maxAmount;
        }

        public int getMinAmount()
        {
            return minAmount;
        }

        public ItemType getItemType()
        {
            return itemType;
        }

        public Material getMaterial()
        {
            return material;
        }

        public double getWeight()
        {
            return weight;
        }
    }
}

