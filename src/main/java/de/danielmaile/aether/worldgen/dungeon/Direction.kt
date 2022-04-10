package de.danielmaile.aether.worldgen.dungeon

import org.bukkit.util.Vector

enum class Direction(val relativePos: Vector) {
    EAST(Vector(1, 0, 0)),
    WEST(Vector(-1, 0, 0)),
    SOUTH(Vector(0, 0, 1)),
    NORTH(Vector(0, 0, -1));
}