/**
 * Daniel Maile - 2022
 */
package de.danielmaile.aether.commands;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandAether implements CommandExecutor, TabExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(Aether.PREFIX + ChatColor.RED + "Aether-Befehle können nur von Spielern ausgeführt werden!");
            return true;
        }
        Player player = (Player) sender;

        if (args.length != 1)
        {
            sendHelp(player);
            return true;
        }

        switch (args[0])
        {
            case "teleport":
                teleport(player);
                return true;
            case "regenerate":
                regenerate(player);
                return true;
            case "reload":
                reload(player);
                return true;
            default:
                sendHelp(player);
                return true;
        }

    }

    private void teleport(@NotNull Player player)
    {
        if (!AetherWorld.isLoaded())
        {
            player.sendMessage(Aether.PREFIX + ChatColor.RED + "Es wurde keine Aether-Welt gefunden! Erstelle eine mit /aether regenerate!");
            return;
        }

        World aetherWorld = AetherWorld.getWorld();
        if (aetherWorld != null)
        {
            player.teleport(AetherWorld.getWorld().getSpawnLocation());
        }
    }

    private void reload(@NotNull Player player)
    {
        AetherWorld.regenerate();
        player.sendMessage(Aether.PREFIX + ChatColor.GREEN + "Die Config wurde neu geladen.");
    }

    private void regenerate(@NotNull Player player)
    {
        player.sendMessage(Aether.PREFIX + "Aether-Welt wird neu generiert...");
        AetherWorld.regenerate();
        player.sendMessage(Aether.PREFIX + "Aether-Welt wurde erfolgreich generiert.");
    }

    private void sendHelp(@NotNull Player player)
    {
        player.sendMessage(Aether.PREFIX + "/ae reload - Läd die Config neu.");
        player.sendMessage(Aether.PREFIX + "/ae teleport - Teleportiert dich in die Aether-Welt.");
        player.sendMessage(Aether.PREFIX + "/ae regenerate - Generiert die Aether-Welt neu.");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (!(sender instanceof Player)) {return null;}

        final List<String> tabComplete = new ArrayList<>();
        final List<String> completions = new ArrayList<>();

        if (args.length == 1)
        {
            tabComplete.add("regenerate");
            tabComplete.add("teleport");
            tabComplete.add("reload");
            StringUtil.copyPartialMatches(args[0], tabComplete, completions);
        }

        Collections.sort(completions);
        return completions;
    }
}
