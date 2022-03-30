package de.danielmaile.aether.worldgen.dungeon;

import org.bukkit.Location;
import org.bukkit.util.Vector;

//Don't make class a record to avoid gson error!
public class InnerPart
{
    private final InnerPartType type;
    private final Vector relativePosition;
    private final Location worldLocation;

    public InnerPart(InnerPartType type, Vector relativePosition, OuterPart outerPart)
    {
        this.type = type;
        this.relativePosition = relativePosition;
        this.worldLocation = outerPart.getWorldLocation().clone().add(relativePosition.getX() * 16, 0, relativePosition.getZ() * 16);
    }

    public InnerPartType getInnerType()
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
