package de.danielmaile.aether.worldgen.dungeon;

import de.danielmaile.aether.worldgen.Prefab;

import java.util.Arrays;
import java.util.List;

public enum DungeonPartType
{
    S(Prefab.DUNGEON_S, Connection.fromDirections(Direction.SOUTH)),
    N(Prefab.DUNGEON_N, Connection.fromDirections(Direction.NORTH)),
    W(Prefab.DUNGEON_W, Connection.fromDirections(Direction.WEST)),
    E(Prefab.DUNGEON_E, Connection.fromDirections(Direction.EAST)),

    SN(Prefab.DUNGEON_SN, Connection.fromDirections(Direction.SOUTH, Direction.NORTH)),
    WS(Prefab.DUNGEON_WS, Connection.fromDirections(Direction.WEST, Direction.SOUTH)),
    ES(Prefab.DUNGEON_ES, Connection.fromDirections(Direction.EAST, Direction.SOUTH)),
    WN(Prefab.DUNGEON_WN, Connection.fromDirections(Direction.WEST, Direction.NORTH)),
    EN(Prefab.DUNGEON_EN, Connection.fromDirections(Direction.EAST, Direction.NORTH)),
    EW(Prefab.DUNGEON_EW, Connection.fromDirections(Direction.EAST, Direction.WEST)),

    EWS(Prefab.DUNGEON_EWS, Connection.fromDirections(Direction.EAST, Direction.WEST, Direction.SOUTH)),
    WSN(Prefab.DUNGEON_WSN, Connection.fromDirections(Direction.WEST, Direction.SOUTH, Direction.NORTH)),
    ESN(Prefab.DUNGEON_ESN, Connection.fromDirections(Direction.EAST, Direction.SOUTH, Direction.NORTH)),
    EWN(Prefab.DUNGEON_EWN, Connection.fromDirections(Direction.EAST, Direction.WEST, Direction.NORTH)),

    EWSN(Prefab.DUNGEON_EWSN, Connection.fromDirections(Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH));

    public static final List<DungeonPartType> END_PARTS = Arrays.asList(DungeonPartType.S, DungeonPartType.N, DungeonPartType.W, DungeonPartType.E);

    public Prefab getPrefab()
    {
        return prefab;
    }

    private final Prefab prefab;

    public Connection getConnection()
    {
        return connection;
    }

    private final Connection connection;

    DungeonPartType(Prefab prefab, Connection connection)
    {
        this.prefab = prefab;
        this.connection = connection;
    }
}
