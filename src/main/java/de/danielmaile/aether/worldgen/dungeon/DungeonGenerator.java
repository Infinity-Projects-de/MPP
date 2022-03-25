package de.danielmaile.aether.worldgen.dungeon;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.util.Vector2I;
import de.danielmaile.aether.worldgen.AetherWorld;
import de.danielmaile.aether.worldgen.Prefab;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DungeonGenerator
{
    public void generateDungeon(Location origin, Random random, float endPartChance)
    {
        Dungeon dungeon = new Dungeon(generateDungeonParts(origin, random, endPartChance), random);

        //If no valid monument location is found dungeon doesn't generate
        if (dungeon.getMonumentLocation() == null) return;

        AetherWorld.addMonument(dungeon.getMonumentLocation(), dungeon.getMonumentTargetLocation());
        Prefab.DUNGEON_MONUMENT.instantiate(dungeon.getMonumentLocation(), true);

        for (DungeonPart part : dungeon.getParts())
        {
            part.getType().getPrefab().instantiate(part.getWorldLocation(), false);
        }

        Aether.logInfo("Generated new dungeon with " + dungeon.getParts().size() + " parts at "
                + origin + " (Monument Location: " + dungeon.getMonumentLocation() + ")");
    }

    private List<DungeonPart> generateDungeonParts(Location origin, Random random, float endPartChance)
    {
        List<DungeonPart> parts = new ArrayList<>();

        //First part TBLR
        List<DungeonPart> newParts = new ArrayList<>();
        newParts.add(new DungeonPart(DungeonPartType.EWSN, new Vector2I(0, 0), origin.clone()));

        //Add parts until no more paths lead to the outside of the dungeon
        while (!newParts.isEmpty())
        {
            parts.addAll(newParts);
            newParts.clear();

            for (DungeonPart part : parts)
            {
                for (Direction connectDirection : part.getType().getConnection().getConnectDirections())
                {
                    Vector2I newPartPos = part.getPosition().add(connectDirection.getRelativePos());
                    if (getPartAt(parts, newPartPos) != null || getPartAt(newParts, newPartPos) != null) continue;

                    //Get neighbours and check for ConnectionState
                    DungeonPart eastNeighbour = getPartAt(parts, newPartPos.add(Direction.EAST.getRelativePos()));
                    DungeonPart westNeighbour = getPartAt(parts, newPartPos.add(Direction.WEST.getRelativePos()));
                    DungeonPart southNeighbour = getPartAt(parts, newPartPos.add(Direction.SOUTH.getRelativePos()));
                    DungeonPart northNeighbour = getPartAt(parts, newPartPos.add(Direction.NORTH.getRelativePos()));

                    Connection newPartCon = new Connection(
                            eastNeighbour != null ? eastNeighbour.getType().getConnection()
                                    .getConnectionState(Direction.WEST) : Connection.ConnectionState.DONT_CARE,
                            westNeighbour != null ? westNeighbour.getType().getConnection()
                                    .getConnectionState(Direction.EAST) : Connection.ConnectionState.DONT_CARE,
                            southNeighbour != null ? southNeighbour.getType().getConnection()
                                    .getConnectionState(Direction.NORTH) : Connection.ConnectionState.DONT_CARE,
                            northNeighbour != null ? northNeighbour.getType().getConnection()
                                    .getConnectionState(Direction.SOUTH) : Connection.ConnectionState.DONT_CARE
                    );

                    newParts.add(getRandomPart(random, endPartChance, newPartCon, newPartPos, origin));
                }
            }
        }
        return parts;
    }

    @Nullable
    private DungeonPart getPartAt(List<DungeonPart> partList, Vector2I position)
    {
        return partList.stream().filter(part -> position.equals(part.getPosition())).findFirst().orElse(null);
    }

    /**
     * @param connection    the connections the new part should have
     * @param position      the position of the new part
     * @param endPartChance chance for the new Part to be an end part
     */
    private DungeonPart getRandomPart(Random random, float endPartChance, Connection connection, Vector2I position, Location origin)
    {
        if (random.nextFloat() <= endPartChance)
        {
            //Set all don't care connections to closed to get end part
            connection = connection.setDontCareToClosed();
        }

        final Connection con = connection;
        List<DungeonPartType> validTypes = Arrays.stream(DungeonPartType.values())
                .filter(type -> con.isValid(type.getConnection()))
                .collect(Collectors.toList());

        return new DungeonPart(validTypes.get(random.nextInt(validTypes.size())), position,
                origin.clone().add(position.getX() * 16, 0, position.getY() * 16));
    }
}
