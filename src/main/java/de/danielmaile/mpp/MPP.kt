package de.danielmaile.mpp

import com.comphenix.protocol.ProtocolLibrary
import de.danielmaile.lama.lamaapi.LamaAPI
import de.danielmaile.mpp.aether.mob.ListenerAetherMobs
import de.danielmaile.mpp.aether.mob.RideableLlama
import de.danielmaile.mpp.aether.world.ListenerAetherWorld
import de.danielmaile.mpp.aether.world.PrefabType
import de.danielmaile.mpp.aether.world.WorldManager
import de.danielmaile.mpp.aether.world.cloud.CloudEffects
import de.danielmaile.mpp.aether.world.dungeon.ListenerDungeon
import de.danielmaile.mpp.aether.world.portal.ListenerPortal
import de.danielmaile.mpp.command.CommandMPP
import de.danielmaile.mpp.config.ConfigManager
import de.danielmaile.mpp.config.LanguageManager
import de.danielmaile.mpp.item.ListenerBlock
import de.danielmaile.mpp.item.ListenerConverter
import de.danielmaile.mpp.item.ListenerCrafting
import de.danielmaile.mpp.item.Recipes
import de.danielmaile.mpp.item.funtion.ListenerArmor
import de.danielmaile.mpp.item.funtion.ListenerItem
import de.danielmaile.mpp.item.funtion.magicwand.ListenerMagicWand
import de.danielmaile.mpp.item.funtion.particle.ListenerParticle
import de.danielmaile.mpp.item.funtion.particle.ParticleManager
import de.danielmaile.mpp.mob.ListenerMPPMobs
import de.danielmaile.mpp.util.logError
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

    fun getLanguageManager(): LanguageManager {
        return configManager.languageManager
    }

    override fun onEnable() {
        instance = this

        Bukkit.getPluginCommand("mpp")?.setExecutor(CommandMPP())

        //Create data folder
        val dataFolder = File(dataFolder.absolutePath + File.separator + "data")
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            logError("Creation of data folder (" + dataFolder.absolutePath + ") failed!")
        }

        //Create locales folder
        val localesFolder = File(getDataFolder().absolutePath + File.separator + "locales")
        if (!localesFolder.exists() && !localesFolder.mkdirs()) {
            logError("Creation of locales folder (" + dataFolder.absolutePath + ") failed!")
        }

        //Save default files
        saveDefaultConfig()
        saveResource("locales/de.yml", false)
        saveResource("locales/en.yml", false)
        reloadConfig()

        //Recipes
        registerRecipes()

        //Protocol lib
        val protocolManager = ProtocolLibrary.getProtocolManager()
        RideableLlama(protocolManager)

        //World Manager
        worldManager = WorldManager()

        //Init object manager and prefabs
        PrefabType.loadPrefabs()

        //Particle manager
        particleManager = ParticleManager()

        //Cloud effects
        CloudEffects()

        //Register events
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
        server.pluginManager.registerEvents(ListenerResourcePack(), this)
        server.pluginManager.registerEvents(ListenerMPPMobs(), this)

        //Setup spigot yml
        checkSpigotYML()

        //Validate license
        LamaAPI.License.registerPluginLicenseChecker(this, "MPP")
    }

    override fun onDisable() {
        worldManager.objectManager.save()
    }

    private fun registerRecipes() {
        for (recipe in Recipes.values()) {
            Bukkit.addRecipe(recipe.recipe)
        }
    }

    //Check if settings in spigot.yml are correct. If not disable the plugin
    private fun checkSpigotYML() {
        val maxValue = 1E100
        val spigotSettingsFile = File(dataFolder.parentFile.parentFile, "spigot.yml")
        val spigotSettings = YamlConfiguration.loadConfiguration(spigotSettingsFile)

        val maxHealthSetting = spigotSettings.get("settings.attribute.maxHealth.max") as Double
        val movementSpeed = spigotSettings.get("settings.attribute.movementSpeed.max") as Double
        val attackDamage = spigotSettings.get("settings.attribute.attackDamage.max") as Double

        //Set settings to max value when they are not set
        if(maxHealthSetting != maxValue || movementSpeed != maxValue || attackDamage != maxValue) {
            for(string in getLanguageManager().getStringList("messages.errors.wrong_spigot_yml_settings")) {
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