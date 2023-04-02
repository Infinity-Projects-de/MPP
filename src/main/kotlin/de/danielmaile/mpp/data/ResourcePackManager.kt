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
import de.danielmaile.mpp.util.logError
import de.danielmaile.mpp.util.logInfo
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.codec.digest.DigestUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerResourcePackStatusEvent
import java.io.File
import java.io.FileInputStream
import java.net.InetAddress
import java.net.URL
import java.util.concurrent.TimeUnit

class ResourcePackManager : Listener {

    private val httpClient = OkHttpClient.Builder()
        .callTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build()

    private val pluginVersion = "MPP-${inst().pluginMeta.version}"
    private val ipAddress = InetAddress.getLocalHost().hostAddress
    private val localPackLink = "http://$ipAddress:8080/$pluginVersion.zip"
    private val githubPackLink = "https://github.com/dm432/MPP/releases/download/$pluginVersion/$pluginVersion.zip"

    private val resourcePackLink: String?
    private val packHash: String?

    init {
        val localPackExists = doesPackExist(URL(localPackLink))
        val githubPackExists = doesPackExist(URL(githubPackLink))

        resourcePackLink = when {
            localPackExists -> {
                logInfo("Using local hosted mpp resource pack.")
                localPackLink
            }

            githubPackExists -> {
                logInfo("Using remote hosted mpp resource pack.")
                githubPackLink
            }

            else -> {
                logError(
                    "No mpp resource pack found! Make sure that you are using the latest plugin version. " +
                        "If you're running the plugin in a development environment make sure that the local " +
                        "ResourcePackHostingServer is running."
                )
                null
            }
        }

        // Download pack
        val request = Request.Builder()
            .url(resourcePackLink ?: "")
            .build()

        val packFile = File(inst().dataFolder, "$pluginVersion.zip")
        try {
            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful && response.body != null) {
                response.body!!.byteStream().use { input ->
                    packFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            } else {
                logError("Error while downloading mpp resource pack to calculate the hash! Please restart the server to try again.")
            }
        } catch (e: Exception) {
            logError("Error while downloading mpp resource pack to calculate the hash! Please restart the server to try again.")
        }

        packHash = calculateSHA1Hash(packFile)
        if (packHash != null) {
            logInfo("Resource pack hash is $packHash")
        }

        packFile.delete()
    }

    // Checks if the resource pack exists at the given link
    private fun doesPackExist(url: URL): Boolean {
        val request = Request.Builder()
            .head()
            .url(url)
            .build()

        return try {
            val response = httpClient.newCall(request).execute()
            val exists = response.code == 200
            response.close()
            return exists
        } catch (e: Exception) {
            false
        }
    }

    // calculates and returns the sha1 hash of a given File or null if the file does not exist
    private fun calculateSHA1Hash(file: File): String? {
        if (!file.exists()) return null
        FileInputStream(file).use {
            return DigestUtils.sha1Hex(it)
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (resourcePackLink == null || packHash == null) return
        event.player.setResourcePack(resourcePackLink, packHash, true)
    }

    // kick players when they decline the mpp resource pack and
    // force resource pack is enabled in the config
    @EventHandler
    fun onResourcePackStatusChanges(event: PlayerResourcePackStatusEvent) {
        if (!inst().configManager.resourcePackForce) return
        if (event.status == PlayerResourcePackStatusEvent.Status.DECLINED) {
            event.player.kick(inst().getLanguageManager().getComponent("messages.errors.kick_resource_pack_declined"))
        }
    }
}
