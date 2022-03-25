package de.danielmaile.aether.worldgen.dungeon;

import de.danielmaile.aether.util.Vector2I;

public enum Direction
{
    EAST(new Vector2I(1, 0)),
    WEST(new Vector2I(-1, 0)),
    SOUTH(new Vector2I(0, 1)),
    NORTH(new Vector2I(0, -1));

    public Vector2I getRelativePos()
    {
        return relativePos;
    }

    private final Vector2I relativePos;

    Direction(Vector2I relativePos)
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