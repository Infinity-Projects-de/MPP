package de.danielmaile.aether.world.dungeon

import de.danielmaile.aether.world.PrefabType

enum class InnerPartType(val prefabType: PrefabType, val type: PartType) {

    //Closed part can't be instantiated
    CLOSED(PrefabType.NONE, PartType.CLOSED),

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

    fun getConnection(): Connection {
        return type.connection
    }
}