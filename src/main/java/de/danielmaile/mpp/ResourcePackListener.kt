package de.danielmaile.mpp

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class ResourcePackListener(
    private val url: String,
    private val hash: String
    ) : Listener {

        @EventHandler
        fun onPlayerJoin(event: PlayerJoinEvent) {
            event.player.setResourcePack(url, hash, true)
        }
}