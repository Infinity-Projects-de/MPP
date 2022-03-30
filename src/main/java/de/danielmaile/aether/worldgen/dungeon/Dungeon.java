package de.danielmaile.aether.worldgen.dungeon;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Dungeon
{
    public List<OuterPart> getOuterParts()
    {
        return outerParts;
    }

    private final List<OuterPart> outerParts;

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

    Dungeon(List<OuterPart> outerParts, Random random)
    {
        this.outerParts = outerParts;

        //Try to find valid monument location around outer end parts
        int radius = 20;
        for (OuterPart outerEndPart : getOuterEndParts())
        {
            for (int x = outerEndPart.getWorldLocation().getBlockX() - radius; x <= outerEndPart.getWorldLocation().getBlockX() + radius; x++)
            {
                for (int z = outerEndPart.getWorldLocation().getBlockZ() - radius; z <= outerEndPart.getWorldLocation().getBlockZ() + radius; z++)
                {
                    int y = outerEndPart.getWorldLocation().getWorld().getHighestBlockYAt(outerEndPart.getWorldLocation()) + 8;

                    //Monument has to be at or above y=0
                    if (y > 0)
                    {
                        //Try to find target location in inner end
                        List<InnerPart> possibleTargets = getInnerEndParts();
                        if (possibleTargets.isEmpty())
                        {
                            this.monumentLocation = this.monumentTargetLocation = null;
                        }
                        else
                        {
                            this.monumentLocation = new Location(outerEndPart.getWorldLocation().getWorld(), x, y, z);
                            this.monumentTargetLocation = possibleTargets.get(random.nextInt(possibleTargets.size())).getWorldLocation().clone().add(-8, 1, 8);
                        }
                        return;
                    }
                }
            }
        }
        this.monumentLocation = this.monumentTargetLocation = null;
    }

    private List<OuterPart> getOuterEndParts()
    {
        return outerParts.stream().filter(part -> PartType.END_PART_TYPES.contains(part.getOuterType().getType())).collect(Collectors.toList());
    }

    private List<InnerPart> getInnerEndParts()
    {
        List<InnerPart> possibleEndParts = new ArrayList<>();
        for (OuterPart outerPart : outerParts)
        {
            if (!outerPart.hasInnerParts()) continue;
            possibleEndParts.addAll(outerPart.getInnerParts());
        }
        return possibleEndParts.stream().filter(part -> PartType.END_PART_TYPES.contains(part.getInnerType().getType())).collect(Collectors.toList());
    }

    public int getSize()
    {
        int size = 0;
        for (OuterPart outerPart : outerParts)
        {
            if (outerPart.hasInnerParts())
            {
                size += outerPart.getInnerParts().size();
            }
            else
            {
                size++;
            }
        }
        return size;
    }
}
