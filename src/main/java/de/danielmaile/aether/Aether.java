/**
 * Daniel Maile - 2022
 */
package de.danielmaile.aether;

import de.danielmaile.aether.commands.CommandAether;
import de.danielmaile.aether.listeners.ListenerAetherVoid;
import de.danielmaile.aether.portal.ListenerPortal;
import de.danielmaile.aether.worldgen.AetherWorld;
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

    public static Aether getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;
        logger = Logger.getLogger("Minecraft");
        saveDefaultConfig();


        //Register listener and commands
        getServer().getPluginManager().registerEvents(new ListenerAetherVoid(), this);
        getServer().getPluginManager().registerEvents(new ListenerPortal(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("aether")).setExecutor(new CommandAether());

        //Load generation values from config and generate world
        AetherWorld.loadConfig();
        AetherWorld.generate();
    }

    public static void logError(String message)
    {
        logger.severe(message);
    }
}
