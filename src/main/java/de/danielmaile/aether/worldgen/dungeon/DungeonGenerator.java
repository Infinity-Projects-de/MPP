package de.danielmaile.aether.worldgen.dungeon;

import de.danielmaile.aether.Aether;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonGenerator
{
    public enum Opening
    {
        TOP, BOTTOM, LEFT, RIGHT
    }

    private final List<DungeonPart> parts = new ArrayList<>();

    public void generateDungeon(Random random)
    {
        Aether.logInfo("Start dungeon generation");

        //First part TBLR
        parts.add(new DungeonPart(DungeonPartType.TBLR, new Vector2D(0, 0)));

        //Add parts until no more can be added

        boolean finished = false;
        while (!finished)
        {
            finished = true;
            List<DungeonPart> toAdd = new ArrayList<>();

            for (DungeonPart part : parts)
            {
                //Add top part
                if (part.getType().canConnectTop())
                {
                    Vector2D newPartPos = part.getPosition().add(new Vector2D(0, 1));
                    //Check if there is already a part
                    if (getPartAt(newPartPos) == null)
                    {
                        toAdd.add(getRandomPartWithOpening(random, Opening.BOTTOM, newPartPos));
                        finished = false;
                    }
                }

                //Add bottom part
                if (part.getType().canConnectBottom())
                {
                    Vector2D newPartPos = part.getPosition().add(new Vector2D(0, -1));
                    //Check if there is already a part
                    if (getPartAt(newPartPos) == null)
                    {
                        toAdd.add(getRandomPartWithOpening(random, Opening.TOP, newPartPos));
                        finished = false;
                    }
                }

                //Add left part
                if (part.getType().canConnectLeft())
                {
                    Vector2D newPartPos = part.getPosition().add(new Vector2D(-1, 0));
                    //Check if there is already a part
                    if (getPartAt(newPartPos) == null)
                    {
                        toAdd.add(getRandomPartWithOpening(random, Opening.RIGHT, newPartPos));
                        finished = false;
                    }
                }

                //Add right part
                if (part.getType().canConnectRight())
                {
                    Vector2D newPartPos = part.getPosition().add(new Vector2D(1, 0));
                    //Check if there is already a part
                    if (getPartAt(newPartPos) == null)
                    {
                        toAdd.add(getRandomPartWithOpening(random, Opening.LEFT, newPartPos));
                        finished = false;
                    }
                }
            }

            parts.addAll(toAdd);
        }

        Aether.logInfo("Finished dungeon generation:");

        for(DungeonPart part : parts)
        {
            Aether.logInfo("Part: " + part.getType().toString() + " at: " + part.getPosition().toString());
        }
    }

    @Nullable
    private DungeonPart getPartAt(Vector2D position)
    {
        return parts.stream().filter(part -> position.equals(part.getPosition())).findFirst().orElse(null);
    }

    private DungeonPart getRandomPartWithOpening(Random random, Opening opening, Vector2D position)
    {
        List<DungeonPartType> validTypes = new ArrayList<>();

        for (DungeonPartType type : DungeonPartType.values())
        {
            if (type.canConnectTop() && opening == Opening.TOP)
            {
                //5% Chance for end part
                if(random.nextFloat() > 0.95f)
                {
                    return new DungeonPart(DungeonPartType.T, position);
                }
                validTypes.add(type);
            }

            if (type.canConnectBottom() && opening == Opening.BOTTOM)
            {
                //5% Chance for end part
                if(random.nextFloat() > 0.95f)
                {
                    return new DungeonPart(DungeonPartType.B, position);
                }
                validTypes.add(type);
            }

            if (type.canConnectLeft() && opening == Opening.LEFT)
            {
                //5% Chance for end part
                if(random.nextFloat() > 0.95f)
                {
                    return new DungeonPart(DungeonPartType.L, position);
                }
                validTypes.add(type);
            }

            if (type.canConnectRight() && opening == Opening.RIGHT)
            {
                //5% Chance for end part
                if(random.nextFloat() > 0.95f)
                {
                    return new DungeonPart(DungeonPartType.R, position);
                }
                validTypes.add(type);
            }
        }

        return new DungeonPart(validTypes.get(random.nextInt(validTypes.size())), position);
    }
}
