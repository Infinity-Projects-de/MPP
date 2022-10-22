package de.danielmaile.mpp.aether.world.portal

import de.danielmaile.mpp.aether.world.Prefab
import de.danielmaile.mpp.aether.world.PrefabType
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.GlassPane

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
            if(checkPortal(location, filled, direction)) {
                return true
            }
        }
        return false
    }

    /**
     * Checks for Portal in all different directions and lights it if one is found
     *
     * @param location location to check (location of the block above the portal bottom
     * @param filled   true: Checks for filled Portal, false: Checks for empty Portal and lights it if it found one
     * @return true if a portal was found
     */
    @JvmStatic
    fun checkPortal(location: Location, filled: Boolean, direction: BlockFace): Boolean {
        var block = location.block
        for (i in 0..2) {
            block = block.getRelative(BlockFace.DOWN)
            if (filled && checkPortal(block, direction, true)) {
                return true
            } else if (!filled && checkPortal(block, direction, false)) {
                alterPortal(block, direction, true)
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun removePortal(location: Location) {
        val directions = arrayOf(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST)
        for (direction in directions) {
            var block = location.block
            for (i in 0..2) {
                block = block.getRelative(BlockFace.DOWN)
                if (checkPortal(block, direction, true)) {
                    alterPortal(block, direction, false)
                    return
                }
            }
        }
    }

    /**
     * @param bottomBlock bottom block of the portal frame
     * @param direction   direction of an adjacent gateway block
     * @param place       whether to place or to remove gateway blocks
     */
    private fun alterPortal(bottomBlock: Block, direction: BlockFace, place: Boolean) {
        val directions = arrayOf(
            BlockFace.UP, BlockFace.UP, BlockFace.UP,
            direction, BlockFace.DOWN, BlockFace.DOWN
        )

        var block = bottomBlock
        for (blockFace in directions) {
            block = block.getRelative(blockFace)

            if (place) {
                block.type = Material.BLUE_STAINED_GLASS_PANE

                //Check for neighbors and connect the glass panes
                val blockData = block.blockData as GlassPane
                val neighbors = arrayOf(BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST)

                for(neighbor in neighbors) {
                    if(block.getRelative(neighbor).type == Material.GLOWSTONE) {
                        blockData.setFace(neighbor, true)
                    }
                }
                block.blockData = blockData
            } else {
                block.type = Material.AIR
            }
        }
    }

    /**
     * Checks for a glowstone portal at given location
     *
     * @param bottomBlock bottom inside block of the portal
     * @param direction   direction in which the portal is located
     * @param filled      true if the portal should be filled with gateways, false if it should be air
     * @return true if portal was found
     */
    private fun checkPortal(bottomBlock: Block, direction: BlockFace, filled: Boolean): Boolean {
        //Check bottom block
        if (bottomBlock.type != Material.GLOWSTONE) return false
        //Check portal frame
        var block = bottomBlock
        //Not very elegant but the corners should not be required for the portal
        val blockDirections = arrayOf(
            "dir", "dirup",
            "UP", "UP", "upopp",
            "opp", "downopp",
            "DOWN", "DOWN"
        )
        for (blockFace in blockDirections) {
            block = when (blockFace) {
                "dir" -> block.getRelative(direction)
                "opp" -> block.getRelative(direction.oppositeFace)
                "dirup" -> block.getRelative(direction).getRelative(BlockFace.UP)
                "upopp" -> block.getRelative(BlockFace.UP).getRelative(direction.oppositeFace)
                "downopp" -> block.getRelative(BlockFace.DOWN).getRelative(direction.oppositeFace)
                else -> block.getRelative(BlockFace.valueOf(blockFace))
            }

            if (block.type != Material.GLOWSTONE)
                return false
        }

        //Check for blocks inside portal
        val toCheck = if (filled) Material.BLUE_STAINED_GLASS_PANE else Material.AIR
        val filledDirections = arrayOf(
            BlockFace.UP, BlockFace.UP, BlockFace.UP,
            direction, BlockFace.DOWN, BlockFace.DOWN
        )
        block = bottomBlock

        for (blockFace in filledDirections) {
            block = block.getRelative(blockFace)
            if (block.type != toCheck && block.type != Material.WATER) return false
        }
        return true
    }
}