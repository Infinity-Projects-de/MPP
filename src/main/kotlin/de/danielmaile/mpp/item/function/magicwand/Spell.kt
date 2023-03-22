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

package de.danielmaile.mpp.item.function.magicwand

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.getDataString
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.inventory.ItemStack

enum class Spell(val range: Int, val color: Color) {
    LLAMA_SPELL(20, Color.PURPLE),
    YEET_SPELL(20, Color.BLUE),
    SOUND_BEAM(20, Color.RED);

    val displayName: Component = inst().getLanguageManager().getComponent("items.MAGIC_WAND.spells.$name")

    fun next(): Spell {
        return values()[(this.ordinal + 1) % values().size]
    }

    companion object {

        @JvmStatic
        fun fromTag(itemStack: ItemStack): Spell? {
            return try {
                val itemTag = itemStack.getDataString(SELECTED_SPELL_TAG) ?: return null
                valueOf(itemTag)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}