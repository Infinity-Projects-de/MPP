package de.danielmaile.aether.item;

import de.danielmaile.aether.util.NBTEditor;
import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
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
        BlockType blockType = BlockType.getFromMaterial(block.getType());
        if (blockType == null) return;

        block.getWorld().dropItemNaturally(location, blockType.getItemDrop().getItemStack());
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
            if (NBTEditor.hasKey(itemStack, ItemType.AETHER_ITEM_TAG_KEY))
            {
                //Convert Item to correct material
                ItemType itemType = ItemType.fromTag(itemStack);
                if (itemType != null)
                {
                    //Change material but keep orientation
                    BlockData data = block.getBlockData();
                    block.setType(itemType.getPlaceMaterial());
                    block.setBlockData(data);
                }
            }
            else
            {
                //Check if block which a player tries to place is already used by aether block
                BlockType blockType = BlockType.getFromMaterial(itemStack.getType());
                if (blockType != null)
                {
                    event.setCancelled(true);
                }
            }
        }
        else
        {
            //Aether items can't be placed in other worlds
            if (NBTEditor.hasKey(itemStack, ItemType.AETHER_ITEM_TAG_KEY))
            {
                event.setCancelled(true);
            }
        }
    }
}
