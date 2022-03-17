package de.danielmaile.aether.worldgen;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
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
        if(random.nextFloat() > 0.75)
        {
            int blockX = random.nextInt(15);
            int blockZ = random.nextInt(15);
            int blockY = world.getMaxHeight() - 1;
            while (chunk.getBlock(blockX, blockY, blockZ).getType() == Material.AIR && blockY > 0) blockY--;
            if(blockY == 0) return;

            Location location = new Location(world, blockX + chunk.getX() * 16, blockY, blockZ + chunk.getZ() * 16);
            AetherWorld.instantiatePrefab(location, tree);
        }
    }
}
