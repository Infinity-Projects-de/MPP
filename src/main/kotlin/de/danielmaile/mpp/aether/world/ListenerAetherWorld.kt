/*
 * This file is part of MPP.
 * Copyright (c) 2022 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.aether.world

import de.danielmaile.mpp.aether.world.dungeon.DungeonGenerator
import de.danielmaile.mpp.aetherWorld
import de.danielmaile.mpp.inst
import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.world.ChunkLoadEvent
import java.util.*

private val treeList = listOf(PrefabType.TREE1, PrefabType.TREE2, PrefabType.TREE3)

class ListenerAetherWorld : Listener {

    // teleport players back to the overworld when they fall out of the aether
    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val playerLoc = player.location
        if (player.world != aetherWorld()) return
        if (playerLoc.y > 64 || playerLoc.y < -256) return

        val destinationWorld = Bukkit.getWorlds()[0]
        val destination = Location(
            destinationWorld,
            playerLoc.x,
            destinationWorld.getHighestBlockYAt(playerLoc.x.toInt(), playerLoc.z.toInt()).toDouble(),
            playerLoc.z
        )
        player.teleport(destination)
        event.isCancelled = true
    }

    // kill all entities that fall out of the aether
    @EventHandler
    fun onEntityMove(event: EntityMoveEvent) {
        val entity: Entity = event.entity
        if (entity.world != aetherWorld()) return
        if (entity.location.y > 64 || entity.location.y < -256) return

        entity.remove()
        event.isCancelled = true
    }

    // stop water and lava from flowing out of aether
    @EventHandler
    fun onBlockFlow(event: BlockFromToEvent) {
        if (event.block.world != aetherWorld()) return
        if (event.toBlock.y > 64 || event.toBlock.y < -256) return
        event.isCancelled = true
    }

    // add objects to world when new chunk is loaded
    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        if (!event.isNewChunk) return
        if (event.world != aetherWorld()) return
        val chunk = event.chunk
        val random = Random()

        if (random.nextFloat() < inst().configManager.treeProbability) {
            generateTrees(chunk, random)
        }

        if (random.nextFloat() < inst().configManager.dungeonProbability) {
            generateDungeons(chunk, random)
        }
    }

    private fun generateTrees(chunk: Chunk, random: Random) {
        val x = random.nextInt(16) + chunk.x * 16
        val z = random.nextInt(16) + chunk.z * 16
        val y = chunk.world.getHighestBlockYAt(x, z)
        if (y <= 0) return

        val location = Location(chunk.world, x.toDouble(), y.toDouble(), z.toDouble())
        val yRotation = (random.nextInt(4) * 90).toDouble()
        Prefab(treeList[random.nextInt(treeList.size)], location, yRotation, true).instantiate()
    }

    private fun generateDungeons(chunk: Chunk, random: Random) {
        val x = chunk.x * 16
        val z = chunk.z * 16

        // generate dungeon at min y level
        val location = Location(chunk.world, x.toDouble(), (chunk.world.minHeight + 1).toDouble(), z.toDouble())
        DungeonGenerator(random).generateDungeon(
            location, inst().configManager.dungeonEndPartChance, inst().configManager.dungeonPartCap
        )
    }
}