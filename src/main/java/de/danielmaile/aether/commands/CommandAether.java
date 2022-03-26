package de.danielmaile.aether.commands;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.item.ItemType;
import de.danielmaile.aether.worldgen.AetherWorld;
import net.kyori.adventure.text.Component;
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
import java.util.stream.Stream;

public class CommandAether implements CommandExecutor, TabExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Component cmdPrefix = Aether.getLanguageManager().getComponent("messages.prefix");

        if (!(sender instanceof Player player))
        {
            sender.sendMessage(cmdPrefix.append(Aether.getLanguageManager().getComponent("messages.cmd.errors.only_player_cmd")));
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("teleport"))
        {
            World aetherWorld = AetherWorld.getWorld();
            if (aetherWorld != null)
            {
                player.teleport(AetherWorld.getWorld().getSpawnLocation());
            }
            else
            {
                player.sendMessage(cmdPrefix.append(Aether.getLanguageManager().getComponent("messages.cmd.errors.aether_world_not_created")));
            }
        }
        else if (args.length == 1 && args[0].equalsIgnoreCase("reload"))
        {
            Aether.getConfigManager().load();
            player.sendMessage(cmdPrefix.append(Aether.getLanguageManager().getComponent("messages.cmd.info.reloaded_config")));
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("give"))
        {
            try
            {
                ItemType item = ItemType.valueOf(args[1].toUpperCase());
                player.getInventory().addItem(item.getItemStack());
            }
            catch (IllegalArgumentException exception)
            {
                player.sendMessage(cmdPrefix
                        .append(Aether.getLanguageManager().getComponent("messages.cmd.error.item_does_not_exist")));
            }
        }
        else
        {
            for (Component component : Aether.getLanguageManager().getComponentList("messages.cmd.info.help_text"))
            {
                player.sendMessage(cmdPrefix.append(component));
            }
        }
        return true;
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

        if (args.length == 2 && args[0].equalsIgnoreCase("give"))
        {
            //Get ItemNames from enum
            List<String> itemNames = Stream.of(ItemType.values()).map(Enum::name).toList();
            tabComplete.addAll(itemNames);
            StringUtil.copyPartialMatches(args[1], tabComplete, completions);
        }

        Collections.sort(completions);
        return completions;
    }
}
