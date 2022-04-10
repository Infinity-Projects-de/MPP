package de.danielmaile.aether.worldgen

import de.danielmaile.aether.inst
import de.danielmaile.aether.worldgen.dungeon.DungeonGenerator
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import java.util.*

private val treeList = listOf(PrefabType.TREE1, PrefabType.TREE2, PrefabType.TREE3)

class ListenerWorldGeneration : Listener {

    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        if (!event.isNewChunk) return
        if (event.world != inst().aetherWorld.getWorld()) return
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

        //Generate dungeon at min y level
        val location = Location(chunk.world, x.toDouble(), (chunk.world.minHeight + 1).toDouble(), z.toDouble())
        DungeonGenerator(random).generateDungeon(
            location, inst().configManager.dungeonEndPartChance, inst().configManager.dungeonPartCap
        )
    }
}