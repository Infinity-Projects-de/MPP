/**
 * Daniel Maile - 2022
 */

//TODO rewrite worldgen
package de.danielmaile.aether.worldgen;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static de.danielmaile.aether.worldgen.AetherWorld.*;

public class AetherGenerator extends ChunkGenerator
{

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData)
    {
        SimplexOctaveGenerator simplexOctaveGenerator = new SimplexOctaveGenerator(new Random(worldInfo.getSeed()), octaves);
        simplexOctaveGenerator.setScale(noise_scale);


        for (int blockX = 0; blockX < 16; blockX++)
        {
            for (int blockZ = 0; blockZ < 16; blockZ++)
            {

                double noise = simplexOctaveGenerator.noise(chunkX * 16 + blockX, chunkZ * 16 + blockZ, noise_frequency, noise_amplitude);

                if (noise > noise_terrain_threshold)
                {
                    int currentHeight = (int) (noise * noise_terrain_height_scale + min_height);

                    for (int y = currentHeight; y > min_height - (noise * noise_terrain_height_scale - terrain_bottom_pos); y--)
                    {

                        if (y == currentHeight)
                        {
                            chunkData.setBlock(blockX, y, blockZ, Material.GRASS_BLOCK);
                        }
                        else
                        {
                            chunkData.setBlock(blockX, y, blockZ, new Random().nextBoolean() ? Material.COBBLESTONE : Material.STONE);
                        }
                    }
                }
            }
        }
    }
}

