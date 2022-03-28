package de.danielmaile.aether.worldgen.dungeon;

import org.bukkit.util.Vector;

public enum Direction
{
    EAST(new Vector(1, 0, 0)),
    WEST(new Vector(-1, 0, 0)),
    SOUTH(new Vector(0, 0, 1)),
    NORTH(new Vector(0, 0, -1));

    public Vector getRelativePos()
    {
        return relativePos;
    }

    private final Vector relativePos;

    Direction(Vector relativePos)
    {
        this.relativePos = relativePos;
    }
}