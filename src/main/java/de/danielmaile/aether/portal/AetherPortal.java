package de.danielmaile.aether.portal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;

public class AetherPortal
{
    /**
     * Checks for Portal in all different directions and lights it if one is found
     *
     * @param filled true: Checks for filled Portal, false: Checks for empty Portal and lights it if it found one
     * @return true if a portal was found
     */
    public static boolean checkPortal(Location location, boolean filled)
    {
        BlockFace[] directions = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
        for (BlockFace direction : directions)
        {
            Block block = location.getBlock();
            for (int i = 0; i < 3; i++)
            {
                block = block.getRelative(BlockFace.DOWN);

                if(filled && checkPortal(block, direction, true))
                {
                    return true;
                }
                else if(!filled && checkPortal(block, direction, false))
                {
                    lightPortal(block, direction);
                    return true;
                }
            }
        }
        return false;
    }

    private static void lightPortal(Block bottomBlock, BlockFace direction)
    {
        BlockFace[] directions = {BlockFace.UP, BlockFace.UP, BlockFace.UP,
                direction, BlockFace.DOWN, BlockFace.DOWN};

        Block block = bottomBlock;
        for (BlockFace blockFace : directions)
        {
            block = block.getRelative(blockFace);
            block.setType(Material.LIGHT_BLUE_STAINED_GLASS_PANE);

            MultipleFacing data = (MultipleFacing) Bukkit.createBlockData(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
            data.setFace(direction, true);
            data.setFace(direction.getOppositeFace(), true);
            block.setBlockData(data);
        }
    }

    /**
     * Checks for a glowstone portal at given location
     * @param bottomBlock bottom inside block of the portal
     * @param direction direction in with the portal is located
     * @param filled true if the portal should be filled with stained glass, false if it should be air
     * @return true if portal was found
     */
    private static boolean checkPortal(Block bottomBlock, BlockFace direction, boolean filled)
    {
        //Check bottom block
        if (bottomBlock.getType() != Material.GLOWSTONE) return false;

        //Check portal frame
        BlockFace[] blockDirections = {direction, direction,
                BlockFace.UP, BlockFace.UP, BlockFace.UP, BlockFace.UP,
                direction.getOppositeFace(), direction.getOppositeFace(), direction.getOppositeFace(),
                BlockFace.DOWN, BlockFace.DOWN, BlockFace.DOWN, BlockFace.DOWN};

        Block block = bottomBlock;
        for (BlockFace blockFace : blockDirections)
        {
            block = block.getRelative(blockFace);
            if (block.getType() != Material.GLOWSTONE) return false;
        }

        //Check for blocks inside portal
        Material toCheck = filled ? Material.LIGHT_BLUE_STAINED_GLASS_PANE : Material.AIR;
        BlockFace[] filledDirections = {BlockFace.UP, BlockFace.UP, BlockFace.UP,
                direction, BlockFace.DOWN, BlockFace.DOWN};

        block = bottomBlock;
        for (BlockFace blockFace : filledDirections)
        {
            block = block.getRelative(blockFace);
            if (block.getType() != toCheck)
                return false;
        }

        return true;
    }
}
