/*
 * This file is part of MPP.
 * Copyright (c) 2023 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.data

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.copyAssetsFromJar
import de.danielmaile.mpp.util.directoryEquals
import de.danielmaile.mpp.util.logInfo
import org.apache.commons.io.FileUtils
import org.bukkit.Bukkit
import java.nio.file.Paths

object DataPackManager {

    private val dataPackWorkingDirectory = Paths.get(inst().dataFolder.absolutePath, "mpp_datapack")
    private val dataPackWorldPath = Paths.get(Bukkit.getWorldContainer().toString(), Bukkit.getWorlds()[0].name, "datapacks", "mpp_datapack")

    /**
     * Saves or updates the data pack included in the plugin jar
     */
    fun saveOrUpdateDataPack() {

        // generate pack from jar
        copyAssetsFromJar("mpp_datapack", dataPackWorkingDirectory)

        // determine if newer version differs from existing one
       // val differentVersion = !directoryEquals(dataPackWorldPath.toFile(), dataPackWorkingDirectory.toFile())

        // if new version is available, delete old version and copy new one
        if(true) {

            // disable pack in order to delete it
            Bukkit.getDatapackManager().packs.find { it.name == "file/mpp_datapack.zip" }?.let {
                it.isEnabled = false
            }

            FileUtils.deleteDirectory(dataPackWorldPath.toFile())
            FileUtils.copyDirectory(dataPackWorkingDirectory.toFile(), dataPackWorldPath.toFile())

            // enable the pack
            Bukkit.getDatapackManager().packs.find { it.name == "file/mpp_datapack.zip" }?.let {
                it.isEnabled = true
            }

            logInfo("A new datapack version found! Please restart the server!")
        }

        // delete generated pack
        FileUtils.deleteDirectory(dataPackWorkingDirectory.toFile())
    }
}