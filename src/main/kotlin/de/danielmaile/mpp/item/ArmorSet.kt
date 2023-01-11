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

import org.bukkit.Color

enum class ArmorSet(val color: Color, val head: ItemType, val chest: ItemType, val legs: ItemType, val feet: ItemType) {
    VALKYRIE(
        Color.fromRGB(255, 0, 1),
        ItemType.VALKYRE_RING,
        ItemType.VALKYRE_WINGS,
        ItemType.VALKYRE_LEGGINGS,
        ItemType.VALKYRE_BOOTS),
    ZANITE(
        Color.fromRGB(255, 0, 2),
        ItemType.ZANITE_HELMET,
        ItemType.ZANITE_CHESTPLATE,
        ItemType.ZANITE_LEGGINGS,
        ItemType.ZANITE_BOOTS),
    GRAVITITE(
        Color.fromRGB(255, 0, 3),
        ItemType.GRAVITITE_HELMET,
        ItemType.GRAVITITE_CHESTPLATE,
        ItemType.GRAVITITE_LEGGINGS,
        ItemType.GRAVITITE_BOOTS),
    THALLASIUM(
        Color.fromRGB(255, 0, 4),
        ItemType.THALLASIUM_HELMET,
        ItemType.THALLASIUM_CHESTPLATE,
        ItemType.THALLASIUM_LEGGINGS,
        ItemType.THALLASIUM_BOOTS),
    PENDORITE(
        Color.fromRGB(255, 0, 5),
        ItemType.PENDORITE_HELMET,
        ItemType.PENDORITE_CHESTPLATE,
        ItemType.PENDORITE_LEGGINGS,
        ItemType.PENDORITE_BOOTS),
    LEAD(
        Color.fromRGB(255, 0, 6),
        ItemType.LEAD_HELMET,
        ItemType.LEAD_CHESTPLATE,
        ItemType.LEAD_LEGGINGS,
        ItemType.LEAD_BOOTS),
    BAUXITE(
        Color.fromRGB(255, 0, 7),
        ItemType.BAUXITE_HELMET,
        ItemType.BAUXITE_CHESTPLATE,
        ItemType.BAUXITE_LEGGINGS,
        ItemType.BAUXITE_BOOTS),
    RUBY(
        Color.fromRGB(255, 0, 8),
        ItemType.RUBY_HELMET,
        ItemType.RUBY_CHESTPLATE,
        ItemType.RUBY_LEGGINGS,
        ItemType.RUBY_BOOTS),
    AETHERIUM(
        Color.fromRGB(255, 0, 9),
        ItemType.AETHERIUM_HELMET,
        ItemType.AETHERIUM_CHESTPLATE,
        ItemType.AETHERIUM_LEGGINGS,
        ItemType.AETHERIUM_BOOTS),
    TERMINITE(
        Color.fromRGB(255, 0, 10),
        ItemType.TERMINITE_HELMET,
        ItemType.TERMINITE_CHESTPLATE,
        ItemType.TERMINITE_LEGGINGS,
        ItemType.TERMINITE_BOOTS),
    ENDERITE(
        Color.fromRGB(255, 0, 11),
        ItemType.ENDERITE_HELMET,
        ItemType.ENDERITE_CHESTPLATE,
        ItemType.ENDERITE_LEGGINGS,
        ItemType.ENDERITE_BOOTS),
    PYRITE(
        Color.fromRGB(255, 0, 12),
        ItemType.PYRITE_HELMET,
        ItemType.PYRITE_CHESTPLATE,
        ItemType.PYRITE_LEGGINGS,
        ItemType.PYRITE_BOOTS),
    SODALITE(
        Color.fromRGB(255, 0, 13),
        ItemType.SODALITE_HELMET,
        ItemType.SODALITE_CHESTPLATE,
        ItemType.SODALITE_LEGGINGS,
        ItemType.SODALITE_BOOTS),
    TIN(
        Color.fromRGB(255, 0, 14),
        ItemType.TIN_HELMET,
        ItemType.TIN_CHESTPLATE,
        ItemType.TIN_LEGGINGS,
        ItemType.TIN_BOOTS),
    CINNEBAR(
        Color.fromRGB(255, 0, 15),
        ItemType.CINNEBAR_HELMET,
        ItemType.CINNEBAR_CHESTPLATE,
        ItemType.CINNEBAR_LEGGINGS,
        ItemType.CINNEBAR_BOOTS),
    AURORA(
        Color.fromRGB(255, 0, 16),
        ItemType.AURORA_HELMET,
        ItemType.AURORA_CHESTPLATE,
        ItemType.AURORA_LEGGINGS,
        ItemType.AURORA_BOOTS),
    OBSIDIAN(
        Color.fromRGB(255, 0, 17),
        ItemType.OBSIDIAN_HELMET,
        ItemType.OBSIDIAN_CHESTPLATE,
        ItemType.OBSIDIAN_LEGGINGS,
        ItemType.OBSIDIAN_BOOTS),
    PHOENIX(
        Color.fromRGB(255, 0, 18),
        ItemType.PHOENIX_HELMET,
        ItemType.PHOENIX_CHESTPLATE,
        ItemType.PHOENIX_LEGGINGS,
        ItemType.PHOENIX_BOOTS);

    fun contains(itemType: ItemType?): Boolean {
        return head == itemType || chest == itemType || legs == itemType || feet == itemType
    }
}