package de.danielmaile.aether;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.danielmaile.aether.commands.CommandAether;
import de.danielmaile.aether.item.CustomRecipe;
import de.danielmaile.aether.item.ListenerBlock;
import de.danielmaile.aether.item.ListenerCrafting;
import de.danielmaile.aether.listeners.ListenerAetherVoid;
import de.danielmaile.aether.listeners.ListenerMonument;
import de.danielmaile.aether.mobs.ListenerAetherMobs;
import de.danielmaile.aether.mobs.RideableLlama;
import de.danielmaile.aether.portal.ListenerPortal;
import de.danielmaile.aether.worldgen.AetherWorld;
import de.danielmaile.aether.worldgen.Prefab;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class Aether extends JavaPlugin
{
    public static final String PREFIX = ChatColor.LIGHT_PURPLE + "[Aether] " + ChatColor.GRAY;
    private static Logger logger;
    private static Aether instance;

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
        getServer().getPluginManager().registerEvents(new ListenerMonument(), this);
        getServer().getPluginManager().registerEvents(new ListenerAetherMobs(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("aether")).setExecutor(new CommandAether());

        //Create plugin data folder
        if (!getDataFolder().exists())
        {
            boolean fileCreated = getDataFolder().mkdirs();
            if (!fileCreated)
            {
                logError("Failed to create plugin data folder");
            }
        }

        registerRecipes();

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        new RideableLlama().init(protocolManager);

        AetherWorld.init();
        Prefab.init();
    }

    private void registerRecipes()
    {
        for (CustomRecipe customRecipe : CustomRecipe.values())
        {
            Bukkit.addRecipe(customRecipe.getRecipe());
        }
    }
}
