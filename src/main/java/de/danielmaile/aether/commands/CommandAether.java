package de.danielmaile.aether.commands;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.config.LanguageManager;
import de.danielmaile.aether.item.ItemType;
import de.danielmaile.aether.worldgen.AetherWorld;
import de.danielmaile.aether.worldgen.dungeon.Dungeon;
import de.danielmaile.aether.worldgen.dungeon.loot.DungeonChest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Location;
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
import java.util.stream.Stream;

public class CommandAether implements CommandExecutor, TabExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        LanguageManager languageManager = Aether.getInstance().getLanguageManager();
        Component cmdPrefix = languageManager.getComponent("messages.prefix");

        if (!(sender instanceof Player player))
        {
            sender.sendMessage(cmdPrefix.append(languageManager.getComponent("messages.cmd.errors.only_player_cmd")));
            return true;
        }

        if (args.length == 1)
        {
            switch (args[0].toLowerCase())
            {
                case "chest" -> new DungeonChest(new Random()).instantiate(player.getLocation());
                case "locate" -> {
                    List<Dungeon> dungeons = AetherWorld.getObjectManager().getDungeons();
                    double smallestDistance = Double.MAX_VALUE;
                    Dungeon nearestDungeon = null;
                    for (Dungeon dungeon : dungeons)
                    {
                        double distance = player.getLocation().distance(dungeon.getMonumentLocation());
                        if (distance < smallestDistance)
                        {
                            smallestDistance = distance;
                            nearestDungeon = dungeon;
                        }
                    }

                    if (player.getWorld().equals(AetherWorld.getWorld()) && nearestDungeon != null)
                    {
                        Location targetLocation = nearestDungeon.getMonumentLocation().clone().add(0, 5, 0);
                        String locationString = targetLocation.getBlockX() + " " + targetLocation.getBlockY() + " " + targetLocation.getBlockZ();
                        TagResolver tagResolver = TagResolver.resolver(Placeholder.parsed("location", locationString),
                                Placeholder.parsed("distance", Integer.toString((int) smallestDistance)));
                        Component message = cmdPrefix.append(languageManager.getComponent("messages.cmd.info.next_dungeon", tagResolver));
                        player.sendMessage(message);
                    }
                    else
                    {
                        player.sendMessage(cmdPrefix.append(languageManager.getComponent("messages.cmd.errors.no_dungeon_found")));
                    }
                }
                case "reload" -> {
                    Aether.getInstance().getConfigManager().load();
                    player.sendMessage(cmdPrefix.append(languageManager.getComponent("messages.cmd.info.reloaded_config")));
                }
                case "teleport" -> {
                    World aetherWorld = AetherWorld.getWorld();
                    if (aetherWorld != null)
                    {
                        player.teleport(AetherWorld.getWorld().getSpawnLocation());
                    }
                    else
                    {
                        player.sendMessage(cmdPrefix.append(languageManager.getComponent("messages.cmd.errors.aether_world_not_created")));
                    }
                }
                default -> sendHelp(cmdPrefix, player);
            }

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
                        .append(languageManager.getComponent("messages.cmd.errors.item_does_not_exist")));
            }
        }
        else
        {
            sendHelp(cmdPrefix, player);
        }
        return true;
    }

    private void sendHelp(Component cmdPrefix, Player player)
    {
        for (Component component : Aether.getInstance().getLanguageManager().getComponentList("messages.cmd.info.help_text"))
        {
            player.sendMessage(cmdPrefix.append(component));
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (!(sender instanceof Player)) { return null; }

        final List<String> tabComplete = new ArrayList<>();
        final List<String> completions = new ArrayList<>();

        if (args.length == 1)
        {
            tabComplete.add("chest");
            tabComplete.add("give");
            tabComplete.add("locate");
            tabComplete.add("reload");
            tabComplete.add("teleport");
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
