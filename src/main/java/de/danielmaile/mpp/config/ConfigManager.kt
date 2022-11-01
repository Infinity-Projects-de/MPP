package de.danielmaile.mpp.config

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.getResource
import de.danielmaile.mpp.util.logError
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.InputStreamReader

class ConfigManager {

    val languageManager: LanguageManager
    val itemConverterEnabled: Boolean
    val dungeonEndPartChance: Float
    val dungeonPartCap: Int
    val dungeonProbability: Float
    val treeProbability: Float
    val llamaJumpHeight: Double
    val llamaSpeed: Float
    val minDungeonChestChecks: Int
    val maxDungeonChestChecks: Int
    val resourcePackHostIP: String
    val resourcePackHostPort: Int

    init {
        var file = File(
            inst().dataFolder.absolutePath + File.separator
                    + "locales" + File.separator + inst().config.getString("language_file") + ".yml"
        )
        if (!file.exists()) {
            logError("Language file " + inst().config.getString("language_file") + ".yml was not found!")
            file = File(
                (inst().dataFolder.absolutePath + File.separator
                        + "locales" + File.separator + "en.yml")
            )
        }

        val languageFile = YamlConfiguration.loadConfiguration(file)

        // Use locale files included in the plugin as fallback file
        // If the plugin doesn't include the language file provided in the config use the english file
        val fallBackLanguageFileStream = getResource("locales/${inst().config.getString("language_file")}.yml") ?: getResource("locales/en.yml")!!
        val fallBackLanguageFile = YamlConfiguration.loadConfiguration(InputStreamReader(fallBackLanguageFileStream, Charsets.UTF_8))
        languageFile.setDefaults(fallBackLanguageFile)
        languageManager = LanguageManager(languageFile)

        itemConverterEnabled = inst().config.getBoolean("item_converter")
        dungeonEndPartChance = inst().config.getDouble("world_generation.dungeons.end_part_chance").toFloat()
        dungeonProbability = inst().config.getDouble("world_generation.dungeons.probability").toFloat()
        dungeonPartCap = inst().config.getInt("world_generation.dungeons.part_cap")
        treeProbability = inst().config.getDouble("world_generation.trees.probability").toFloat()
        llamaJumpHeight = inst().config.getDouble("entities.llama.jump_height")
        llamaSpeed = inst().config.getDouble("entities.llama.speed").toFloat()
        minDungeonChestChecks = inst().config.getInt("world_generation.dungeons.chests.min_checks")
        maxDungeonChestChecks = inst().config.getInt("world_generation.dungeons.chests.max_checks")

        resourcePackHostIP = inst().config.getString("resource_pack.host.ip")!!
        resourcePackHostPort = inst().config.getInt("resource_pack.host.port")
    }
}