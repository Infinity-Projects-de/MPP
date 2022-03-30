package de.danielmaile.aether.worldgen;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.worldgen.dungeon.DungeonGenerator;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ListenerWorldGeneration implements Listener
{
    private static final List<Prefab> treeList = Arrays.asList(Prefab.TREE1, Prefab.TREE2, Prefab.TREE3);

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event)
    {
        if (!event.isNewChunk()) return;
        if (!event.getWorld().equals(AetherWorld.getWorld())) return;
        Chunk chunk = event.getChunk();

        Random random = new Random();
        if (random.nextFloat() < Aether.getConfigManager().getTreeProbability())
        {
            generateTrees(chunk, random);
        }

        if (random.nextFloat() < Aether.getConfigManager().getDungeonProbability())
        {
            generateDungeons(chunk, random);
        }
    }

    private void generateTrees(Chunk chunk, Random random)
    {
        int x = random.nextInt(15) + chunk.getX() * 16;
        int z = random.nextInt(15) + chunk.getZ() * 16;
        int y = chunk.getWorld().getHighestBlockYAt(x, z);
        if (y <= 0) return;

        Location location = new Location(chunk.getWorld(), x, y, z);
        double yRotation = random.nextInt(4) * 90;
        treeList.get(random.nextInt(treeList.size())).instantiate(location, yRotation, true);
    }

    private void generateDungeons(Chunk chunk, Random random)
    {
        int x = chunk.getX() * 16;
        int z = chunk.getZ() * 16;
        //Generate dungeon at min y level

        Location location = new Location(chunk.getWorld(), x, chunk.getWorld().getMinHeight() + 1, z);
        DungeonGenerator generator = new DungeonGenerator();
        generator.generateDungeon(location, random,
                Aether.getConfigManager().getDungeonEndPartChance(), Aether.getConfigManager().getDungeonPartCap());
    }
}
