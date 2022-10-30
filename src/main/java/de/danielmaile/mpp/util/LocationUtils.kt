package de.danielmaile.mpp.util

import net.minecraft.core.BlockPos
import org.bukkit.Location

val Location.blockPos: BlockPos
    get() = BlockPos(blockX, blockY, blockZ)