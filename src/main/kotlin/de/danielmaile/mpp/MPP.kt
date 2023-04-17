/*
 * This file is part of MPP.
 * Copyright (c) 2022 by it's authors. All rights reserved.
 * MPP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MPP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MPP.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.danielmaile.mpp

import de.danielmaile.mpp.block.BlockBreakingService
import de.danielmaile.mpp.block.ListenerBlock
import de.danielmaile.mpp.block.function.CloudEffects
import de.danielmaile.mpp.command.CommandMPP
import de.danielmaile.mpp.data.ResourcePackManager
import de.danielmaile.mpp.data.config.ConfigManager
import de.danielmaile.mpp.data.config.LanguageManager
import de.danielmaile.mpp.item.Armors
import de.danielmaile.mpp.item.Blocks
import de.danielmaile.mpp.item.Ingredients
import de.danielmaile.mpp.item.ItemRegistry
import de.danielmaile.mpp.item.ListenerConverter
import de.danielmaile.mpp.item.ListenerCrafting
import de.danielmaile.mpp.item.Tools
import de.danielmaile.mpp.item.function.ArmorListener
import de.danielmaile.mpp.item.function.ListenerItem
import de.danielmaile.mpp.mob.ListenerMPPMobs
import de.danielmaile.mpp.mob.MPPMobSpawnManager
import de.danielmaile.mpp.mob.listeners.ListenerHealer
import de.danielmaile.mpp.mob.listeners.ListenerHitman
import de.danielmaile.mpp.mob.listeners.ListenerKing
import de.danielmaile.mpp.mob.listeners.ListenerNecromancer
import de.danielmaile.mpp.mob.listeners.ListenerPlague
import de.danielmaile.mpp.mob.listeners.ListenerRift
import de.danielmaile.mpp.packet.PacketHandler
import de.danielmaile.mpp.util.logError
import de.danielmaile.mpp.world.WorldManager
import de.danielmaile.mpp.world.aether.ListenerAether
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class MPP : JavaPlugin() {

    companion object {
        @JvmStatic
        lateinit var instance: MPP
            private set
    }

    lateinit var configManager: ConfigManager
        private set

    lateinit var worldManager: WorldManager
        private set

    fun getLanguageManager(): LanguageManager {
        return configManager.languageManager
    }

    override fun onEnable() {
        instance = this
        saveDefaultFiles()

        // setup spigot and paper yml
        if (!checkSpigotYML() || !checkPaperYML()) {
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        PacketHandler.enable(this)
        registerPacketListners()
//        DataPackManager.saveOrUpdateDataPack()

        // register commands, events and recipes
        Bukkit.getPluginCommand("mpp")?.setExecutor(CommandMPP())

        registerItems()

//        registerEvents()
        registerNewEvents()

        /*try {
            worldManager = WorldManager()
        } catch (e: Exception) {
            installMessage(e.message)
            Bukkit.shutdown()
            return
        }
*/
        // cloud effects
        CloudEffects()

        BlockBreakingService.initializeBreakingScheduler()

        Metrics(this, 18055)
    }

    private fun registerPacketListeners() {
        PacketHandler.registerListeners(BlockBreakingService)
    }

    /**
     * Send message on first install, might be changed to a better one?
     */
    private fun installMessage(message: String?) {
        sendMessage("------------------------------")
        sendMessage("")
        sendMessage("")
        for (s in getLanguageManager().getStringList("messages.errors.first_time_server_shutdown")) {
            sendMessage(s)
        }
        if (!message.isNullOrBlank()) {
            sendMessage(message)
        } else {
            sendMessage("")
        }
        sendMessage("")
        sendMessage("------------------------------")
    }

    override fun onDisable() {
        PacketHandler.destroy()
    }

    private fun sendMessage(text: String) {
        val color = ChatColor.GREEN
        server.consoleSender.sendMessage(color.toString() + text)
    }

    private fun saveDefaultFiles() {
        // create locales folder
        val localesFolder = File(dataFolder.absolutePath + File.separator + "locales")
        if (!localesFolder.exists() && !localesFolder.mkdirs()) {
            logError("Creation of locales folder (" + dataFolder.absolutePath + ") failed!")
        }

        // save default files
        saveDefaultConfig()
        if (!File(dataFolder, "locales/de.yml").exists()) {
            saveResource("locales/de.yml", false)
        }
        if (!File(dataFolder, "locales/en.yml").exists()) {
            saveResource("locales/en.yml", false)
        }

        reloadConfig()
    }

    private fun registerNewEvents() {
        server.pluginManager.registerEvents(ArmorListener(), this)
        server.pluginManager.registerEvents(ResourcePackManager(), this)
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(ListenerAether(), this)
        server.pluginManager.registerEvents(ListenerBlock(), this)
        server.pluginManager.registerEvents(ListenerCrafting(), this)
        server.pluginManager.registerEvents(ListenerItem(), this)
        server.pluginManager.registerEvents(ListenerConverter(), this)
        server.pluginManager.registerEvents(ListenerMPPMobs(), this)
        server.pluginManager.registerEvents(MPPMobSpawnManager(), this)
        server.pluginManager.registerEvents(ListenerNecromancer(), this)
        server.pluginManager.registerEvents(ListenerKing(), this)
        server.pluginManager.registerEvents(ListenerPlague(), this)
        server.pluginManager.registerEvents(ListenerRift(), this)
        server.pluginManager.registerEvents(ListenerHealer(), this)
        server.pluginManager.registerEvents(ListenerHitman(), this)
    }

    private fun registerRecipes() {
        /*for (recipes in recipeList) {
            for (recipe in recipes.spigotRecipes) {
                Bukkit.addRecipe(recipe)
            }
        }*/
    }

    /**
     * Checks if settings in spigot.yml are correct.
     * If not disable the plugin.
     */
    private fun checkSpigotYML(): Boolean {
        val maxValue = 1E100
        val spigotSettingsFile = File(dataFolder.parentFile.parentFile, "spigot.yml")

        setupSpigotConfig(spigotSettingsFile)

        val spigotSettings = YamlConfiguration.loadConfiguration(spigotSettingsFile)

        val maxHealthSetting = spigotSettings.get("settings.attribute.maxHealth.max") as Double
        val movementSpeed = spigotSettings.get("settings.attribute.movementSpeed.max") as Double
        val attackDamage = spigotSettings.get("settings.attribute.attackDamage.max") as Double

        // send error message when values are not correct
        if (maxHealthSetting != maxValue || movementSpeed != maxValue || attackDamage != maxValue) {
            for (string in getLanguageManager().getStringList("messages.errors.wrong_spigot_yml_settings")) {
                logError(string)
            }
            return false
        }
        return true
    }

    /**
     * Sets health, speed and damage from spigot settings to their desired values of 1.0E100
     */
    private fun setupSpigotConfig(file: File) {
        val spigotSettings = YamlConfiguration.loadConfiguration(file)

        spigotSettings.set("settings.attribute.maxHealth.max", 1.0E100)
        spigotSettings.set("settings.attribute.movementSpeed.max", 1.0E100)
        spigotSettings.set("settings.attribute.attackDamage.max", 1.0E100)

        spigotSettings.save(file)
    }

    /**
     * Checks if settings in paper-world-defaults.yml are correct.
     * If not disable the plugin.
     */
    private fun checkPaperYML(): Boolean {
        val paperWorldDefaultsSettingsFile =
            File(dataFolder.parentFile.parentFile, "config" + File.separator + "paper-world-defaults.yml")

        setupPaperConfig(paperWorldDefaultsSettingsFile)

        val paperWorldDefaultsSettings = YamlConfiguration.loadConfiguration(paperWorldDefaultsSettingsFile)

        val countAllMobsForSpawning =
            paperWorldDefaultsSettings.get("entities.spawning.count-all-mobs-for-spawning") as Boolean

        // send error message when value is not correct
        if (!countAllMobsForSpawning) {
            for (string in getLanguageManager().getStringList("messages.errors.wrong_paper_world_defaults_yml_settings")) {
                logError(string)
            }
            return false
        }
        return true
    }

    /**
     * Sets count all mobs for spawning from paper settings to their desired value of true
     */
    private fun setupPaperConfig(file: File) {
        val paperSettings = YamlConfiguration.loadConfiguration(file)

        paperSettings.set("entities.spawning.count-all-mobs-for-spawning", true)

        paperSettings.save(file)
    }

    override fun reloadConfig() {
        super.reloadConfig()
        configManager = ConfigManager()
    }
}

fun inst(): MPP {
    return MPP.instance
}

fun aetherWorld(): World {
    return MPP.instance.worldManager.aetherWorld
}

// FIXME: I don't really like this here it's quite unintuitive, but I need it for the resource pack builder
fun registerItems() {
    ItemRegistry.registerItems(Armors.values())
    ItemRegistry.registerItems(Blocks.values())
    ItemRegistry.registerItems(Ingredients.values())
    ItemRegistry.registerItems(Tools.values())
}
