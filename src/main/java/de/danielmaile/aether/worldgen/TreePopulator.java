package de.danielmaile.aether.worldgen;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TreePopulator extends BlockPopulator
{
    private final Clipboard tree;

    public TreePopulator()
    {
        tree = AetherWorld.loadPrefabToClipboard("tree");
    }

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
            AetherWorld.instantiatePrefab(location, tree);
        }
    }
}
