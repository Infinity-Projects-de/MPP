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
import de.danielmaile.mpp.util.getPluginJar
import de.danielmaile.mpp.util.logError
import de.danielmaile.mpp.util.saveResource
import org.bukkit.Bukkit
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile
import kotlin.io.path.exists

object DataPackManager {

    /**
     * Saves or updates the data pack included in the plugin jar
     */
    fun saveOrUpdateDataPack() {
        // determine if newer version is available from jar
        val versionJar = getVersion(inst().javaClass.classLoader.getResourceAsStream("mpp_datapack/version.txt")!!)
        if (versionJar == -1) {
            logError("Could not detect data pack version from jar! Aborted data pack update.")
            return
        }
        val dataPackPath = Bukkit.getWorldContainer()
            .toString() + File.separator + Bukkit.getWorlds()[0].name + File.separator + "datapacks" + File.separator + "mpp_datapack"
        val versionFilePath = dataPackPath + File.separator + "version.txt"
        if (File(versionFilePath).exists() && versionJar <= getVersion(FileInputStream(versionFilePath)))
            return

        // delete old data pack files
        if (Paths.get(dataPackPath).exists())
            Files.walk(Paths.get(dataPackPath)).use { dirStream ->
                dirStream
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete)
            }

        // copy updated data pack from jar
        val jarFile = getPluginJar()
        if (jarFile.isFile) {
            val path = "mpp_datapack"
            val jar = JarFile(jarFile)
            val entries: Enumeration<JarEntry> = jar.entries()
            while (entries.hasMoreElements()) {
                val jarEntry = entries.nextElement()
                val name: String = jarEntry.name
                if (name.startsWith("$path/")) {
                    if (name.endsWith('/')) continue
                    saveResourceToWorldFolder(name)
                }
            }
            jar.close()
        } else {
            logError("[THIS SHOULD NOT BE REACHED] Failed to copy data pack.")
        }
    }

    private fun getVersion(versionFileStream: InputStream): Int {
        val versionFileContents = String(versionFileStream.readAllBytes())
        var version = -1
        for (line in versionFileContents.split('\n'))
            if (line.startsWith("version: "))
                version = Integer.parseInt(line.replace("version: ", "").trim())
        versionFileStream.close()
        return version
    }

    @Throws(IOException::class)
    private fun saveResourceToWorldFolder(resourcePath: String) {
        val outputPath = Paths.get(Bukkit.getWorldContainer().toString(), Bukkit.getWorlds()[0].name, "datapacks", resourcePath)
        saveResource(resourcePath, outputPath)
    }
}