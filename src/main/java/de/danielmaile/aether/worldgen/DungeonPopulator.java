package de.danielmaile.aether.worldgen;

import de.danielmaile.aether.worldgen.dungeon.DungeonGenerator;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DungeonPopulator extends BlockPopulator
{
    @Override
    public void populate(@NotNull World world, @NotNull Random random, @NotNull Chunk chunk)
    {
        if (random.nextFloat() < 0.0004)
        {
            int x = chunk.getX() * 16;
            int z = chunk.getZ() * 16;
            int y = world.getHighestBlockYAt(x, z);
            if (y < world.getMinHeight()) return;

            Location location = new Location(world, x, y, z);
            DungeonGenerator generator = new DungeonGenerator();
            generator.generateDungeon(location, random, 0.35f);

        }
    }
}
