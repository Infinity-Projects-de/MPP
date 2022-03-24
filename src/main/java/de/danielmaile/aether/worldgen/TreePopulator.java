package de.danielmaile.aether.worldgen;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TreePopulator extends BlockPopulator
{
    @Override
    public void populate(@NotNull World world, @NotNull Random random, @NotNull Chunk chunk)
    {
        if (random.nextFloat() > 0.75)
        {
            int x = random.nextInt(15) + chunk.getX() * 16;
            int z = random.nextInt(15) + chunk.getZ() * 16;
            int y = world.getHighestBlockYAt(x, z);
            if (y == -1) return;

            Location location = new Location(world, x, y, z);
            Prefab.TREE.instantiate(location, true);
        }
    }
}
