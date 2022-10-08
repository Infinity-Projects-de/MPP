package de.danielmaile.mpp.aether.world

import de.danielmaile.mpp.aetherWorld
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.aether.world.dungeon.DungeonGenerator
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
import org.bukkit.event.world.WorldSaveEvent
import java.util.*

private val treeList = listOf(PrefabType.TREE1, PrefabType.TREE2, PrefabType.TREE3)

class ListenerAetherWorld : Listener {

    //Save objects during vanilla world save
    @EventHandler
    fun onSave(event: WorldSaveEvent) {
        if (event.world != aetherWorld()) return
        inst().worldManager.objectManager.save()
    }

    //TODO uncomment when new world generation is implemented
    //Teleport players back to the overworld when they fall out of the aether
    /*
    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val playerLoc = player.location
        if (player.world != aetherWorld()) return
        if (playerLoc.y > 64 || playerLoc.y < -256) return

        val destination = Location(Bukkit.getWorlds()[0], playerLoc.x, 400.0, playerLoc.z)
        player.teleport(destination)
        event.isCancelled = true
    }*/

    //Kill all entities that fall out of the aether
    @EventHandler
    fun onEntityMove(event: EntityMoveEvent) {
        val entity: Entity = event.entity
        if (entity.world != aetherWorld()) return
        if (entity.location.y > 64 || entity.location.y < -256) return

        entity.remove()
        event.isCancelled = true
    }

    //Stop water and lava from flowing out of aether
    @EventHandler
    fun onBlockFlow(event: BlockFromToEvent) {
        if (event.block.world != aetherWorld()) return
        if (event.toBlock.y > 64 || event.toBlock.y < -256) return
        event.isCancelled = true
    }

    //Add objects to world when new chunk is loaded
    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        if (!event.isNewChunk) return
        if (event.world != aetherWorld()) return
        val chunk = event.chunk
        val random = Random()

        /*//TODO uncomment when new world generation is implemented
        if (random.nextFloat() < inst().configManager.treeProbability) {
            generateTrees(chunk, random)
        }*/

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