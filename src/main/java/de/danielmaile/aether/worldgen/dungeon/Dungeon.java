package de.danielmaile.aether.worldgen.dungeon;

import org.bukkit.Location;

import java.util.List;
import java.util.stream.Collectors;

public class Dungeon
{
    public List<DungeonPart> getParts()
    {
        return parts;
    }

    private final List<DungeonPart> parts;

    public Location getMonumentLocation()
    {
        return monumentLocation;
    }

    private final Location monumentLocation;

    Dungeon(List<DungeonPart> parts)
    {
        this.parts = parts;

        //Try to find valid monument location
        int radius = 20;
        for(DungeonPart endPart : getEndParts())
        {
            for(int x = endPart.getWorldLocation().getBlockX() - radius; x <= endPart.getWorldLocation().getBlockX() + radius; x++)
            {
                for(int z = endPart.getWorldLocation().getBlockZ() - radius; z <= endPart.getWorldLocation().getBlockZ() + radius; z++)
                {
                    int y = endPart.getWorldLocation().getWorld().getHighestBlockYAt(endPart.getWorldLocation());

                    //Monument has to be at least 16 blocks above dungeon
                    if(y >= endPart.getWorldLocation().getY() + 16)
                    {
                        this.monumentLocation = new Location(endPart.getWorldLocation().getWorld(), x, y, z);
                        return;
                    }
                }
            }
        }
        this.monumentLocation = null;
    }

    public List<DungeonPart> getEndParts()
    {
        return parts.stream().filter(part -> DungeonPartType.END_PARTS.contains(part.getType())).collect(Collectors.toList());
    }
}
