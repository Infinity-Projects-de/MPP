package de.danielmaile.aether

import com.comphenix.protocol.ProtocolLibrary
import de.danielmaile.aether.commands.CommandAether
import de.danielmaile.aether.config.ConfigManager
import de.danielmaile.aether.config.LanguageManager
import de.danielmaile.aether.item.ListenerBlock
import de.danielmaile.aether.item.ListenerConverter
import de.danielmaile.aether.item.ListenerCrafting
import de.danielmaile.aether.item.Recipes
import de.danielmaile.aether.item.funtion.ListenerArmor
import de.danielmaile.aether.item.funtion.ListenerItem
import de.danielmaile.aether.item.funtion.magicwand.ListenerMagicWand
import de.danielmaile.aether.item.funtion.particle.ListenerParticle
import de.danielmaile.aether.item.funtion.particle.ParticleManager
import de.danielmaile.aether.listeners.ListenerAetherVoid
import de.danielmaile.aether.listeners.ListenerDungeon
import de.danielmaile.aether.listeners.ListenerWorldSave
import de.danielmaile.aether.mobs.ListenerAetherMobs
import de.danielmaile.aether.mobs.RideableLlama
import de.danielmaile.aether.portal.ListenerPortal
import de.danielmaile.aether.util.logError
import de.danielmaile.aether.worldgen.AetherWorld
import de.danielmaile.aether.worldgen.ListenerWorldGeneration
import de.danielmaile.aether.worldgen.ObjectManager
import de.danielmaile.aether.worldgen.PrefabType
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Aether : JavaPlugin() {

    companion object {

        @JvmStatic
        lateinit var instance: Aether
            private set
    }

    lateinit var configManager: ConfigManager
        private set

    lateinit var particleManager: ParticleManager
        private set

    lateinit var aetherWorld: AetherWorld
        private set

    fun getLanguageManager(): LanguageManager {
        return configManager.languageManager
    }

    fun getObjectManager(): ObjectManager {
        return aetherWorld.objectManager
    }

    override fun onEnable() {
        instance = this

        //Register listener and commands
        server.pluginManager.registerEvents(ListenerAetherVoid(), this)
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
        server.pluginManager.registerEvents(ListenerWorldGeneration(), this)
        server.pluginManager.registerEvents(ListenerWorldSave(), this)
        Bukkit.getPluginCommand("aether")?.setExecutor(CommandAether())

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
        val deFile = File(getDataFolder().absolutePath + File.separator + "locales" + File.separator + "de.yml")
        if (!deFile.exists()) {
            saveResource("locales/de.yml", false)
        }
        reloadConfig()

        //Recipes
        registerRecipes()

        //Protocol lib
        val protocolManager = ProtocolLibrary.getProtocolManager()
        RideableLlama(protocolManager)

        //Init world and prefabs
        aetherWorld = AetherWorld()
        PrefabType.loadPrefabs()

        //Particle manager
        particleManager = ParticleManager()
    }

    override fun onDisable() {
        getObjectManager().save()
    }

    private fun registerRecipes() {
        for (recipe in Recipes.values()) {
            Bukkit.addRecipe(recipe.recipe)
        }
    }

    override fun reloadConfig() {
        super.reloadConfig()
        configManager = ConfigManager()
    }
}

fun inst(): Aether {
    return Aether.instance
}