package de.danielmaile.aether.worldgen.dungeon;

import org.bukkit.Location;

import java.util.List;
import java.util.Random;
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

    public Location getMonumentTargetLocation()
    {
        return monumentTargetLocation;
    }

    private final Location monumentTargetLocation;

    Dungeon(List<DungeonPart> parts, Random random)
    {
        this.parts = parts;

        //Try to find valid monument location
        int radius = 20;
        for (DungeonPart endPart : getEndParts())
        {
            for (int x = endPart.getWorldLocation().getBlockX() - radius; x <= endPart.getWorldLocation().getBlockX() + radius; x++)
            {
                for (int z = endPart.getWorldLocation().getBlockZ() - radius; z <= endPart.getWorldLocation().getBlockZ() + radius; z++)
                {
                    int y = endPart.getWorldLocation().getWorld().getHighestBlockYAt(endPart.getWorldLocation()) + 8;

                    //Monument has to be at or above y=0
                    if (y >= 0)
                    {
                        this.monumentLocation = new Location(endPart.getWorldLocation().getWorld(), x, y, z);
                        this.monumentTargetLocation = getEndParts().get(random.nextInt(getEndParts().size()))
                                .getWorldLocation().clone().add(-8, 1, 8);
                        return;
                    }
                }
            }
        }
        this.monumentLocation = this.monumentTargetLocation = null;
    }

    public List<DungeonPart> getEndParts()
    {
        return parts.stream().filter(part -> DungeonPartType.END_PARTS.contains(part.getType())).collect(Collectors.toList());
    }

    public int getSize()
    {
        return parts.size();
    }
}
