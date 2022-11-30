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
    VALKYRIE(
        ItemType.VALKYRE_RING,
        ItemType.VALKYRE_WINGS,
        ItemType.VALKYRE_LEGGINGS,
        ItemType.VALKYRE_BOOTS),
    ZANITE(
        ItemType.ZANITE_HELMET,
        ItemType.ZANITE_CHESTPLATE,
        ItemType.ZANITE_LEGGINGS,
        ItemType.ZANITE_BOOTS),
    GRAVITITE(
        ItemType.GRAVITITE_HELMET,
        ItemType.GRAVITITE_CHESTPLATE,
        ItemType.GRAVITITE_LEGGINGS,
        ItemType.GRAVITITE_BOOTS),
    THALLASIUM(
        ItemType.THALLASIUM_HELMET,
        ItemType.THALLASIUM_CHESTPLATE,
        ItemType.THALLASIUM_LEGGINGS,
        ItemType.THALLASIUM_BOOTS),
    PENDORITE(
        ItemType.PENDORITE_HELMET,
        ItemType.PENDORITE_CHESTPLATE,
        ItemType.PENDORITE_LEGGINGS,
        ItemType.PENDORITE_BOOTS),
    LEAD(
        ItemType.LEAD_HELMET,
        ItemType.LEAD_CHESTPLATE,
        ItemType.LEAD_LEGGINGS,
        ItemType.LEAD_BOOTS),
    BAUXITE(
        ItemType.BAUXITE_HELMET,
        ItemType.BAUXITE_CHESTPLATE,
        ItemType.BAUXITE_LEGGINGS,
        ItemType.BAUXITE_BOOTS),
    RUBY(
        ItemType.RUBY_HELMET,
        ItemType.RUBY_CHESTPLATE,
        ItemType.RUBY_LEGGINGS,
        ItemType.RUBY_BOOTS),
    AETHERIUM(
        ItemType.AETHERIUM_HELMET,
        ItemType.AETHERIUM_CHESTPLATE,
        ItemType.AETHERIUM_LEGGINGS,
        ItemType.AETHERIUM_BOOTS),
    TERMINITE(
        ItemType.TERMINITE_HELMET,
        ItemType.TERMINITE_CHESTPLATE,
        ItemType.TERMINITE_LEGGINGS,
        ItemType.TERMINITE_BOOTS),
    ENDERITE(
        ItemType.ENDERITE_HELMET,
        ItemType.ENDERITE_CHESTPLATE,
        ItemType.ENDERITE_LEGGINGS,
        ItemType.ENDERITE_BOOTS),
    PYRITE(
        ItemType.PYRITE_HELMET,
        ItemType.PYRITE_CHESTPLATE,
        ItemType.PYRITE_LEGGINGS,
        ItemType.PYRITE_BOOTS),
    SODALITE(
        ItemType.SODALITE_HELMET,
        ItemType.SODALITE_CHESTPLATE,
        ItemType.SODALITE_LEGGINGS,
        ItemType.SODALITE_BOOTS),
    TIN(
        ItemType.TIN_HELMET,
        ItemType.TIN_CHESTPLATE,
        ItemType.TIN_LEGGINGS,
        ItemType.TIN_BOOTS),
    CINNEBAR(
        ItemType.CINNEBAR_HELMET,
        ItemType.CINNEBAR_CHESTPLATE,
        ItemType.CINNEBAR_LEGGINGS,
        ItemType.CINNEBAR_BOOTS),
    AURORA(
        ItemType.AURORA_HELMET,
        ItemType.AURORA_CHESTPLATE,
        ItemType.AURORA_LEGGINGS,
        ItemType.AURORA_BOOTS);

    fun contains(itemType: ItemType?): Boolean {
        return head == itemType || chest == itemType || legs == itemType || feet == itemType
    }
}