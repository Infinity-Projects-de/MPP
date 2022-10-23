package de.danielmaile.mpp.mob.listeners

import de.danielmaile.mpp.event.MPPMobSpawnEvent
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.mob.MPPMob
import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

class HitmanListener : MobListener(MPPMob.HITMAN, MPPMob.HITMAN_ELITE) {

    private val hidden = ArrayList<Int>()

    init {
        for (world in inst().server.worlds)
            for (entity in world.livingEntities) {
                if (shouldListen(entity as LivingEntity)) {
                    hidden.add(entity.entityId)
                    for (player in inst().server.onlinePlayers)
                        player.hideEntity(inst(), entity)
                }
            }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        for (world in inst().server.worlds)
            for (entity in world.livingEntities) {
                if (hidden.contains(entity.entityId))
                    event.player.hideEntity(inst(), entity)
            }
    }

    @EventHandler
    fun onMobSpawn(event: MPPMobSpawnEvent) {
        if (shouldListen(event.mob)) {
            hidden.add(event.entity.entityId)
            for (player in inst().server.onlinePlayers)
                player.hideEntity(inst(), event.entity)
        }
    }

    @EventHandler
    fun onMoveEntity(event: EntityMoveEvent) {
        //Do not calc for small movements
        if (!event.hasChangedBlock() || !hidden.contains(event.entity.entityId)) return
        if (event.entity.getNearbyEntities(5.0, 5.0, 5.0).filterIsInstance<Player>().isNotEmpty()) {
            hidden.remove(event.entity.entityId)
            for (player in inst().server.onlinePlayers) {
                player.showEntity(inst(), event.entity)
            }
        }
    }

    @EventHandler
    fun onMovePlayer(event: PlayerMoveEvent) {
        //Do not calc for small movements
        if (!event.hasChangedBlock()) return
        val nearby = event.player.getNearbyEntities(5.0, 5.0, 5.0).filterIsInstance<LivingEntity>()
        if (nearby.isNotEmpty()) {
            for (entity in nearby) {
                if (hidden.contains(entity.entityId)) {
                    hidden.remove(entity.entityId)
                    for (player in inst().server.onlinePlayers) {
                        player.showEntity(inst(), entity)
                    }
                }
            }
        }
    }
}