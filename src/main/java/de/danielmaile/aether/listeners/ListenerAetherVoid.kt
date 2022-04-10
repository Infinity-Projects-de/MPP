package de.danielmaile.aether.listeners

import de.danielmaile.aether.inst
import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.event.player.PlayerMoveEvent

class ListenerAetherVoid : Listener {

    /***
     * Teleports players back to the overworld when they fall out of the aether
     */
    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val playerLoc = player.location
        if (player.world != inst().aetherWorld.getWorld()) return
        if (playerLoc.y > 64 || playerLoc.y < -256) return

        val destination = Location(Bukkit.getWorlds()[0], playerLoc.x, 400.0, playerLoc.z)
        player.teleport(destination)
        event.isCancelled = true
    }

    /***
     * Kill all entities that fall out of the aether
     */
    @EventHandler
    fun onEntityMove(event: EntityMoveEvent) {
        val entity: Entity = event.entity
        if (entity.world != inst().aetherWorld.getWorld()) return
        if (entity.location.y > 64 || entity.location.y < -256) return

        entity.remove()
        event.isCancelled = true
    }

    /***
     * Stop water and lava from flowing out of aether
     */
    @EventHandler
    fun onBlockFlow(event: BlockFromToEvent) {
        if (event.block.world != inst().aetherWorld.getWorld()) return
        if (event.toBlock.y > 64 || event.toBlock.y < -256) return
        event.isCancelled = true
    }
}