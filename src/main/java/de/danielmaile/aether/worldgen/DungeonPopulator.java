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
        if (random.nextFloat() < 0.005)
        {
            int x = chunk.getX() * 16;
            int z = chunk.getZ() * 16;
            //Generate dungeon 20 blocks under highest block but not lower than y=150
            int y = world.getHighestBlockYAt(x, z) - 20;
            if (y < 150) return;

            Location location = new Location(world, x, y, z);
            DungeonGenerator generator = new DungeonGenerator();
            generator.generateDungeon(location, random, 0.35f);
        }
    }
}
