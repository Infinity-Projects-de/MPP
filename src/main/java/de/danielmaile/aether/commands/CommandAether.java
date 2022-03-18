package de.danielmaile.aether.commands;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.worldgen.AetherWorld;
import de.danielmaile.aether.worldgen.dungeon.DungeonGenerator;
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
import java.util.Random;

public class CommandAether implements CommandExecutor, TabExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(Aether.PREFIX + ChatColor.RED + "Aether-Befehle können nur von Spielern ausgeführt werden!");
            return true;
        }

        if (args.length != 1)
        {
            sendHelp(player);
            return true;
        }

        switch (args[0])
        {
            case "teleport" -> {
                teleport(player);
                return true;
            }
            case "prefab" -> {
                AetherWorld.instantiatePrefab(player.getLocation(), "tree");
                return true;
            }
            case "dungeon" -> {
                DungeonGenerator dungeonGenerator = new DungeonGenerator();
                Random random = new Random();
                dungeonGenerator.generateDungeon(random);
                return true;
            }
            default -> {
                sendHelp(player);
                return true;
            }
        }

    }

    private void teleport(@NotNull Player player)
    {
        World aetherWorld = AetherWorld.getWorld();
        if (aetherWorld != null)
        {
            player.teleport(AetherWorld.getWorld().getSpawnLocation());
        }
        else
        {
            player.sendMessage(Aether.PREFIX + ChatColor.RED + "Die Aether-Welt wurde noch nicht erstellt. Starte den Server neu um sie zu erstellen!");
        }
    }

    private void sendHelp(@NotNull Player player)
    {
        player.sendMessage(Aether.PREFIX + "/ae teleport - Teleportiert dich in die Aether-Welt.");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (!(sender instanceof Player)) { return null; }

        final List<String> tabComplete = new ArrayList<>();
        final List<String> completions = new ArrayList<>();

        if (args.length == 1)
        {
            tabComplete.add("teleport");
            tabComplete.add("prefab");
            StringUtil.copyPartialMatches(args[0], tabComplete, completions);
        }

        Collections.sort(completions);
        return completions;
    }
}
