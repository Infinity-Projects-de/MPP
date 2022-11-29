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

import de.danielmaile.mpp.aether.mob.ListenerAetherMobs
import de.danielmaile.mpp.aether.mob.RideableLlama
import de.danielmaile.mpp.aether.world.ListenerAetherWorld
import de.danielmaile.mpp.aether.world.PrefabType
import de.danielmaile.mpp.aether.world.WorldManager
import de.danielmaile.mpp.aether.world.dungeon.ListenerDungeon
import de.danielmaile.mpp.aether.world.portal.ListenerPortal
import de.danielmaile.mpp.block.BlockBreakingService
import de.danielmaile.mpp.block.ListenerBlock
import de.danielmaile.mpp.block.cloud.CloudEffects
import de.danielmaile.mpp.command.CommandMPP
import de.danielmaile.mpp.config.ConfigManager
import de.danielmaile.mpp.config.LanguageManager
import de.danielmaile.mpp.data.LicenseManager
import de.danielmaile.mpp.data.ResourcePackBuilder
import de.danielmaile.mpp.demo.ListenerJoinDemo
import de.danielmaile.mpp.item.ListenerConverter
import de.danielmaile.mpp.item.ListenerCrafting
import de.danielmaile.mpp.item.Recipes
import de.danielmaile.mpp.item.funtion.ListenerArmor
import de.danielmaile.mpp.item.funtion.ListenerItem
import de.danielmaile.mpp.item.funtion.magicwand.ListenerMagicWand
import de.danielmaile.mpp.item.funtion.particle.ListenerParticle
import de.danielmaile.mpp.item.funtion.particle.ParticleManager
import de.danielmaile.mpp.mob.ListenerMPPMobs
import de.danielmaile.mpp.mob.MPPMobSpawnManager
import de.danielmaile.mpp.mob.listeners.*
import de.danielmaile.mpp.util.logError
import de.danielmaile.mpp.util.logInfo
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence
import org.bukkit.Bukkit
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

    lateinit var particleManager: ParticleManager
        private set

    lateinit var worldManager: WorldManager
        private set

    private lateinit var entityManagerFactory: EntityManagerFactory

    lateinit var entityManager: EntityManager
        private set

    var validLicense: Boolean = false
        private set

    fun getLanguageManager(): LanguageManager {
        return configManager.languageManager
    }

    override fun onEnable() {
        instance = this

        Bukkit.getPluginCommand("mpp")?.setExecutor(CommandMPP())

        // create data folder
        val dataFolder = File(dataFolder.absolutePath + File.separator + "data")
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            logError("Creation of data folder (" + dataFolder.absolutePath + ") failed!")
        }

        // create locales folder
        val localesFolder = File(getDataFolder().absolutePath + File.separator + "locales")
        if (!localesFolder.exists() && !localesFolder.mkdirs()) {
            logError("Creation of locales folder (" + dataFolder.absolutePath + ") failed!")
        }

        // save default files
        saveDefaultConfig()
        if (!File(getDataFolder(), "locales/de.yml").exists())
            saveResource("locales/de.yml", false)
        if (!File(getDataFolder(), "locales/en.yml").exists())
            saveResource("locales/en.yml", false)
        reloadConfig()

        // check license
        logInfo("Validating license...")
        validLicense = LicenseManager.validateLicense()
        if(!validLicense) {
            logError("License key validation failed! Please check your key in the config and restart the server to try again. If this fails again try updating the plugin. Demo mode gets disabled...")
        }

        // generate resource pack
        ResourcePackBuilder()

        // connect to database
        Thread.currentThread().contextClassLoader = inst().javaClass.classLoader
        entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit")
        entityManager = entityManagerFactory.createEntityManager()

        // recipes
        registerRecipes()

        // rideable Llama
        RideableLlama()

        // world Manager
        worldManager = WorldManager(this)

        // init object manager and prefabs
        PrefabType.loadPrefabs()

        // particle manager
        particleManager = ParticleManager()

        // cloud effects
        CloudEffects()

        // register events
        registerEvents()

        // setup spigot and paper yml
        checkSpigotYML()
        checkPaperYML()

        // init BlockBreakingService
        BlockBreakingService.init()
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(ListenerPortal(), this)
        server.pluginManager.registerEvents(ListenerBlock(), this)
        server.pluginManager.registerEvents(ListenerCrafting(), this)
        server.pluginManager.registerEvents(ListenerDungeon(), this)
        server.pluginManager.registerEvents(ListenerAetherMobs(), this)
        server.pluginManager.registerEvents(ListenerItem(), this)
        server.pluginManager.registerEvents(ListenerArmor(), this)
        server.pluginManager.registerEvents(ListenerParticle(), this)
        server.pluginManager.registerEvents(ListenerConverter(), this)
        server.pluginManager.registerEvents(ListenerMagicWand(), this)
        server.pluginManager.registerEvents(ListenerAetherWorld(), this)
        server.pluginManager.registerEvents(ListenerMPPMobs(), this)
        server.pluginManager.registerEvents(MPPMobSpawnManager(), this)
        server.pluginManager.registerEvents(NecromancerListener(), this)
        server.pluginManager.registerEvents(KingListener(), this)
        server.pluginManager.registerEvents(PlagueListener(), this)
        server.pluginManager.registerEvents(RiftListener(), this)
        server.pluginManager.registerEvents(HealerListener(), this)
        server.pluginManager.registerEvents(HitmanListener(), this)
        server.pluginManager.registerEvents(ListenerJoinDemo(), this)
    }

    override fun onDisable() {
        entityManager.close()
        entityManagerFactory.close()
    }

    private fun registerRecipes() {
        for (recipe in Recipes.values()) {
            Bukkit.addRecipe(recipe.recipe)
        }
    }

    /**
     * Checks if settings in spigot.yml are correct.
     * If not disable the plugin.
     */
    private fun checkSpigotYML() {
        val maxValue = 1E100
        val spigotSettingsFile = File(dataFolder.parentFile.parentFile, "spigot.yml")
        val spigotSettings = YamlConfiguration.loadConfiguration(spigotSettingsFile)

        val maxHealthSetting = spigotSettings.get("settings.attribute.maxHealth.max") as Double
        val movementSpeed = spigotSettings.get("settings.attribute.movementSpeed.max") as Double
        val attackDamage = spigotSettings.get("settings.attribute.attackDamage.max") as Double

        // send error message when values are not correct
        if (maxHealthSetting != maxValue || movementSpeed != maxValue || attackDamage != maxValue) {
            for (string in getLanguageManager().getStringList("messages.errors.wrong_spigot_yml_settings")) {
                logError(string)
            }
            Bukkit.getPluginManager().disablePlugin(this)
        }
    }

    /**
     * Checks if settings in paper-world-defaults.yml are correct.
     * If not disable the plugin.
     */
    private fun checkPaperYML() {
        val paperWorldDefaultsSettingsFile =
            File(dataFolder.parentFile.parentFile, "config" + File.separator + "paper-world-defaults.yml")
        val paperWorldDefaultsSettings = YamlConfiguration.loadConfiguration(paperWorldDefaultsSettingsFile)

        val countAllMobsForSpawning =
            paperWorldDefaultsSettings.get("entities.spawning.count-all-mobs-for-spawning") as Boolean

        // send error message when value is not correct
        if (!countAllMobsForSpawning) {
            for (string in getLanguageManager().getStringList("messages.errors.wrong_paper_world_defaults_yml_settings")) {
                logError(string)
            }
            Bukkit.getPluginManager().disablePlugin(this)
        }
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
    return MPP.instance.worldManager.world
}