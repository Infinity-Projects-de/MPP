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