package de.danielmaile.aether.item;

import de.danielmaile.aether.util.NBTEditor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerCrafting implements Listener
{
    @EventHandler
    //Check for crafting of vanilla items with aether items
    public void onCraftPrepare(PrepareItemCraftEvent event)
    {
        ItemStack result = event.getInventory().getResult();
        if (result == null) return;

        if (NBTEditor.hasKey(result, CustomItemType.AETHER_ITEM_TAG_KEY)) return;

        ItemStack[] ingredients = event.getInventory().getContents();

        for (ItemStack ingredient : ingredients)
        {
            if (ingredient == null || ingredient.getType() == Material.AIR) continue;

            if (NBTEditor.hasKey(ingredient, CustomItemType.AETHER_ITEM_TAG_KEY))
            {
                event.getInventory().setResult(null);
                return;
            }
        }
    }
}
