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

import org.bukkit.Material
import org.bukkit.inventory.EquipmentSlot

enum class Armors(
    val slot: EquipmentSlot // Should we use this or create an enum like before
): Item {
    ZANITE_CHESTPLATE(EquipmentSlot.CHEST),
    ZANITE_LEGGINGS(EquipmentSlot.LEGS),
    ZANITE_HELMET(EquipmentSlot.HEAD),
    ZANITE_BOOTS(EquipmentSlot.FEET),
    GRAVITITE_CHESTPLATE(EquipmentSlot.CHEST),
    GRAVITITE_LEGGINGS(EquipmentSlot.LEGS),
    GRAVITITE_HELMET(EquipmentSlot.HEAD),
    GRAVITITE_BOOTS(EquipmentSlot.FEET);

    override val material: Material
        get() = when(slot) {
            EquipmentSlot.CHEST -> Material.LEATHER_CHESTPLATE
            EquipmentSlot.HEAD -> Material.LEATHER_HELMET
            EquipmentSlot.LEGS -> Material.LEATHER_LEGGINGS
            EquipmentSlot.FEET -> Material.LEATHER_BOOTS
            else -> { throw NoSuchFieldException() }
        }

    override fun register() {
        ItemRegistry.registerItem("mpp:$name", this)
    }
}