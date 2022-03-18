package de.danielmaile.aether.portal;

import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class AetherPortal
{
    /**
     * Checks for Portal in all different directions and lights it if one is found
     *
     * @param location location to check (location of the block above the portal bottom
     * @param filled   true: Checks for filled Portal, false: Checks for empty Portal and lights it if it found one
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

                if (filled && checkPortal(block, direction, true))
                {
                    return true;
                }
                else if (!filled && checkPortal(block, direction, false))
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
            block.setType(Material.END_GATEWAY);
        }
    }

    /**
     * Checks for a glowstone portal at given location
     *
     * @param bottomBlock bottom inside block of the portal
     * @param direction   direction in with the portal is located
     * @param filled      true if the portal should be filled with stained glass, false if it should be air
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
        Material toCheck = filled ? Material.END_GATEWAY : Material.AIR;
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

    public static void createPortal(Location location)
    {
        AetherWorld.instantiatePrefab(location, "portal");
    }

    public static Location findPortalInRadius(Location location, int radius)
    {
        for (int y = location.getWorld().getMaxHeight() - 1; y >= location.getWorld().getMinHeight() + 1; y--)
        {
            for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++)
            {

                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++)
                {
                    Location portalLocation = new Location(location.getWorld(), x, y, z);
                    if (location.getWorld().getBlockAt(portalLocation).getType() == Material.GLOWSTONE)
                    {
                        if (checkPortal(portalLocation.clone().add(0, -1, 0), true))
                        {
                            return portalLocation;
                        }
                    }
                }
            }
        }
        return null;
    }
}
