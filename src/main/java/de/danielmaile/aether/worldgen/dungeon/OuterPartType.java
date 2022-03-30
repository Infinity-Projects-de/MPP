package de.danielmaile.aether.worldgen.dungeon;

import de.danielmaile.aether.worldgen.PrefabType;

public enum OuterPartType
{
    S(PartType.S),
    N(PartType.N),
    W(PartType.W),
    E(PartType.E),

    SN(PartType.SN),
    WS(PartType.WS),
    ES(PartType.ES),
    WN(PartType.WN),
    EN(PartType.EN),
    EW(PartType.EW),

    EWS(PartType.EWS),
    WSN(PartType.WSN),
    ESN(PartType.ESN),
    EWN(PartType.EWN),

    EWSN(PartType.EWSN);

    public PartType getType()
    {
        return type;
    }

    private final PartType type;

    public PrefabType getPrefabType()
    {
        return prefabType;
    }

    private final PrefabType prefabType;

    OuterPartType(PartType type)
    {
        this(type, null);
    }

    OuterPartType(PartType type, PrefabType prefabType)
    {
        this.type = type;
        this.prefabType = prefabType;
    }

    public Connection getConnection()
    {
        return this.type.getConnection();
    }
}
