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

package de.danielmaile.mpp.item

enum class ArmorSet(val head: ItemType, val chest: ItemType, val legs: ItemType, val feet: ItemType) {
    VALKYRIE(ItemType.VALKYRE_RING, ItemType.VALKYRE_WINGS, ItemType.VALKYRE_LEGGINGS, ItemType.VALKYRE_BOOTS),
    ZANITE(ItemType.ZANITE_HELMET, ItemType.ZANITE_CHESTPLATE, ItemType.ZANITE_LEGGINGS, ItemType.ZANITE_BOOTS),
    GRAVITITE(
        ItemType.GRAVITITE_HELMET,
        ItemType.GRAVITITE_CHESTPLATE,
        ItemType.GRAVITITE_LEGGINGS,
        ItemType.GRAVITITE_BOOTS
    );

    fun contains(itemType: ItemType?): Boolean {
        return head == itemType || chest == itemType || legs == itemType || feet == itemType
    }
}