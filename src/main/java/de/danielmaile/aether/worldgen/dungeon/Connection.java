package de.danielmaile.aether.worldgen.dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Connection(ConnectionState eastState, ConnectionState westState, ConnectionState southState,
                         ConnectionState northState)
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

    public Connection setDontCareToClosed()
    {
        return new Connection(
                eastState == ConnectionState.DONT_CARE ? ConnectionState.CLOSED : eastState,
                westState == ConnectionState.DONT_CARE ? ConnectionState.CLOSED : westState,
                southState == ConnectionState.DONT_CARE ? ConnectionState.CLOSED : southState,
                northState == ConnectionState.DONT_CARE ? ConnectionState.CLOSED : northState
        );
    }

    /**
     * Check if other connection matches this connection.
     * Don't cares of this connection get ignored
     */
    public boolean isValid(Connection other)
    {
        if (this.eastState != ConnectionState.DONT_CARE && this.eastState != other.eastState) return false;
        if (this.westState != ConnectionState.DONT_CARE && this.westState != other.westState) return false;
        if (this.southState != ConnectionState.DONT_CARE && this.southState != other.southState) return false;
        return this.northState == ConnectionState.DONT_CARE || this.northState == other.northState;
    }

    public List<Direction> getConnectDirections()
    {
        List<Direction> connectDirections = new ArrayList<>();
        if (eastState == ConnectionState.CONNECTED) connectDirections.add(Direction.EAST);
        if (westState == ConnectionState.CONNECTED) connectDirections.add(Direction.WEST);
        if (southState == ConnectionState.CONNECTED) connectDirections.add(Direction.SOUTH);
        if (northState == ConnectionState.CONNECTED) connectDirections.add(Direction.NORTH);
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
