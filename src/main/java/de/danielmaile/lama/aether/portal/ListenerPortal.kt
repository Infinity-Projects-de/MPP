package de.danielmaile.lama.aether.portal

import de.danielmaile.lama.aether.aetherWorld
import de.danielmaile.lama.aether.inst
import de.danielmaile.lama.aether.portal.AetherPortal.checkPortal
import de.danielmaile.lama.aether.portal.AetherPortal.createPortal
import de.danielmaile.lama.aether.portal.AetherPortal.findPortalInRadius
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerMoveEvent

class ListenerPortal : Listener {

    @EventHandler
    fun onWaterBucketClick(event: PlayerBucketEmptyEvent) {
        if (event.bucket != Material.WATER_BUCKET) return
        if (!checkPortal(event.block.location, false)) return

        //Explode Portal in Nether or End
        if (event.player.world.environment == World.Environment.NETHER || event.player.world.environment == World.Environment.THE_END) {
            event.player.world.createExplosion(event.blockClicked.location, 20f, true)
            return
        }
        event.isCancelled = true
    }

    @EventHandler
    fun onAetherTeleport(event: PlayerMoveEvent) {
        if (!checkPortal(event.to, true)) return

        val toLocation = event.player.location.clone()
        toLocation.world = if (event.player.world == aetherWorld()) Bukkit.getWorlds()[0] else aetherWorld()
        val portalLocation = findPortalInRadius(toLocation, 32)

        if (portalLocation != null) {
            event.player.teleport(portalLocation.add(0.0, 4.0, 0.0))
        } else {
            toLocation.y = (toLocation.world.getHighestBlockYAt(toLocation) + 2).toDouble()
            createPortal(toLocation)
            event.player.teleport(toLocation.add(0.0, 4.0, 0.0))
        }
    }
}