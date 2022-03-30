package de.danielmaile.aether.config;

import de.danielmaile.aether.Aether;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager
{
    public LanguageManager getLanguageManager()
    {
        return languageManager;
    }

    public float getDungeonEndPartChance()
    {
        return dungeonEndPartChance;
    }

    public int getDungeonPartCap()
    {
        return dungeonPartCap;
    }

    public float getDungeonProbability()
    {
        return dungeonProbability;
    }

    public float getTreeProbability()
    {
        return treeProbability;
    }

    public float getLlamaJumpHeight()
    {
        return llamaJumpHeight;
    }

    public float getLlamaSpeed()
    {
        return llamaSpeed;
    }

    private LanguageManager languageManager;

    public boolean isItemConverterEnabled()
    {
        return itemConverterEnabled;
    }

    private boolean itemConverterEnabled;
    private float dungeonEndPartChance;
    private int dungeonPartCap;
    private float dungeonProbability;
    private float treeProbability;
    private float llamaJumpHeight;
    private float llamaSpeed;

    public ConfigManager()
    {
        load();
    }

    public void load()
    {
        File file = new File(Aether.getInstance().getDataFolder().getAbsolutePath() + File.separator
                + "locales" + File.separator + Aether.getInstance().getConfig().getString("language_file") + ".yml");
        if (!file.exists())
        {
            Aether.logError("Language file " + Aether.getInstance().getConfig().getString("language_file") + ".yml was not found!");
            file = new File(Aether.getInstance().getDataFolder().getAbsolutePath() + File.separator
                    + "locales" + File.separator + "de.yml");
        }

        languageManager = new LanguageManager(YamlConfiguration.loadConfiguration(file));
        itemConverterEnabled = Aether.getInstance().getConfig().getBoolean("item_converter");
        dungeonEndPartChance = (float) Aether.getInstance().getConfig().getDouble("world_generation.dungeons.end_part_chance");
        dungeonProbability = (float) Aether.getInstance().getConfig().getDouble("world_generation.dungeons.probability");
        dungeonPartCap = Aether.getInstance().getConfig().getInt("world_generation.dungeons.part_cap");
        treeProbability = (float) Aether.getInstance().getConfig().getDouble("world_generation.trees.probability");
        llamaJumpHeight = (float) Aether.getInstance().getConfig().getDouble("entities.llama.jump_height");
        llamaSpeed = (float) Aether.getInstance().getConfig().getDouble("entities.llama.speed");
    }
}
