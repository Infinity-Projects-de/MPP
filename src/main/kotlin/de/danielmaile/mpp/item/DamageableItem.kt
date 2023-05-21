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

import de.danielmaile.mpp.util.getDataString
import de.danielmaile.mpp.util.setDataString
import net.minecraft.world.item.Tiers
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import java.lang.NumberFormatException

interface DamageableItem : Item {
    val maxDamage: Int

    val currentDamage: (ItemStack) -> Int
        get() = {
            try {
                it.getDataString("customDamage")?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                0
            }
        }

    fun damage(itemStack: ItemStack) {
        var customDamage = currentDamage(itemStack)
        customDamage++
        itemStack.setDataString("customDamage", customDamage.toString())
        refreshDamage(itemStack)
    }

    fun refreshDamage(itemStack: ItemStack) {
        val trueDamage = Tiers.WOOD.uses * currentDamage(itemStack) / maxDamage

        itemStack.apply {
            itemMeta = (itemMeta as Damageable).apply {
                damage = trueDamage
            }
        }
    }
}
