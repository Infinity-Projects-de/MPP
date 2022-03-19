package de.danielmaile.aether.worldgen.dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Connection(de.danielmaile.aether.worldgen.dungeon.Connection.ConnectionState eastState,
                         de.danielmaile.aether.worldgen.dungeon.Connection.ConnectionState westState,
                         de.danielmaile.aether.worldgen.dungeon.Connection.ConnectionState southState,
                         de.danielmaile.aether.worldgen.dungeon.Connection.ConnectionState northState)
{
    public enum ConnectionState
    {
        CONNECTED, //Side should be connected
        CLOSED, //Side shouldn't be connected
        DONT_CARE //Don't care if it's connected or not
    }

    public ConnectionState getConnectionState(Direction direction)
    {
        return switch (direction)
                {
                    case EAST -> eastState;
                    case WEST -> westState;
                    case SOUTH -> southState;
                    case NORTH -> northState;
                };
    }

    public List<Direction> getConnectDirections()
    {
        List<Direction> connectDirections = new ArrayList<>();
        if(eastState == ConnectionState.CONNECTED) connectDirections.add(Direction.EAST);
        if(westState == ConnectionState.CONNECTED) connectDirections.add(Direction.WEST);
        if(southState == ConnectionState.CONNECTED) connectDirections.add(Direction.SOUTH);
        if(northState == ConnectionState.CONNECTED) connectDirections.add(Direction.NORTH);
        return connectDirections;
    }

    public static Connection fromDirections(Direction... directions)
    {
        List<Direction> list = Arrays.asList(directions);
        return new Connection(
                list.contains(Direction.EAST) ? ConnectionState.CONNECTED : ConnectionState.CLOSED,
                list.contains(Direction.WEST) ? ConnectionState.CONNECTED : ConnectionState.CLOSED,
                list.contains(Direction.SOUTH) ? ConnectionState.CONNECTED : ConnectionState.CLOSED,
                list.contains(Direction.NORTH) ? ConnectionState.CONNECTED : ConnectionState.CLOSED
        );
    }

}
