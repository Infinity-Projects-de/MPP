/*
 * This file is part of MPP.
 * Copyright (c) 2023 by it's authors. All rights reserved.
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
import org.bukkit.Sound

enum class ArmorMaterial(
    val armor: ArmorProtection,
    private val enchantability: Int,
    private val equipmentSound: Sound?,
    val knockbackResistance: Float,
    val armorToughness: Float,
    val ingredient: Ingredients
) {
    ZANITE(
        ArmorProtection(1, 1, 1, 1),
        15,
        null,
        0.0F,
        0.0F,
        Ingredients.ZANITE_STONE
    ),
    GRAVITITE(
        ArmorProtection(1, 1, 1, 1),
        15,
        null,
        0.0F,
        0.0F,
        Ingredients.GRAVITITE_PLATE
    );

    val color: Color = Color.fromRGB(ordinal) // FIXME: This enum can't be reordered!!!
    class ArmorProtection(
        val boots: Int,
        val leggings: Int,
        val chestPlate: Int,
        val helmet: Int
    )
}
