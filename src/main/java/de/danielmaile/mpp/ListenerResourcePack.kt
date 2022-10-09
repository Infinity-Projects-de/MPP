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
        player.setResourcePack("https://github.com/DanielMaile/MPP_ResourcePacks/raw/main/MPP_v2.zip",
            "e8e1322dadf0cd871fcd2856fc988523fd175164", true)
    }

    private fun applyAetherResourcePack(player: Player) {
        player.setResourcePack("https://github.com/DanielMaile/MPP_ResourcePacks/raw/main/MPP_Aether_v7.zip",
            "62e99f0fddfbac5d881266d3a32547ae55652814", true)
    }
}