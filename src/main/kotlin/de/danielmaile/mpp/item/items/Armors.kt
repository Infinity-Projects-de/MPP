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

package de.danielmaile.mpp.item.items

import de.danielmaile.mpp.item.Item
import de.danielmaile.mpp.item.recipe.recipes.ArmorRecipe
import de.danielmaile.mpp.item.utils.ArmorMaterial
import de.danielmaile.mpp.item.utils.ArmorSlot
import org.bukkit.Material
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta

enum class Armors(
    val armorMaterial: ArmorMaterial,
    val slot: ArmorSlot // Should we use this or create an enum like before

) : Item {
    ZANITE_HELMET(ArmorMaterial.ZANITE, ArmorSlot.HELMET),
    ZANITE_CHESTPLATE(ArmorMaterial.ZANITE, ArmorSlot.CHESTPLATE),
    ZANITE_LEGGINGS(ArmorMaterial.ZANITE, ArmorSlot.LEGGINGS),
    ZANITE_BOOTS(ArmorMaterial.ZANITE, ArmorSlot.BOOTS),
    GRAVITITE_HELMET(ArmorMaterial.GRAVITITE, ArmorSlot.HELMET),
    GRAVITITE_CHESTPLATE(ArmorMaterial.GRAVITITE, ArmorSlot.CHESTPLATE),
    GRAVITITE_LEGGINGS(ArmorMaterial.GRAVITITE, ArmorSlot.LEGGINGS),
    GRAVITITE_BOOTS(ArmorMaterial.GRAVITITE, ArmorSlot.BOOTS);

    override val material: Material
        get() = when (slot) {
            ArmorSlot.HELMET -> Material.LEATHER_HELMET
            ArmorSlot.CHESTPLATE -> Material.LEATHER_CHESTPLATE
            ArmorSlot.LEGGINGS -> Material.LEATHER_LEGGINGS
            ArmorSlot.BOOTS -> Material.LEATHER_BOOTS
        }

    override fun modifySpecialItemMeta(itemMeta: ItemMeta) {
        itemMeta as LeatherArmorMeta
        itemMeta.setColor(armorMaterial.color)
    }

    override fun getRecipes(): List<Recipe> {
        val material = armorMaterial.ingredient.itemStack(1)
        val itemStack = this.itemStack(1)
        val recipes: List<Recipe> = when (slot) {
            ArmorSlot.HELMET -> ArmorRecipe.HelmetRecipe(itemStack, material)

            ArmorSlot.CHESTPLATE -> ArmorRecipe.ChestplateRecipe(itemStack, material)

            ArmorSlot.LEGGINGS -> ArmorRecipe.LeggingsRecipe(itemStack, material)

            ArmorSlot.BOOTS -> ArmorRecipe.BootsRecipe(itemStack, material)
        }.recipes

        return recipes
    }
}
