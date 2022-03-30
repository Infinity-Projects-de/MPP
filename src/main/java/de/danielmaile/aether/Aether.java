package de.danielmaile.aether;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.danielmaile.aether.commands.CommandAether;
import de.danielmaile.aether.config.ConfigManager;
import de.danielmaile.aether.config.LanguageManager;
import de.danielmaile.aether.item.ListenerBlock;
import de.danielmaile.aether.item.ListenerConverter;
import de.danielmaile.aether.item.ListenerCrafting;
import de.danielmaile.aether.item.Recipes;
import de.danielmaile.aether.item.funtion.ListenerArmor;
import de.danielmaile.aether.item.funtion.ListenerItem;
import de.danielmaile.aether.item.funtion.magicwand.ListenerMagicWand;
import de.danielmaile.aether.item.funtion.particle.ListenerParticle;
import de.danielmaile.aether.item.funtion.particle.ParticleManager;
import de.danielmaile.aether.listeners.ListenerAetherVoid;
import de.danielmaile.aether.listeners.ListenerDungeon;
import de.danielmaile.aether.listeners.ListenerWorldSave;
import de.danielmaile.aether.mobs.ListenerAetherMobs;
import de.danielmaile.aether.mobs.RideableLlama;
import de.danielmaile.aether.portal.ListenerPortal;
import de.danielmaile.aether.worldgen.AetherWorld;
import de.danielmaile.aether.worldgen.ListenerWorldGeneration;
import de.danielmaile.aether.worldgen.Prefab;
import de.danielmaile.aether.worldgen.PrefabType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class Aether extends JavaPlugin
{
    private static Logger logger;
    private static Aether instance;

    public static ParticleManager getParticleManager()
    {
        return particleManager;
    }

    private static ParticleManager particleManager;

    public static ConfigManager getConfigManager()
    {
        return configManager;
    }

    public static LanguageManager getLanguageManager()
    {
        return configManager.getLanguageManager();
    }

    private static ConfigManager configManager;

    public static void logError(String message)
    {
        logger.severe(message);
    }

    public static void logInfo(String message)
    {
        logger.info(message);
    }

    public static Aether getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;
        logger = getLogger();

        //Register listener and commands
        getServer().getPluginManager().registerEvents(new ListenerAetherVoid(), this);
        getServer().getPluginManager().registerEvents(new ListenerPortal(), this);
        getServer().getPluginManager().registerEvents(new ListenerBlock(), this);
        getServer().getPluginManager().registerEvents(new ListenerCrafting(), this);
        getServer().getPluginManager().registerEvents(new ListenerDungeon(), this);
        getServer().getPluginManager().registerEvents(new ListenerAetherMobs(), this);
        getServer().getPluginManager().registerEvents(new ListenerItem(), this);
        getServer().getPluginManager().registerEvents(new ListenerArmor(), this);
        getServer().getPluginManager().registerEvents(new ListenerParticle(), this);
        getServer().getPluginManager().registerEvents(new ListenerConverter(), this);
        getServer().getPluginManager().registerEvents(new ListenerMagicWand(), this);
        getServer().getPluginManager().registerEvents(new ListenerWorldGeneration(), this);
        getServer().getPluginManager().registerEvents(new ListenerWorldSave(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("aether")).setExecutor(new CommandAether());

        //Create plugin, data and locales folder
        File dataFolder = new File(getDataFolder().getAbsolutePath() + File.separator + "data");
        if (!dataFolder.exists() && !dataFolder.mkdirs())
        {
            Aether.logError("Creation of data folder (" + dataFolder.getAbsolutePath() + ") failed!");
        }

        File localesFolder = new File(getDataFolder().getAbsolutePath() + File.separator + "locales");
        if (!localesFolder.exists() && !localesFolder.mkdirs())
        {
            Aether.logError("Creation of locales folder (" + dataFolder.getAbsolutePath() + ") failed!");
        }

        saveDefaultConfig();
        File deFile = new File(getDataFolder().getAbsolutePath() + File.separator + "locales" + File.separator + "de.yml");
        if (!deFile.exists())
        {
            saveResource("locales/de.yml", false);
        }
        configManager = new ConfigManager();

        registerRecipes();

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        new RideableLlama().init(protocolManager);

        AetherWorld.init();
        PrefabType.loadPrefabs();

        particleManager = new ParticleManager();
    }

    @Override
    public void onDisable()
    {
        AetherWorld.getObjectManager().save();
    }

    private void registerRecipes()
    {
        for (Recipes recipe : Recipes.values())
        {
            Bukkit.addRecipe(recipe.getRecipe());
        }
    }
}
