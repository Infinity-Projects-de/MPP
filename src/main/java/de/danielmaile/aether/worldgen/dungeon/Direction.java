package de.danielmaile.aether.worldgen.dungeon;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public enum Direction
{
    TOP(new Vector2D(0, 1)),
    BOTTOM(new Vector2D(0, -1)),
    LEFT(new Vector2D(-1, 0)),
    RIGHT(new Vector2D(1, 0));

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
                    case TOP -> BOTTOM;
                    case BOTTOM -> TOP;
                    case LEFT -> RIGHT;
                    case RIGHT -> LEFT;
                };
    }
}