package de.danielmaile.aether.worldgen.dungeon.loot;

import de.danielmaile.aether.Aether;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public record DungeonChest(Random random)
{

    public void instantiate(Location location)
    {
        World world = location.getWorld();
        Block block = world.getBlockAt(location);
        block.setType(Material.CHEST);
        Chest chest = (Chest) block.getState();

        Bukkit.getScheduler().runTaskAsynchronously(Aether.getInstance(), () -> addItems(random, chest.getBlockInventory(), generateItemStacks()));
    }

    //Add items in random positions
    private void addItems(Random random, Inventory inventory, List<ItemStack> stacks)
    {
        for (ItemStack itemStack : stacks)
        {
            //Try at most 3 times to set item randomly. Else add it normally
            int tries = 0;
            while (tries < 3)
            {
                int position = random.nextInt(0, inventory.getSize());
                if (inventory.getItem(position) == null)
                {
                    inventory.setItem(position, itemStack);
                    tries = -1;
                    break;
                }
                else
                {
                    tries++;
                }
            }

            if (tries != -1)
            {
                inventory.addItem(itemStack);
            }
        }
    }

    private List<ItemStack> generateItemStacks()
    {
        List<ItemStack> stacks = new ArrayList<>();
        LootTable table = getRandomLootTable();
        int stackAmount = random.nextInt(table.getMinItemStacks(), table.getMaxItemStacks() + 1);
        for (int i = 0; i < stackAmount; i++)
        {
            LootTable.Loot loot = table.getRandomLoot(random);
            int amount = random.nextInt(loot.getMinAmount(), loot.getMaxAmount() + 1);
            if (amount < 1) continue;
            if (loot.isCustomItem())
            {
                stacks.add(loot.getItemType().getItemStack(amount));
            }
            else
            {
                stacks.add(new ItemStack(loot.getMaterial(), amount));
            }
        }
        return stacks;
    }

    private LootTable getRandomLootTable()
    {
        double totalWeight = LootTable.getTotalWeight();
        int index = 0;
        for (double randomWeight = random.nextDouble() * totalWeight; index < LootTable.values().length - 1; ++index)
        {
            randomWeight -= LootTable.values()[index].getWeight();
            if (randomWeight <= 0.0) break;
        }

        return LootTable.values()[index];
    }
}
