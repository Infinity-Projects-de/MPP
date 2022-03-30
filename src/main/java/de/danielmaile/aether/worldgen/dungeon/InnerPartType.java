package de.danielmaile.aether.worldgen.dungeon;

import de.danielmaile.aether.worldgen.PrefabType;

public enum InnerPartType
{
    //Closed part can't be instantiated
    CLOSED(null, PartType.CLOSED),

    S(PrefabType.DUNGEON_PART_INNER_S, PartType.S),
    N(PrefabType.DUNGEON_PART_INNER_N, PartType.N),
    W(PrefabType.DUNGEON_PART_INNER_W, PartType.W),
    E(PrefabType.DUNGEON_PART_INNER_E, PartType.E),

    SN(PrefabType.DUNGEON_PART_INNER_SN, PartType.SN),
    WS(PrefabType.DUNGEON_PART_INNER_WS, PartType.WS),
    ES(PrefabType.DUNGEON_PART_INNER_ES, PartType.ES),
    WN(PrefabType.DUNGEON_PART_INNER_WN, PartType.WN),
    EN(PrefabType.DUNGEON_PART_INNER_EN, PartType.EN),
    EW(PrefabType.DUNGEON_PART_INNER_EW, PartType.EW),

    EWS(PrefabType.DUNGEON_PART_INNER_EWS, PartType.EWS),
    WSN(PrefabType.DUNGEON_PART_INNER_WSN, PartType.WSN),
    ESN(PrefabType.DUNGEON_PART_INNER_ESN, PartType.ESN),
    EWN(PrefabType.DUNGEON_PART_INNER_EWN, PartType.EWN),

    EWSN(PrefabType.DUNGEON_PART_INNER_EWSN, PartType.EWSN);

    public PrefabType getPrefabType()
    {
        return prefabType;
    }

    private final PrefabType prefabType;

    public PartType getType()
    {
        return type;
    }

    private final PartType type;

    InnerPartType(PrefabType prefabType, PartType type)
    {
        this.prefabType = prefabType;
        this.type = type;
    }

    public Connection getConnection()
    {
        return this.type.getConnection();
    }
}
