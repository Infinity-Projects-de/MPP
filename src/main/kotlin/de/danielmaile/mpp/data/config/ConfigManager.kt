/*
 * This file is part of MPP.
 * Copyright (c) 2022-2023 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.data.config

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.getResource
import de.danielmaile.mpp.util.logError
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.InputStreamReader

class ConfigManager {

    val languageManager: LanguageManager
    val itemConverterEnabled: Boolean
    val llamaJumpHeight: Double
    val llamaSpeed: Float
    val resourcePackHostIP: String
    val resourcePackHostPort: Int

    init {
        var file = File(
            inst().dataFolder.absolutePath + File.separator +
                "locales" + File.separator + inst().config.getString("language_file") + ".yml"
        )
        if (!file.exists()) {
            logError("Language file " + inst().config.getString("language_file") + ".yml was not found!")
            file = File(
                (
                    inst().dataFolder.absolutePath + File.separator +
                        "locales" + File.separator + "en.yml"
                    )
            )
        }

        val languageFile = YamlConfiguration.loadConfiguration(file)

        // use locale files included in the plugin as fallback file
        // if the plugin doesn't include the language file provided in the config use the english file
        val fallBackLanguageFileStream =
            getResource("locales/${inst().config.getString("language_file")}.yml") ?: getResource("locales/en.yml")!!
        val fallBackLanguageFile =
            YamlConfiguration.loadConfiguration(InputStreamReader(fallBackLanguageFileStream, Charsets.UTF_8))
        languageFile.setDefaults(fallBackLanguageFile)
        languageManager = LanguageManager(languageFile)

        itemConverterEnabled = inst().config.getBoolean("item_converter")
        llamaJumpHeight = inst().config.getDouble("entities.llama.jump_height")
        llamaSpeed = inst().config.getDouble("entities.llama.speed").toFloat()

        resourcePackHostIP = inst().config.getString("resource_pack.host.ip")!!
        resourcePackHostPort = inst().config.getInt("resource_pack.host.port")
    }
}
