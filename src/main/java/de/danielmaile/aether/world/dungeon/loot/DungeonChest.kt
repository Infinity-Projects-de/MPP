package de.danielmaile.aether.world.dungeon.loot

import de.danielmaile.aether.inst
import de.danielmaile.aether.world.dungeon.loot.LootTable.Companion.getTotalWeight
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.Chest
import org.bukkit.block.data.Directional
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.lang.Integer.min
import java.util.Random

class DungeonChest(private val random: Random) {

    fun instantiate(location: Location, facing: BlockFace) {
        val world = location.world ?: return
        val block = world.getBlockAt(location)
        block.type = Material.CHEST

        //Set facing
        val data = block.blockData as Directional
        data.facing = facing
        block.blockData = data

        val chest = block.state as Chest
        Bukkit.getScheduler().runTaskAsynchronously(
            inst(),
            Runnable { addItems(random, chest.blockInventory, generateItemStacks()) })
    }

    //Add items in random positions
    private fun addItems(random: Random, inventory: Inventory, stacks: List<ItemStack>) {
        for (itemStack in stacks) {
            //Try at most 3 times to set item randomly. Else add it normally
            var tries = 0
            while (tries < 3) {
                val position = random.nextInt(0, inventory.size)
                if (inventory.getItem(position) == null) {
                    inventory.setItem(position, itemStack)
                    tries = -1
                    break
                } else {
                    tries++
                }
            }

            if (tries != -1) {
                inventory.addItem(itemStack)
            }
        }
    }

    private fun generateItemStacks(): ArrayList<ItemStack> {
        val stacks = ArrayList<ItemStack>()
        val table = getRandomLootTable()
        val stackAmount = random.nextInt(table.minItemStacks, table.maxItemStacks + 1)

        for (i in 0 until stackAmount) {
            val loot = table.getRandomLoot(random)
            var amountLeft = random.nextInt(loot.minAmount, loot.maxAmount + 1)
            if (amountLeft < 1) continue

            while(amountLeft > 0) {
                val addedAmount: Int
                if (loot.isCustomItem()) {
                    addedAmount = min(amountLeft, loot.itemType!!.material.maxStackSize)
                    stacks.add(loot.itemType.getItemStack(addedAmount))
                } else {
                    addedAmount = min(amountLeft, loot.material!!.maxStackSize)
                    stacks.add(ItemStack(loot.material, addedAmount))
                }
                amountLeft -= addedAmount
            }
        }
        return stacks
    }

    private fun getRandomLootTable(): LootTable {
        val totalWeight = getTotalWeight()
        var index = 0
        var randomWeight = random.nextDouble() * totalWeight

        while (index < LootTable.values().size - 1) {
            randomWeight -= LootTable.values()[index].weight
            if (randomWeight <= 0.0) break
            index++
        }
        return LootTable.values()[index]
    }
}