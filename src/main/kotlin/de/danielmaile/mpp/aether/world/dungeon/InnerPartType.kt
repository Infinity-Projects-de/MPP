/*
 * This file is part of MPP.
 * Copyright (c) 2022 by it's authors. All rights reserved.
 * MPP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MPP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MPP.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.danielmaile.mpp.aether.world.dungeon

import de.danielmaile.mpp.aether.world.PrefabType

enum class InnerPartType(val prefabType: PrefabType, val type: PartType) {

    // closed part can't be instantiated
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