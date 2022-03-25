package de.danielmaile.aether.item;

import de.danielmaile.aether.util.NBTEditor;
import de.danielmaile.aether.worldgen.AetherWorld;
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
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (!block.getWorld().equals(AetherWorld.getWorld())) return;

        Location location = block.getLocation();
        CustomBlockType customBlockType = CustomBlockType.getFromMaterial(block.getType());
        if (customBlockType == null) return;

        block.getWorld().dropItemNaturally(location, customBlockType.getItemDrop().getItemStack());
        event.setDropItems(false);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        ItemStack itemStack = event.getItemInHand();
        if (block.getWorld().equals(AetherWorld.getWorld()))
        {
            if (NBTEditor.hasKey(itemStack, CustomItemType.AETHER_ITEM_TAG_KEY))
            {
                //Convert Item to correct material
                CustomItemType customItemType = CustomItemType.getFromTag(itemStack);
                if(customItemType != null)
                {
                    event.getBlockPlaced().setType(customItemType.getPlaceMaterial());
                }
            }
            else
            {
                //Check if block which a player tries to place is already used by aether block
                CustomBlockType customBlockType = CustomBlockType.getFromMaterial(itemStack.getType());
                if (customBlockType != null)
                {
                    event.setCancelled(true);
                }
            }
        }
        else
        {
            //Aether items can't be placed in other worlds
            if (NBTEditor.hasKey(itemStack, CustomItemType.AETHER_ITEM_TAG_KEY))
            {
                event.setCancelled(true);
            }
        }
    }
}
