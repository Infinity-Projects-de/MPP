package de.danielmaile.aether.worldgen.dungeon;

import de.danielmaile.aether.worldgen.Prefab;

public enum InnerPartType
{
    //Closed part can't be instantiated
    CLOSED(null, PartType.CLOSED),

    S(Prefab.DUNGEON_PART_INNER_S, PartType.S),
    N(Prefab.DUNGEON_PART_INNER_N, PartType.N),
    W(Prefab.DUNGEON_PART_INNER_W, PartType.W),
    E(Prefab.DUNGEON_PART_INNER_E, PartType.E),

    SN(Prefab.DUNGEON_PART_INNER_SN, PartType.SN),
    WS(Prefab.DUNGEON_PART_INNER_WS, PartType.WS),
    ES(Prefab.DUNGEON_PART_INNER_ES, PartType.ES),
    WN(Prefab.DUNGEON_PART_INNER_WN, PartType.WN),
    EN(Prefab.DUNGEON_PART_INNER_EN, PartType.EN),
    EW(Prefab.DUNGEON_PART_INNER_EW, PartType.EW),

    EWS(Prefab.DUNGEON_PART_INNER_EWS, PartType.EWS),
    WSN(Prefab.DUNGEON_PART_INNER_WSN, PartType.WSN),
    ESN(Prefab.DUNGEON_PART_INNER_ESN, PartType.ESN),
    EWN(Prefab.DUNGEON_PART_INNER_EWN, PartType.EWN),

    EWSN(Prefab.DUNGEON_PART_INNER_EWSN, PartType.EWSN);

    public Prefab getPrefab()
    {
        return prefab;
    }

    private final Prefab prefab;

    public PartType getType()
    {
        return type;
    }

    private final PartType type;

    InnerPartType(Prefab prefab, PartType type)
    {
        this.prefab = prefab;
        this.type = type;
    }

    public Connection getConnection()
    {
        return this.type.getConnection();
    }
}
