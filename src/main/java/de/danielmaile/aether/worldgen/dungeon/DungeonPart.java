package de.danielmaile.aether.worldgen.dungeon;

import de.danielmaile.aether.util.Vector2I;
import org.bukkit.Location;

public record DungeonPart(DungeonPartType type, Vector2I position, Location worldLocation)
{
    public DungeonPartType getType()
    {
        return type;
    }

    public Vector2I getPosition()
    {
        return position;
    }

    public Location getWorldLocation()
    {
        return worldLocation;
    }
}
