package de.danielmaile.mpp.mob.listeners

import de.danielmaile.mpp.mob.MPPMob
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import java.util.*

class RiftListener : MobListener(MPPMob.RIFT, MPPMob.RIFT_ELITE) {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onDamage(event: EntityDamageByEntityEvent) {
        if (!shouldListen(event.entity)) return
        if (event.damage >= (event.entity as LivingEntity).health) return

        val heights = intArrayOf(0, 1, -1, 2, -2, 3, -3, 4, -4)
        for (height in heights) {
            //Make 8 attempts per height to teleport the rift
            for (i in 0..8) {
                val loc = getRandomLocationPlateau(
                    event.entity.location.add(0.0, height.toDouble(), 0.0)
                )
                if (canTeleport(loc.block)) {
                    event.entity.teleport(loc)
                    return
                }
            }
        }

    }

    private fun canTeleport(block: Block): Boolean {
        return block.isPassable && block.getRelative(BlockFace.DOWN).isSolid
                && block.getRelative(BlockFace.UP).isPassable
    }

    private fun getRandomLocationPlateau(location: Location): Location {
        return location.add(Random().nextDouble(20.0) - 10.0, 0.0, Random().nextDouble(20.0) - 10.0)
    }
}