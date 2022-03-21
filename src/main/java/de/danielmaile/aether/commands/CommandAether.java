package de.danielmaile.aether.commands;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.item.CustomItemType;
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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        if (args.length == 1 && args[0].equalsIgnoreCase("teleport"))
        {
            teleport(player);
        }
        else if(args.length == 2 && args[0].equalsIgnoreCase("dungeon"))
        {
            float endPartChance = Float.parseFloat(args[1]);
            Random random = new Random();
            DungeonGenerator generator = new DungeonGenerator();
            generator.generateDungeon(player.getLocation().getBlock().getLocation(), random, endPartChance);
        }
        else if(args.length == 2 && args[0].equalsIgnoreCase("give"))
        {
            try
            {
                CustomItemType item = CustomItemType.valueOf(args[1].toUpperCase());
                player.getInventory().addItem(item.getItemStack());
            }
            catch (IllegalArgumentException exception)
            {
                player.sendMessage(Aether.PREFIX + " Das Item " + args[1] + " existiert nicht!");
            }
        }
        else
        {
            sendHelp(player);
        }

        return true;
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
        player.sendMessage(Aether.PREFIX + "/ae dungeon <end part chance> - Generiert einen Dungeon an deiner Position.");
        player.sendMessage(Aether.PREFIX + "/ae give <name> - Gibt dir ein Aether Item.");
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
            tabComplete.add("dungeon");
            tabComplete.add("give");
            StringUtil.copyPartialMatches(args[0], tabComplete, completions);
        }

        if(args.length == 2 && args[0].equalsIgnoreCase("give"))
        {
            //Get ItemNames from enum
            List<String> itemNames = Stream.of(CustomItemType.values()).map(Enum::name).toList();
            tabComplete.addAll(itemNames);
            StringUtil.copyPartialMatches(args[1], tabComplete, completions);
        }

        Collections.sort(completions);
        return completions;
    }
}
