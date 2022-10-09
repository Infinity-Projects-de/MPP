package de.danielmaile.mpp.config

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.logError
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

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

        languageManager = LanguageManager(YamlConfiguration.loadConfiguration(file))
        itemConverterEnabled = inst().config.getBoolean("item_converter")
        dungeonEndPartChance = inst().config.getDouble("world_generation.dungeons.end_part_chance").toFloat()
        dungeonProbability = inst().config.getDouble("world_generation.dungeons.probability").toFloat()
        dungeonPartCap = inst().config.getInt("world_generation.dungeons.part_cap")
        treeProbability = inst().config.getDouble("world_generation.trees.probability").toFloat()
        llamaJumpHeight = inst().config.getDouble("entities.llama.jump_height")
        llamaSpeed = inst().config.getDouble("entities.llama.speed").toFloat()
        minDungeonChestChecks = inst().config.getInt("world_generation.dungeons.chests.min_checks")
        maxDungeonChestChecks = inst().config.getInt("world_generation.dungeons.chests.max_checks")
    }
}