/**
 * Daniel Maile - 2022
 */
package de.danielmaile.aether.portal;

import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Orientable;

public class AetherPortal
{
    /**
     * Checks for Portal in all different directions and lights it if one is found
     * @param light if the portal should be lit when one was found
     * @return true if a portal was found
     */
    public static boolean checkPortal(Location lightLocation, boolean light)
    {
        BlockFace[] directions = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
        for (BlockFace direction : directions)
        {
            Block block = lightLocation.getBlock();
            for (int i = 0; i < 3; i++)
            {
                block = block.getRelative(BlockFace.DOWN);
                if (checkPortal(block, direction))
                {
                    if(light)
                    {
                        lightPortal(block, direction);
                    }
                    System.out.println("Portal at " + lightLocation);
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
            block.setType(Material.NETHER_PORTAL);

            if (direction == BlockFace.NORTH || direction == BlockFace.SOUTH)
            {
                Orientable data = (Orientable) Bukkit.createBlockData(Material.NETHER_PORTAL);
                data.setAxis(Axis.Z);
                block.setBlockData(data);
            }
        }
    }

    private static boolean checkPortal(Block bottomBlock, BlockFace direction)
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

        //Check for air inside portal
        BlockFace[] airDirections = {BlockFace.UP, BlockFace.UP, BlockFace.UP,
                direction, BlockFace.DOWN, BlockFace.DOWN};

        block = bottomBlock;
        for (BlockFace blockFace : airDirections)
        {
            block = block.getRelative(blockFace);
            if (block.getType() != Material.AIR && block.getType() != Material.NETHER_PORTAL) return false;
        }

        return true;
    }
}
