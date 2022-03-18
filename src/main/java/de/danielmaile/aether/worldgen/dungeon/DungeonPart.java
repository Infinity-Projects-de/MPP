package de.danielmaile.aether.worldgen.dungeon;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class DungeonPart
{
    public DungeonPartType getType()
    {
        return type;
    }

    private final DungeonPartType type;

    public Vector2D getPosition()
    {
        return position;
    }

    private final Vector2D position;

    public DungeonPart(DungeonPartType type, Vector2D position)
    {
        this.type = type;
        this.position = position;
    }
}
