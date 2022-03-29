package de.danielmaile.aether.worldgen;

import org.bukkit.Material;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BedrockPopulator extends BlockPopulator
{
    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion)
    {
        for (int x = chunkX * 16; x < (chunkX + 1) * 16; x++)
        {
            for (int z = chunkZ * 16; z < (chunkZ + 1) * 16; z++)
            {
                limitedRegion.setType(x, -256, z, Material.BEDROCK);
            }
        }
    }
}
