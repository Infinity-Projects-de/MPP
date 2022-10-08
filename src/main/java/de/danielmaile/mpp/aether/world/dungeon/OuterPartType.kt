package de.danielmaile.mpp.aether.world.dungeon

import de.danielmaile.mpp.aether.world.PrefabType

enum class OuterPartType(val type: PartType, val prefabType: PrefabType?) {
    S(PartType.S, null),
    N(PartType.N,null),
    W(PartType.W, null),
    E(PartType.E, null),

    SN(PartType.SN, null),
    WS(PartType.WS, null),
    ES(PartType.ES, null),
    WN(PartType.WN, null),
    EN(PartType.EN, null),
    EW(PartType.EW, null),

    EWS(PartType.EWS, null),
    WSN(PartType.WSN, null),
    ESN(PartType.ESN, null),
    EWN(PartType.EWN, null),

    EWSN(PartType.EWSN, null);

    fun getConnection(): Connection {
        return type.connection
    }
}