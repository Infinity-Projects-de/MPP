package de.danielmaile.aether.worldgen.dungeon;

public enum DungeonPartType
{
    S("S_var1", Connection.fromDirections(Direction.SOUTH)),
    N("N_var1", Connection.fromDirections(Direction.NORTH)),
    W("W_var1", Connection.fromDirections(Direction.WEST)),
    E("E_var1", Connection.fromDirections(Direction.EAST)),

    SN("SN_var1", Connection.fromDirections(Direction.SOUTH, Direction.NORTH)),
    WS("WS_var1", Connection.fromDirections(Direction.WEST, Direction.SOUTH)),
    ES("ES_var1", Connection.fromDirections(Direction.EAST, Direction.SOUTH)),
    WN("WN_var1", Connection.fromDirections(Direction.WEST, Direction.NORTH)),
    EN("EN_var1", Connection.fromDirections(Direction.EAST, Direction.NORTH)),
    EW("EW_var1", Connection.fromDirections(Direction.EAST, Direction.WEST)),

    EWS("EWS_var1", Connection.fromDirections(Direction.EAST, Direction.WEST, Direction.SOUTH)),
    WSN("WSN_var1", Connection.fromDirections(Direction.WEST, Direction.SOUTH, Direction.NORTH)),
    ESN("ESN_var1", Connection.fromDirections(Direction.EAST, Direction.SOUTH, Direction.NORTH)),
    EWN("EWN_var1", Connection.fromDirections(Direction.EAST, Direction.WEST, Direction.NORTH)),

    EWSN("EWSN_var1", Connection.fromDirections(Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH));

    public String getPrefabName()
    {
        return prefabName;
    }

    private final String prefabName;

    public Connection getConnection()
    {
        return connection;
    }

    private final Connection connection;

    DungeonPartType(String prefabName, Connection connection)
    {
        this.prefabName = prefabName;
        this.connection = connection;
    }
}
