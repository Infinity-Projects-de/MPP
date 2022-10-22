package de.danielmaile.mpp.aether.world.portal

import de.danielmaile.mpp.aetherWorld
import de.danielmaile.mpp.aether.world.portal.AetherPortal.checkPortal
import de.danielmaile.mpp.aether.world.portal.AetherPortal.createPortal
import de.danielmaile.mpp.aether.world.portal.AetherPortal.findPortalInRadius
import de.danielmaile.mpp.aether.world.portal.AetherPortal.removePortal
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.Dispenser
import org.bukkit.block.data.Directional
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.inventory.ItemStack

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
    fun onWaterDispense(event: BlockDispenseEvent) {
        if (event.item.type != Material.WATER_BUCKET) return

        val directional = event.block.blockData as Directional
        if (!checkPortal(event.block.getRelative(directional.facing).location, false)) return

        event.isCancelled = true

        val dispenser = event.block.state as Dispenser
        for (i in IntRange(0, dispenser.inventory.size - 1)) {
            if (dispenser.inventory.getItem(i) != null) {
                if (dispenser.inventory.getItem(i)!!.type == Material.WATER_BUCKET) {
                    dispenser.inventory.setItem(i, ItemStack(Material.BUCKET))
                    break
                }
            }
        }
    }

    // If a player breaks the glowstone blocks, the portal (inside) gets destroyed
    // If a player tries to break the inside blocks the event will get cancelled

    @EventHandler
    fun onBlockExplode(event: BlockExplodeEvent) {
        for (block in event.blockList()) {
            removePortalIfPresent(block)
            event.isCancelled = isInnerPortalBlock(block)
        }

    }

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        for (block in event.blockList()) {
            removePortalIfPresent(block)
            event.isCancelled = isInnerPortalBlock(block)
        }
    }

    @EventHandler
    fun onBlockPistonExtend(event: BlockPistonExtendEvent) {
        for (block in event.blocks) {
            removePortalIfPresent(block)
            event.isCancelled = isInnerPortalBlock(block)
        }
    }

    @EventHandler
    fun onBlockPistonRetract(event: BlockPistonRetractEvent) {
        for (block in event.blocks) {
            removePortalIfPresent(block)
            event.isCancelled = isInnerPortalBlock(block)
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        removePortalIfPresent(event.block)
        event.isCancelled = isInnerPortalBlock(event.block)
    }

    private fun removePortalIfPresent(block: Block) {
        if (block.type != Material.GLOWSTONE) return

        val locations = HashMap<Location, BlockFace>() // multiple portals can be present
        val blockFaces = arrayOf(
            BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST
        )
        for (blockFace in blockFaces) {
            if (block.getRelative(blockFace).type == Material.BLUE_STAINED_GLASS_PANE) {
                var blockIterating = block.getRelative(blockFace)
                for (i in 0..2) {
                    blockIterating = blockIterating.getRelative(BlockFace.DOWN)
                    if (blockIterating.type != Material.BLUE_STAINED_GLASS_PANE) {
                        if (blockFace == BlockFace.DOWN) {
                            val sideFaces = arrayOf(
                                BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST
                            )
                            val blockBelow = block.getRelative(BlockFace.DOWN)
                            for (face in sideFaces) if (blockBelow.getRelative(face).type == Material.BLUE_STAINED_GLASS_PANE) {
                                locations[blockIterating.getRelative(BlockFace.UP).location] = face
                                break
                            }
                        } else {
                            locations[blockIterating.getRelative(BlockFace.UP).location] = blockFace
                        }
                        break
                    }
                }
            }
        }

        for (location in locations)
            if (checkPortal(location.key, true, location.value))
                removePortal(location.key)
    }

    private fun isInnerPortalBlock(block: Block): Boolean {
        if (block.type != Material.BLUE_STAINED_GLASS_PANE) return false
        return checkPortal(block.location, true)
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
            //Limit minimum y to 130 to prevent the portal from spawning at the bottom of the world if there is no ground block
            toLocation.y = (toLocation.world.getHighestBlockYAt(toLocation) + 2).toDouble().coerceAtLeast(130.0)
            createPortal(toLocation)
            event.player.teleport(toLocation.add(0.0, 4.0, 0.0))
        }
    }
}