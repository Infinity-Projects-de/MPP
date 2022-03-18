package de.danielmaile.aether.worldgen.dungeon;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public record DungeonPart(DungeonPartType type, Vector2D position)
{
    public DungeonPartType getType()
    {
        return type;
    }

    public Vector2D getPosition()
    {
        return position;
    }
}
