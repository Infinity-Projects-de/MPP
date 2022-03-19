package de.danielmaile.aether.worldgen.dungeon;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public enum Direction
{
    EAST(new Vector2D(1, 0)),
    WEST(new Vector2D(-1, 0)),
    SOUTH(new Vector2D(0, 1)),
    NORTH(new Vector2D(0, -1));

    public Vector2D getRelativePos()
    {
        return relativePos;
    }

    private final Vector2D relativePos;

    Direction(Vector2D relativePos)
    {
        this.relativePos = relativePos;
    }

    public Direction getOpposite()
    {
        return switch (this)
                {
                    case EAST -> WEST;
                    case WEST -> EAST;
                    case NORTH -> SOUTH;
                    case SOUTH -> NORTH;
                };
    }
}