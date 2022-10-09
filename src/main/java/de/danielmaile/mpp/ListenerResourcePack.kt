package de.danielmaile.mpp

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerJoinEvent

class ListenerResourcePack: Listener {

    @EventHandler
    fun onPlayerChangeWorld(event: PlayerChangedWorldEvent) {
        val player = event.player

        //Player teleported to aether
        if(player.world == aetherWorld()) {
            applyAetherResourcePack(player)
        }

        //Player teleported out of aether
        if(event.from == aetherWorld()) {
            applyDefaultResourcePack(player)
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        if(player.world == aetherWorld()) {
            applyAetherResourcePack(player)
        } else {
            applyDefaultResourcePack(player)
        }
    }

    private fun applyDefaultResourcePack(player: Player) {
        player.setResourcePack("https://github.com/DanielMaile/MPP_ResourcePacks/raw/main/MPP_v3.zip",
            "376e954f49daf77e876ac607c90b9beb0dca9a20", true)
    }

    private fun applyAetherResourcePack(player: Player) {
        player.setResourcePack("https://github.com/DanielMaile/MPP_ResourcePacks/raw/main/MPP_Aether_v8.zip",
            "97c290743c14935add7dc9216942b5f0ba34ce32", true)
    }
}