package de.danielmaile.aether.worldgen.dungeon;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.bukkit.Location;

public record DungeonPart(DungeonPartType type, Vector2D position, Location worldLocation)
{
    public DungeonPartType getType()
    {
        return type;
    }

    public Vector2D getPosition()
    {
        return position;
    }

    public Location getWorldLocation()
    {
        return worldLocation;
    }
}
