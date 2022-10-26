package de.danielmaile.mpp.util

import net.minecraft.core.BlockPos
import org.bukkit.Location
import org.bukkit.entity.Player

fun Location.getPlayersNearby(maxDistance: Double): Sequence<Player> {
    val maxDistanceSquared = maxDistance * maxDistance

    return world!!.players
        .asSequence()
        .filter { distanceSquared(it.location) <= maxDistanceSquared }
}

val Location.blockPos: BlockPos
    get() = BlockPos(blockX, blockY, blockZ)