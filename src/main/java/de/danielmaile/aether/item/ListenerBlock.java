package de.danielmaile.aether.item;

import de.danielmaile.aether.worldgen.AetherWorld;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerBlock implements Listener
{
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        if(event.isCancelled()) return;

        Block block = event.getBlock();
        if(!block.getWorld().equals(AetherWorld.getWorld())) return;

        Location location = block.getLocation();
        CustomItemType customItemType = CustomItemType.getFromBlockMaterial(block.getType());
        if(customItemType == null) return;

        block.getWorld().dropItemNaturally(location, customItemType.getItemStack(1));
        event.setDropItems(false);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if(event.isCancelled()) return;

        Block block = event.getBlock();
        ItemStack itemStack = event.getItemInHand();
        NBTItem nbtItem = new NBTItem(itemStack);
        if(block.getWorld().equals(AetherWorld.getWorld()))
        {
            if(nbtItem.hasKey(CustomItemType.AETHER_ITEM_TAG_KEY))
            {
                //Convert Item to correct material
                CustomItemType customItemType = CustomItemType.valueOf(nbtItem.getString(CustomItemType.AETHER_ITEM_TAG_KEY));
                event.getBlockPlaced().setType(customItemType.getBlockMaterial());
            }
            else
            {
                //Check if block which a player tries to place is already used by aether block
                CustomItemType customItemType = CustomItemType.getFromBlockMaterial(itemStack.getType());
                if(customItemType != null)
                {
                    event.setCancelled(true);
                }
            }
        }
        else
        {
            //Aether items can't be placed in other worlds
            if(nbtItem.hasKey(CustomItemType.AETHER_ITEM_TAG_KEY))
            {
                event.setCancelled(true);
            }
        }
    }
}
