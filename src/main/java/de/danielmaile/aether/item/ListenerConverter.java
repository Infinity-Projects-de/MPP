package de.danielmaile.aether.item;

import de.danielmaile.aether.Aether;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerConverter implements Listener
{
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event)
    {
        if (!Aether.getConfigManager().isItemConverterEnabled()) return;
        ItemStack[] content = event.getInventory().getContents();
        for (int i = 0; i < content.length; i++)
        {
            ItemStack itemStack = content[i];
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;

            ItemType type = ItemType.getFromTag(itemStack);
            if (type == null) continue;

            ItemStack typeStack = type.getItemStack(itemStack.getAmount());
            if (itemStack.equals(typeStack)) continue;

            event.getInventory().setItem(i, typeStack);
        }
    }
}
