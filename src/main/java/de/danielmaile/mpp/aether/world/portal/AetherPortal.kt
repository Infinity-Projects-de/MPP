package de.danielmaile.mpp.aether.world.portal

import de.danielmaile.mpp.aether.world.Prefab
import de.danielmaile.mpp.aether.world.PrefabType
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

object AetherPortal {

    @JvmStatic
    fun createPortal(location: Location) {
        Prefab(PrefabType.PORTAL, location, false).instantiate()
    }

    @JvmStatic
    fun findPortalInRadius(location: Location, radius: Int): Location? {
        for (y in location.world.maxHeight - 1 downTo location.world.minHeight + 1) {
            for (x in location.blockX - radius..location.blockX + radius) {
                for (z in location.blockZ - radius..location.blockZ + radius) {
                    val portalLocation = Location(location.world, x.toDouble(), y.toDouble(), z.toDouble())
                    if (location.world.getBlockAt(portalLocation).type == Material.GLOWSTONE) {
                        if (checkPortal(portalLocation.clone().add(0.0, -1.0, 0.0), true)) {
                            return portalLocation
                        }
                    }
                }
            }
        }
        return null
    }

    /**
     * Checks for Portal in all different directions and lights it if one is found
     *
     * @param location location to check (location of the block above the portal bottom
     * @param filled   true: Checks for filled Portal, false: Checks for empty Portal and lights it if it found one
     * @return true if a portal was found
     */
    @JvmStatic
    fun checkPortal(location: Location, filled: Boolean): Boolean {
        val directions = arrayOf(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST)
        for (direction in directions) {
            var block = location.block
            for (i in 0..2) {
                block = block.getRelative(BlockFace.DOWN)
                if (filled && checkPortal(block, direction, true)) {
                    return true
                } else if (!filled && checkPortal(block, direction, false)) {
                    lightPortal(block, direction)
                    return true
                }
            }
        }
        return false
    }

    private fun lightPortal(bottomBlock: Block, direction: BlockFace) {
        val directions = arrayOf(
            BlockFace.UP, BlockFace.UP, BlockFace.UP,
            direction, BlockFace.DOWN, BlockFace.DOWN
        )
        var block = bottomBlock

        for (blockFace in directions) {
            block = block.getRelative(blockFace)
            block.type = Material.END_GATEWAY
        }
    }

    /**
     * Checks for a glowstone portal at given location
     *
     * @param bottomBlock bottom inside block of the portal
     * @param direction   direction in with the portal is located
     * @param filled      true if the portal should be filled with stained-glass, false if it should be air
     * @return true if portal was found
     */
    private fun checkPortal(bottomBlock: Block, direction: BlockFace, filled: Boolean): Boolean {
        //Check bottom block
        if (bottomBlock.type != Material.GLOWSTONE) return false

        //Check portal frame
        val blockDirections = arrayOf(
            direction, direction,
            BlockFace.UP, BlockFace.UP, BlockFace.UP, BlockFace.UP,
            direction.oppositeFace, direction.oppositeFace, direction.oppositeFace,
            BlockFace.DOWN, BlockFace.DOWN, BlockFace.DOWN, BlockFace.DOWN
        )
        var block = bottomBlock

        for (blockFace in blockDirections) {
            block = block.getRelative(blockFace)
            if (block.type != Material.GLOWSTONE) return false
        }

        //Check for blocks inside portal
        val toCheck = if (filled) Material.END_GATEWAY else Material.AIR
        val filledDirections = arrayOf(
            BlockFace.UP, BlockFace.UP, BlockFace.UP,
            direction, BlockFace.DOWN, BlockFace.DOWN
        )
        block = bottomBlock

        for (blockFace in filledDirections) {
            block = block.getRelative(blockFace)
            if (block.type != toCheck) return false
        }
        return true
    }
}