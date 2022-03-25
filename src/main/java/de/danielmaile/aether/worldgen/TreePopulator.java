package de.danielmaile.aether.worldgen;

import de.danielmaile.aether.Aether;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TreePopulator extends BlockPopulator
{
    private final List<Prefab> treeList = Arrays.asList(Prefab.TREE1, Prefab.TREE2, Prefab.TREE3);

    @Override
    public void populate(@NotNull World world, @NotNull Random random, @NotNull Chunk chunk)
    {
        if (random.nextFloat() < Aether.getConfigManager().getTreeProbability())
        {
            int x = random.nextInt(15) + chunk.getX() * 16;
            int z = random.nextInt(15) + chunk.getZ() * 16;
            int y = world.getHighestBlockYAt(x, z);
            if (y == -1) return;

            Location location = new Location(world, x, y, z);
            double yRotation = random.nextInt(4) * 90;
            treeList.get(random.nextInt(treeList.size())).instantiate(location, yRotation, true);
        }
    }
}
