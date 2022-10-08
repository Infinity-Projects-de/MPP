package de.danielmaile.mpp.command

import de.danielmaile.mpp.aetherWorld
import de.danielmaile.mpp.config.LanguageManager
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ItemType
import de.danielmaile.mpp.util.getDirection
import de.danielmaile.mpp.aether.world.dungeon.Dungeon
import de.danielmaile.mpp.aether.world.dungeon.loot.DungeonChest
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.util.*
import java.util.stream.Stream
import kotlin.collections.ArrayList

class CommandMPP : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val languageManager = inst().getLanguageManager()
        val cmdPrefix = languageManager.getComponent("messages.prefix")

        if (sender !is Player) {
            sender.sendMessage(cmdPrefix.append(languageManager.getComponent("messages.cmd.errors.only_player_cmd")))
            return true
        }

        when (args.size) {
            1 -> {
                when (args[0].lowercase()) {
                    "chest" -> {
                        val facing = sender.getDirection()
                        DungeonChest(Random()).instantiate(
                            sender.location.block.getRelative(facing).location,
                            sender.getDirection().oppositeFace
                        )
                    }

                    "locate" -> {
                        locateCMD(sender, languageManager, cmdPrefix)
                    }

                    "reload" -> {
                        inst().reloadConfig()
                        sender.sendMessage(cmdPrefix.append(languageManager.getComponent("messages.cmd.info.reloaded_config")))
                    }

                    "teleport" -> {
                        teleportCMD(sender)
                    }

                    else -> {
                        sendHelp(sender, languageManager, cmdPrefix)
                    }
                }
            }

            2 -> {
                when (args[0].lowercase()) {
                    "give" -> {
                        giveCMD(sender, args, languageManager, cmdPrefix)
                    }

                    else -> {
                        sendHelp(sender, languageManager, cmdPrefix)
                    }
                }
            }

            else -> sendHelp(sender, languageManager, cmdPrefix)
        }
        return true
    }

    private fun locateCMD(player: Player, languageManager: LanguageManager, cmdPrefix: Component) {
        val dungeons: List<Dungeon> = inst().worldManager.objectManager.dungeons
        var smallestDistance = Double.MAX_VALUE
        var nearestDungeon: Dungeon? = null
        for (dungeon in dungeons) {
            val distance: Double = player.location.distance(dungeon.monumentLocation!!)
            if (distance < smallestDistance) {
                smallestDistance = distance
                nearestDungeon = dungeon
            }
        }

        if (player.world == aetherWorld() && nearestDungeon != null) {
            val targetLocation = nearestDungeon.monumentLocation!!.clone().add(0.0, 5.0, 0.0)
            val locationString =
                targetLocation.blockX.toString() + " " + targetLocation.blockY + " " + targetLocation.blockZ
            val tagResolver = TagResolver.resolver(
                Placeholder.parsed("location", locationString),
                Placeholder.parsed("distance", smallestDistance.toInt().toString())
            )
            val message = cmdPrefix.append(
                languageManager.getComponent(
                    "messages.cmd.info.next_dungeon",
                    tagResolver
                )
            )
            player.sendMessage(message)
        } else {
            player.sendMessage(cmdPrefix.append(languageManager.getComponent("messages.cmd.errors.no_dungeon_found")))
        }
    }

    private fun teleportCMD(player: Player) {
        player.teleport(aetherWorld().spawnLocation)
    }

    private fun giveCMD(
        player: Player,
        args: Array<out String>,
        languageManager: LanguageManager,
        cmdPrefix: Component
    ) {
        try {
            val item = ItemType.valueOf(args[1].uppercase(Locale.getDefault()))
            player.inventory.addItem(item.getItemStack())
        } catch (exception: IllegalArgumentException) {
            player.sendMessage(
                cmdPrefix
                    .append(languageManager.getComponent("messages.cmd.errors.item_does_not_exist"))
            )
        }
    }

    private fun sendHelp(player: Player, languageManager: LanguageManager, cmdPrefix: Component) {
        for (component in languageManager.getComponentList("messages.cmd.info.help_text")) {
            player.sendMessage(cmdPrefix.append(component))
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (sender !is Player) {
            return null
        }

        val tabComplete: MutableList<String> = ArrayList()
        val completions: ArrayList<String> = ArrayList()

        if (args.size == 1) {
            tabComplete.add("chest")
            tabComplete.add("give")
            tabComplete.add("locate")
            tabComplete.add("reload")
            tabComplete.add("teleport")
            StringUtil.copyPartialMatches(args[0], tabComplete, completions)
        }

        if (args.size == 2 && args[0].equals("give", ignoreCase = true)) {
            //Get ItemNames from enum
            val itemNames = Stream.of(*ItemType.values()).map { obj: ItemType -> obj.name }
                .toList()
            tabComplete.addAll(itemNames)
            StringUtil.copyPartialMatches(args[1], tabComplete, completions)
        }

        completions.sort()
        return completions
    }
}