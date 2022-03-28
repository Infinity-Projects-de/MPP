package de.danielmaile.aether.worldgen.dungeon;

import org.bukkit.Location;
import org.bukkit.util.Vector;

//Don't make class a record to avoid gson error!
public class DungeonPart
{
    private final DungeonPartType type;
    private final Vector relativePosition;
    private final Location worldLocation;

    public DungeonPart(DungeonPartType type, Vector relativePosition, Location worldLocation)
    {
        this.type = type;
        this.relativePosition = relativePosition;
        this.worldLocation = worldLocation;
    }

    public DungeonPartType getType()
    {
        return type;
    }

    public Vector getRelativePosition()
    {
        return relativePosition;
    }

    public Location getWorldLocation()
    {
        return worldLocation;
    }
}
