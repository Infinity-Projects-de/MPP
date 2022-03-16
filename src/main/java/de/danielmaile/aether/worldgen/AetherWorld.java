/**
 * Daniel Maile - 2022
 */
package de.danielmaile.aether.worldgen;

import de.danielmaile.aether.Aether;
import org.bukkit.*;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;

public class AetherWorld
{
    public static final NamespacedKey WORLD_KEY = new NamespacedKey(Aether.getInstance(), "world_aether");

    public static int min_height;
    public static int octaves;
    public static double noise_scale;
    public static double noise_frequency;
    public static double noise_amplitude;
    public static double noise_terrain_height_scale;
    public static double noise_terrain_threshold;
    public static double terrain_bottom_pos;

    public static void loadConfig()
    {
        min_height = Aether.getInstance().getConfig().getInt("min_height");
        octaves = Aether.getInstance().getConfig().getInt("octaves");
        noise_scale = Aether.getInstance().getConfig().getDouble("noise_scale");
        noise_frequency = Aether.getInstance().getConfig().getDouble("noise_frequency");
        noise_amplitude = Aether.getInstance().getConfig().getDouble("noise_amplitude");
        noise_terrain_height_scale = Aether.getInstance().getConfig().getDouble("noise_terrain_height_scale");
        noise_terrain_threshold = Aether.getInstance().getConfig().getDouble("noise_terrain_threashold");
        terrain_bottom_pos = Aether.getInstance().getConfig().getDouble("terrain_bottom_pos");
    }

    public static @Nullable World getWorld()
    {
        return Bukkit.getWorld(WORLD_KEY);
    }

    public static boolean isLoaded()
    {
        return Bukkit.getWorld(WORLD_KEY) != null;
    }

    public static void generate()
    {
        new WorldCreator(WORLD_KEY)
                .generator(new AetherGenerator())
                .type(WorldType.NORMAL)
                .generateStructures(false)
                .environment(World.Environment.NORMAL).createWorld();
    }

    public static void regenerate()
    {
        delete();
        generate();
    }

    public static void delete()
    {
        World world = Bukkit.getWorld(WORLD_KEY);
        if (world == null) return;
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (player.getWorld().equals(world))
            {
                player.sendMessage(Aether.PREFIX + ChatColor.RED + "Die Aether-Welt wird neu generiert!");
                World defaultWorld = Bukkit.getWorld("world");
                if (defaultWorld != null)
                {
                    player.teleport(defaultWorld.getSpawnLocation());
                }
            }
        }
        Bukkit.getServer().unloadWorld(world, false);

        File worldFolder = world.getWorldFolder();
        if (!worldFolder.exists()) return;
        deleteWorldFolder(worldFolder);
    }

    private static void deleteWorldFolder(File path)
    {
        if (!path.exists()) return;
        File[] files = path.listFiles();
        if (files == null) return;

        for (File file : files)
        {
            if (file.isDirectory())
            {
                deleteWorldFolder(file);
            }
            else
            {
                if(!file.delete())
                {
                    Aether.logError("Der Aether-Welt Ordner konnte nicht gelöscht werden!");
                }
            }
        }
    }
}
