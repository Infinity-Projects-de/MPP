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
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerResourcePackStatusEvent
import java.net.HttpURLConnection
import java.net.URL

class ResourcePackManager : Listener {

    private val githubPackLink: String
    private val packEnabled: Boolean

    init {
        val pluginVersion = "MPP-${inst().pluginMeta.version}"
        githubPackLink = "https://github.com/dm432/MPP/releases/download/$pluginVersion/$pluginVersion.zip"

        packEnabled = doesPackExist(URL(githubPackLink))
        if (!packEnabled) {
            logError("The MPP Resource Pack was not found on Github! Please make sure to download the latest official Release from https://github.com/dm432/MPP/releases")
        }
    }

    // Checks if the resource pack exits at the given link
    private fun doesPackExist(url: URL): Boolean {
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        return connection.responseCode == HttpURLConnection.HTTP_OK
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (!packEnabled) return
        // TODO use pack hash
        event.player.setResourcePack(githubPackLink)
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
