package de.danielmaile.aether.worldgen.dungeon;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.worldgen.AetherWorld;
import de.danielmaile.aether.worldgen.Prefab;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DungeonGenerator
{
    public void generateDungeon(Location origin, Random random, float endPartChance, int partCap)
    {
        Dungeon dungeon = new Dungeon(generateOuterParts(origin, random, endPartChance, partCap), random);

        //If no valid monument location is found dungeon doesn't generate
        if (dungeon.getMonumentLocation() == null)
        {
            return;
        }

        AetherWorld.getObjectManager().getDungeonList().add(dungeon);
        Prefab.DUNGEON_MONUMENT.instantiate(dungeon.getMonumentLocation(), true);

        for (OuterPart part : dungeon.getOuterParts())
        {
            if (part.hasInnerParts())
            {
                for (InnerPart innerPart : part.getInnerParts())
                {
                    innerPart.getInnerType().getPrefab().instantiate(innerPart.getWorldLocation(), false);
                }
            }
            else
            {
                part.getOuterType().getPrefab().instantiate(part.getWorldLocation(), false);
            }
        }

        Aether.logInfo("Generated new dungeon with " + dungeon.getSize() + " inner parts at "
                + origin + " (Monument Location: " + dungeon.getMonumentLocation() + ")");
    }

    private List<OuterPart> generateOuterParts(Location origin, Random random, float endPartChance, int partCap)
    {
        List<OuterPart> outerParts = new ArrayList<>();

        //First part TBLR
        List<OuterPart> newParts = new ArrayList<>();
        newParts.add(new OuterPart(OuterPartType.EWSN, new Vector(0, 0, 0), origin.clone(), random, endPartChance));

        //Add parts until no more paths lead to the outside of the dungeon
        while (!newParts.isEmpty())
        {
            outerParts.addAll(newParts);
            newParts.clear();

            for (OuterPart outerPart : outerParts)
            {
                if (outerParts.size() + newParts.size() > partCap)
                {
                    endPartChance = 0;
                }

                for (Direction connectDirection : outerPart.getOuterType().getConnection().getConnectDirections())
                {
                    Vector newPartPos = outerPart.getRelativePosition().clone().add(connectDirection.getRelativePos());
                    if (getPartAt(outerParts, newPartPos) != null || getPartAt(newParts, newPartPos) != null) continue;

                    //Get neighbours and check for ConnectionState
                    OuterPart eastNeighbour = getNeighbour(newPartPos, Direction.EAST, outerParts);
                    OuterPart westNeighbour = getNeighbour(newPartPos, Direction.WEST, outerParts);
                    OuterPart southNeighbour = getNeighbour(newPartPos, Direction.SOUTH, outerParts);
                    OuterPart northNeighbour = getNeighbour(newPartPos, Direction.NORTH, outerParts);

                    Connection newPartCon = new Connection(
                            eastNeighbour != null ? eastNeighbour.getOuterType().getConnection()
                                    .getConnectionState(Direction.WEST) : Connection.ConnectionState.DONT_CARE,
                            westNeighbour != null ? westNeighbour.getOuterType().getConnection()
                                    .getConnectionState(Direction.EAST) : Connection.ConnectionState.DONT_CARE,
                            southNeighbour != null ? southNeighbour.getOuterType().getConnection()
                                    .getConnectionState(Direction.NORTH) : Connection.ConnectionState.DONT_CARE,
                            northNeighbour != null ? northNeighbour.getOuterType().getConnection()
                                    .getConnectionState(Direction.SOUTH) : Connection.ConnectionState.DONT_CARE
                    );

                    newParts.add(getRandomOuterPart(random, endPartChance, newPartCon, newPartPos, origin));
                }
            }
        }
        return outerParts;
    }

    //Try to find neighbour part in list. If none is found return null
    private @Nullable
    OuterPart getNeighbour(Vector location, Direction direction, List<OuterPart> partList1)
    {
        return getPartAt(partList1, location.clone().add(direction.getRelativePos()));
    }

    @Nullable
    private OuterPart getPartAt(List<OuterPart> partList, Vector relativePosition)
    {
        return partList.stream().filter(outerPart -> relativePosition.equals(outerPart.getRelativePosition())).findFirst().orElse(null);
    }

    /**
     * @param connection       the connections the new part should have
     * @param relativePosition the position of the new part
     * @param endPartChance    chance for the new Part to be an end part
     */
    private OuterPart getRandomOuterPart(Random random, float endPartChance, Connection connection, Vector relativePosition, Location origin)
    {
        if (random.nextFloat() < endPartChance)
        {
            //Set all don't care connections to closed to get end part
            connection = connection.setDontCareToClosed();
        }

        final Connection con = connection;
        List<OuterPartType> validTypes = Arrays.stream(OuterPartType.values())
                .filter(type -> con.isValid(type.getConnection()))
                .collect(Collectors.toList());

        return new OuterPart(validTypes.get(random.nextInt(validTypes.size())), relativePosition,
                origin.clone().add(relativePosition.getX() * 16 * 5, 0, relativePosition.getZ() * 16 * 5), random, endPartChance);
    }
}
