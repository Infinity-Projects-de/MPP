package de.danielmaile.aether.worldgen.dungeon;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//Don't make class a record to avoid gson error!
public class OuterPart
{
    private final OuterPartType type;
    private final Vector relativePosition;
    private final Location worldLocation;

    //Outer part has 25 inner parts (or 0)
    private final List<InnerPart> innerParts;

    public OuterPart(OuterPartType type, Vector relativePosition, Location worldLocation, Random random, double endPartChance)
    {
        this.type = type;
        this.relativePosition = relativePosition;
        this.worldLocation = worldLocation;

        innerParts = generateInnerParts(random, endPartChance);
    }

    private @Nullable
    List<InnerPart> generateInnerParts(Random random, double endPartChance)
    {
        if (type.getPrefab() != null) return null;

        //Generate middle cross depending on outer connections
        List<InnerPart> parts = new ArrayList<>();
        List<Direction> outerConnectDirections = this.type.getConnection().getConnectDirections();
        List<InnerPart> newParts = getCross(random, endPartChance, outerConnectDirections);

        //Add parts until no more paths lead to the outside of the dungeon
        while (!newParts.isEmpty())
        {
            parts.addAll(newParts);
            newParts.clear();

            for (InnerPart innerPart : parts)
            {
                for (Direction connectDirection : innerPart.getInnerType().getConnection().getConnectDirections())
                {
                    Vector newPartPos = innerPart.getRelativePosition().clone().add(connectDirection.getRelativePos());
                    if (getPartAt(parts, newPartPos) != null || getPartAt(newParts, newPartPos) != null) continue;

                    //Get neighbours and check for ConnectionState
                    InnerPart eastNeighbour = getNeighbour(newPartPos, Direction.EAST, parts, newParts);
                    InnerPart westNeighbour = getNeighbour(newPartPos, Direction.WEST, parts, newParts);
                    InnerPart southNeighbour = getNeighbour(newPartPos, Direction.SOUTH, parts, newParts);
                    InnerPart northNeighbour = getNeighbour(newPartPos, Direction.NORTH, parts, newParts);

                    Connection newPartCon = new Connection(
                            eastNeighbour != null ? eastNeighbour.getInnerType().getConnection()
                                    .getConnectionState(Direction.WEST) : Connection.ConnectionState.DONT_CARE,
                            westNeighbour != null ? westNeighbour.getInnerType().getConnection()
                                    .getConnectionState(Direction.EAST) : Connection.ConnectionState.DONT_CARE,
                            southNeighbour != null ? southNeighbour.getInnerType().getConnection()
                                    .getConnectionState(Direction.NORTH) : Connection.ConnectionState.DONT_CARE,
                            northNeighbour != null ? northNeighbour.getInnerType().getConnection()
                                    .getConnectionState(Direction.SOUTH) : Connection.ConnectionState.DONT_CARE
                    );

                    newParts.add(getRandomInnerPart(random, endPartChance, newPartCon, newPartPos));
                }
            }
        }

        return parts;
    }

    //Try to find neighbour part in two lists. If none is found return null
    private @Nullable
    InnerPart getNeighbour(Vector location, Direction direction, List<InnerPart> partList1, List<InnerPart> partList2)
    {
        InnerPart neighbour = getPartAt(partList1, location.clone().add(direction.getRelativePos()));
        return neighbour != null ? neighbour : getPartAt(partList2, location.clone().add(direction.getRelativePos()));
    }

    @Nullable
    private InnerPart getPartAt(List<InnerPart> partList, Vector relativePosition)
    {
        //If relative position is out of bounds, return closed part
        if (relativePosition.getX() < 0 || relativePosition.getX() > 4 ||
                relativePosition.getZ() < 0 || relativePosition.getZ() > 4)
        {
            return new InnerPart(InnerPartType.CLOSED, relativePosition, this);
        }

        //Else try to find part in list. If no part is found return null
        return partList.stream().filter(outerPart -> relativePosition.equals(outerPart.getRelativePosition())).findFirst().orElse(null);
    }

    private List<InnerPart> getCross(Random random, double endPartChance, List<Direction> outerConnectDirections)
    {
        List<InnerPart> parts = new ArrayList<>();

        //Center part (2,2)
        parts.add(new InnerPart(InnerPartType.EWSN, new Vector(2, 0, 2), this));
        //East center part (4, 2)
        parts.add(getRandomInnerPart(random, endPartChance,
                new Connection(outerConnectDirections.contains(Direction.EAST) ? Connection.ConnectionState.CONNECTED : Connection.ConnectionState.CLOSED,
                        Connection.ConnectionState.CONNECTED, Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE),
                new Vector(4, 0, 2)));
        //West center part (0, 2)
        parts.add(getRandomInnerPart(random, endPartChance,
                new Connection(Connection.ConnectionState.CONNECTED,
                        outerConnectDirections.contains(Direction.WEST) ? Connection.ConnectionState.CONNECTED : Connection.ConnectionState.CLOSED,
                        Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE),
                new Vector(0, 0, 2)));
        //South center part (2, 4)
        parts.add(getRandomInnerPart(random, endPartChance,
                new Connection(Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE,
                        outerConnectDirections.contains(Direction.SOUTH) ? Connection.ConnectionState.CONNECTED : Connection.ConnectionState.CLOSED,
                        Connection.ConnectionState.CONNECTED),
                new Vector(2, 0, 4)));
        //North center part (2, 0)
        parts.add(getRandomInnerPart(random, endPartChance,
                new Connection(Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.CONNECTED,
                        outerConnectDirections.contains(Direction.NORTH) ? Connection.ConnectionState.CONNECTED : Connection.ConnectionState.CLOSED),
                new Vector(2, 0, 0)));
        //East inner center part (3, 2)
        parts.add(getRandomInnerPart(random, endPartChance,
                new Connection(Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED,
                        Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE),
                new Vector(3, 0, 2)));
        //West inner center part (1, 2)
        parts.add(getRandomInnerPart(random, endPartChance,
                new Connection(Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED,
                        Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE),
                new Vector(1, 0, 2)));
        //South inner center part (2, 3)
        parts.add(getRandomInnerPart(random, endPartChance,
                new Connection(Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE,
                        Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED),
                new Vector(2, 0, 3)));
        //North inner center part (2, 1)
        parts.add(getRandomInnerPart(random, endPartChance,
                new Connection(Connection.ConnectionState.DONT_CARE, Connection.ConnectionState.DONT_CARE,
                        Connection.ConnectionState.CONNECTED, Connection.ConnectionState.CONNECTED),
                new Vector(2, 0, 1)));

        return parts;
    }

    private InnerPart getRandomInnerPart(Random random, double endPartChance, Connection connection, Vector relativePosition)
    {
        if (random.nextFloat() < endPartChance)
        {
            //Set all don't care connections to closed to get end part
            connection = connection.setDontCareToClosed();
        }

        Connection finalConnection = connection;
        List<InnerPartType> validTypes = Arrays.stream(InnerPartType.values())
                .filter(type -> finalConnection.isValid(type.getConnection()))
                .collect(Collectors.toList());
        return new InnerPart(validTypes.get(random.nextInt(validTypes.size())), relativePosition, this);
    }

    public OuterPartType getOuterType()
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

    public List<InnerPart> getInnerParts()
    {
        return innerParts;
    }

    public boolean hasInnerParts()
    {
        return innerParts != null;
    }
}
