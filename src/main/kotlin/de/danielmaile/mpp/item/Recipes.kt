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

import de.danielmaile.mpp.inst
import org.bukkit.NamespacedKey
import org.bukkit.inventory.*

enum class Recipes {

    AETHER_WOODEN_SWORD(
        true,
        ItemType.AETHER_WOODEN_SWORD, 1,
        ItemType.AETHER_PLANKS, null, null,
        ItemType.AETHER_PLANKS, null, null,
        ItemType.AETHER_STICK, null, null
    ),
    AETHER_WOODEN_HOE(
        true,
        ItemType.AETHER_WOODEN_HOE, 1,
        null,ItemType.AETHER_PLANKS, ItemType.AETHER_PLANKS,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    AETHER_WOODEN_PICKAXE(
        true, ItemType.AETHER_WOODEN_PICKAXE, 1,
        ItemType.AETHER_PLANKS, ItemType.AETHER_PLANKS, ItemType.AETHER_PLANKS,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    AETHER_WOODEN_SHOVEL(
        true, ItemType.AETHER_WOODEN_SHOVEL, 1,
        null, ItemType.AETHER_PLANKS, null,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    AETHER_WOODEN_AXE(
        true, ItemType.AETHER_WOODEN_AXE, 1,
        null, ItemType.AETHER_PLANKS, ItemType.AETHER_PLANKS,
        null, ItemType.AETHER_STICK, ItemType.AETHER_PLANKS,
        null, ItemType.AETHER_STICK, null
    ),
    AETHER_STONE_SWORD(
        true, ItemType.AETHER_STONE_SWORD, 1,
        null, ItemType.AETHER_STONE, null,
        null, ItemType.AETHER_STONE, null,
        null, ItemType.AETHER_STICK, null
    ),
    AETHER_STONE_HOE(
        true, ItemType.AETHER_STONE_HOE, 1,
        null, ItemType.AETHER_STONE, ItemType.AETHER_STONE,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    AETHER_STONE_PICKAXE(
        true, ItemType.AETHER_STONE_PICKAXE, 1,
        ItemType.AETHER_STONE, ItemType.AETHER_STONE, ItemType.AETHER_STONE,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    AETHER_STONE_SHOVEL(
        true, ItemType.AETHER_STONE_SHOVEL, 1,
        null, ItemType.AETHER_STONE, null,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    AETHER_STONE_AXE(
        true,
        ItemType.AETHER_STONE_AXE, 1,
        null, ItemType.AETHER_STONE, ItemType.AETHER_STONE,
        null, ItemType.AETHER_STICK, ItemType.AETHER_STONE,
        null, ItemType.AETHER_STICK, null
    ),
    AETHER_STICK(
        false, ItemType.AETHER_STICK, 4,
        ItemType.AETHER_PLANKS
    ),
    AETHER_STICK1(
        false, ItemType.AETHER_STICK, 4,
        ItemType.FIRE_PLANKS
    ),
    AETHER_STICK2(
        false, ItemType.WATER_PLANKS, 4,
        ItemType.FIRE_PLANKS
    ),
    AETHER_STICK3(
        false, ItemType.AIR_PLANKS, 4,
        ItemType.FIRE_PLANKS
    ),
    AETHER_STICK4(
        false, ItemType.EARTH_PLANKS, 4,
        ItemType.FIRE_PLANKS
    ),
    ZANITE_SWORD(
        true, ItemType.ZANITE_SWORD, 1,
        ItemType.ZANITE_STONE, null, null,
        ItemType.ZANITE_STONE, null, null,
        ItemType.AETHER_STICK, null, null
    ),
    ZANITE_SWORD1(
        true, ItemType.ZANITE_SWORD, 1,
        null, ItemType.ZANITE_STONE, null,
        null, ItemType.ZANITE_STONE, null,
        null, ItemType.AETHER_STICK, null
    ),
    ZANITE_SWORD2(
        true, ItemType.ZANITE_SWORD, 1,
        null, null, ItemType.ZANITE_STONE,
        null, null, ItemType.ZANITE_STONE,
        null, null, ItemType.AETHER_STICK
    ),
    ZANITE_HOE(
        true,
        ItemType.ZANITE_HOE, 1,
        ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null,
        ItemType.AETHER_STICK, null, null,
        ItemType.AETHER_STICK, null, null
    ),
    ZANITE_HOE1(
        true,
        ItemType.ZANITE_HOE, 1,
        ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null, null,
        ItemType.AETHER_STICK, null, null,
        ItemType.AETHER_STICK, null
    ),
    ZANITE_HOE2(
        true,
        ItemType.ZANITE_HOE, 1,
        null,
        ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null,
        ItemType.AETHER_STICK, null, null,
        ItemType.AETHER_STICK, null
    ),
    ZANITE_HOE3(
        true,
        ItemType.ZANITE_HOE, 1,
        null,
        ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null, null,
        ItemType.AETHER_STICK, null, null,
        ItemType.AETHER_STICK
    ),
    ZANITE_PICKAXE(
        true,
        ItemType.ZANITE_PICKAXE, 1,
        ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null,
        ItemType.AETHER_STICK, null, null,
        ItemType.AETHER_STICK, null
    ),
    ZANITE_SHOVEL(
        true, ItemType.ZANITE_SHOVEL, 1,
        ItemType.ZANITE_STONE, null, null,
        ItemType.AETHER_STICK, null, null,
        ItemType.AETHER_STICK, null, null
    ),
    ZANITE_SHOVEL1(
        true, ItemType.ZANITE_SHOVEL, 1,
        null, ItemType.ZANITE_STONE, null,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    ZANITE_SHOVEL2(
        true, ItemType.ZANITE_SHOVEL, 1,
        null, null, ItemType.ZANITE_STONE,
        null, null, ItemType.AETHER_STICK,
        null, null, ItemType.AETHER_STICK
    ),
    ZANITE_AXE(
        true,
        ItemType.ZANITE_AXE, 1,
        ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null,
        ItemType.ZANITE_STONE,
        ItemType.AETHER_STICK, null, null,
        ItemType.AETHER_STICK, null
    ),
    ZANITE_AXE1(
        true,
        ItemType.ZANITE_AXE, 1,
        null,
        ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null,
        ItemType.AETHER_STICK,
        ItemType.ZANITE_STONE, null,
        ItemType.AETHER_STICK, null
    ),
    GRAVITITE_SWORD(
        true, ItemType.GRAVITITE_SWORD, 1,
        ItemType.GRAVITITE_PLATE, null, null,
        ItemType.GRAVITITE_PLATE, null, null,
        ItemType.AETHER_STICK, null, null
    ),
    GRAVITITE_SWORD1(
        true, ItemType.GRAVITITE_SWORD, 1,
        null, ItemType.GRAVITITE_PLATE, null,
        null, ItemType.GRAVITITE_PLATE, null,
        null, ItemType.AETHER_STICK, null
    ),
    GRAVITITE_SWORD2(
        true, ItemType.GRAVITITE_SWORD, 1,
        null, null, ItemType.GRAVITITE_PLATE,
        null, null, ItemType.GRAVITITE_PLATE,
        null, null, ItemType.AETHER_STICK
    ),
    GRAVITITE_HOE(
        true,
        ItemType.GRAVITITE_HOE, 1,
        ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, null,
        ItemType.AETHER_STICK, null, ItemType.AETHER_STICK,
        null, null, null
    ),
    GRAVITITE_HOE1(
        true,
        ItemType.GRAVITITE_HOE, 1,
        ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, null,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    GRAVITITE_HOE2(
        true,
        ItemType.GRAVITITE_HOE, 1,
        null, ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    GRAVITITE_HOE3(
        true,
        ItemType.GRAVITITE_HOE, 1,
        null, ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE,
        null, null, ItemType.AETHER_STICK,
        null, null, ItemType.AETHER_STICK
    ),
    GRAVITITE_PICKAXE(
        true,
        ItemType.GRAVITITE_PICKAXE, 1,
        ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    GRAVITITE_SHOVEL(
        true, ItemType.GRAVITITE_SHOVEL, 1,
        ItemType.GRAVITITE_ORE, null, null,
        ItemType.AETHER_STICK, null, null,
        ItemType.AETHER_STICK, null, null
    ),
    GRAVITITE_SHOVEL1(
        true, ItemType.GRAVITITE_SHOVEL, 1,
        null, ItemType.GRAVITITE_ORE, null,
        null, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    GRAVITITE_SHOVEL2(
        true, ItemType.GRAVITITE_SHOVEL, 1,
        null, null, ItemType.GRAVITITE_ORE,
        null, null, ItemType.AETHER_STICK,
        null, null, ItemType.AETHER_STICK
    ),
    GRAVITITE_AXE(
        true,
        ItemType.GRAVITITE_AXE, 1,
        ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, null,
        ItemType.GRAVITITE_ORE, ItemType.AETHER_STICK, null,
        null, ItemType.AETHER_STICK, null
    ),
    GRAVITITE_AXE1(
        true,
        ItemType.GRAVITITE_AXE, 1,
        null, ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE,
        null, ItemType.AETHER_STICK, ItemType.GRAVITITE_ORE,
        null, ItemType.AETHER_STICK, null
    ),
    LIGHTNING_SWORD(
        false, ItemType.LIGHTNING_SWORD, 1,
        ItemType.ZANITE_SWORD, ItemType.LIGHTNING_ESSENCE
    ),
    FIRE_SWORD(
        false, ItemType.FIRE_SWORD, 1,
        ItemType.ZANITE_SWORD, ItemType.FIRE_ESSENCE
    ),
    ICE_SWORD(
        false, ItemType.ICE_SWORD, 1,
        ItemType.ZANITE_SWORD, ItemType.ICE_ESSENCE
    ),
    SUN_SWORD(
        false,
        ItemType.SUN_SWORD, 1,
        ItemType.LIGHTNING_ESSENCE, ItemType.FIRE_ESSENCE, ItemType.ICE_ESSENCE, ItemType.SUN_STONE, ItemType.FIRE_SWORD
    ),
    ZANITE_HELMET(
        true,
        ItemType.ZANITE_HELMET, 1,
        ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE,
        null, null, null
    ),
    ZANITE_CHESTPLATE(
        true,
        ItemType.ZANITE_CHESTPLATE, 1,
        ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE
    ),
    ZANITE_LEGGINGS(
        true,
        ItemType.ZANITE_LEGGINGS, 1,
        ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE
    ),
    ZANITE_BOOTS(
        true,
        ItemType.ZANITE_BOOTS, 1,
        null, null, null,
        ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE,
        ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE
    ),
    GRAVITITE_HELMET(
        true,
        ItemType.GRAVITITE_HELMET, 1,
        ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE,
        ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE,
        null, null, null
    ),
    GRAVITITE_CHESTPLATE(
        true,
        ItemType.GRAVITITE_CHESTPLATE, 1,
        ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE,
        ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE,
        ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE
    ),
    GRAVITITE_LEGGINGS(
        true,
        ItemType.GRAVITITE_LEGGINGS, 1,
        ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE,
        ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE,
        ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE
    ),
    GRAVITITE_BOOTS(
        true,
        ItemType.GRAVITITE_BOOTS, 1,
        null, null, null,
        ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE,
        ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE
    ),
    AETHER_PLANKS(
        false, ItemType.AETHER_PLANKS, 4,
        ItemType.AETHER_LOG
    ),
    FIRE_PLANKS(
        false, ItemType.FIRE_PLANKS, 4,
        ItemType.FIRE_LOG
    ),
    WATER_PLANKS(
        false, ItemType.WATER_PLANKS, 4,
        ItemType.WATER_LOG
    ),
    AIR_PLANKS(
        false, ItemType.AIR_PLANKS, 4,
        ItemType.AIR_LOG
    ),
    EARTH_PLANKS(
        false, ItemType.EARTH_PLANKS, 4,
        ItemType.EARTH_LOG
    ),
    MAGIC_WAND(
        true, ItemType.MAGIC_WAND, 1,
        null, ItemType.GRAVITITE_PLATE, null,
        null, ItemType.GRAVITITE_PLATE, null,
        null, ItemType.GRAVITITE_PLATE, null
    ),
    GRAVITITE_ORE_SMELTING(ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_ORE, 50f, 30),
    ZANITE_ORE_SMELTING(ItemType.ZANITE_STONE, ItemType.ZANITE_ORE, 50f, 20),
    SILVER_ORE_SMELTING(ItemType.SILVER_INGOT, ItemType.SILVER_ORE, 50f, 20),
    TITANIUM_SMELTING(ItemType.TITANIUM_INGOT, ItemType.TITANIUM_ORE, 50f, 20),
    AMBER_ORE_SMELTING(ItemType.AMBER_GEM, ItemType.AMBER_ORE, 50f, 20),
    THALLASIUM_ORE_SMELTING(ItemType.THALLASIUM_INGOT, ItemType.THALLASIUM_ORE, 50f, 20),
    AMETRINE_ORE_SMELTING(ItemType.AMETRINE_CRYSTAL, ItemType.AMETRINE_ORE, 50f, 20),
    PENDORITE_ORE_SMELTING(ItemType.PENDORITE_INGOT, ItemType.PENDORITE_ORE, 50f, 20),
    NETHER_EMERALD_ORE_SMELTING(ItemType.EMERLAD_SHARD, ItemType.NETHER_EMERALD_ORE, 50f, 20),
    ENDERITE_ORE_SMELTING(ItemType.ENDERITE_INGOT, ItemType.ENDERITE_ORE, 50f, 20),
    CRACKED_ENDERITE_ORE_SMELTING(ItemType.ENDERITE_INGOT, ItemType.CRACKED_ENDERITE_ORE, 50f, 20),
    TUNGSTEN_ORE_SMELTING(ItemType.TUNGSTEN_INGOT, ItemType.TUNGSTEN_ORE, 50f, 20),
    TIN_ORE_SMELTING(ItemType.TIN_INGOT, ItemType.TIN_ORE, 50f, 20),
    NIKOLITE_ORE_SMELTING(ItemType.NIKOLITE_INGOT, ItemType.NIKOLITE_ORE, 50f, 20),
    CINNEBAR_ORE_SMELTING(ItemType.CINNEBAR_INGOT, ItemType.CINNEBAR_ORE, 50f, 20),
    IRIDIUM_ORE_SMELTING(ItemType.IRIDIUM_INGOT, ItemType.IRIDIUM_ORE, 50f, 20),
    PYRITE_ORE_SMELTING(ItemType.PYRITE_INGOT, ItemType.PYRITE_ORE, 50f, 20),
    SHELDONITE_ORE_SMELTING(ItemType.SHELDONITE_INGOT, ItemType.SHELDONITE_ORE, 50f, 20),
    SODALITE_ORE_SMELTING(ItemType.SODALITE_INGOT, ItemType.SODALITE_ORE, 50f, 20),
    ZINC_ORE_SMELTING(ItemType.ZINC_INGOT, ItemType.ZINC_ORE, 50f, 20),
    CINCINNASITE_ORE_SMELTING(ItemType.CINCINNASITE_INGOT, ItemType.CINCINNASITE_ORE, 50f, 20),
    AETHERIUM_ORE_SMELTING(ItemType.AETHERIUM_INGOT, ItemType.AETHERIUM_ORE, 50f, 20),
    TERMINITE_ORE_SMELTING(ItemType.TERMINITE_INGOT, ItemType.TERMINITE_ORE, 50f, 20),
    DEEPSLATE_TUNGSTEN_ORE_SMELTING(ItemType.TUNGSTEN_INGOT, ItemType.DEEPSLATE_TUNGSTEN_ORE, 50f, 20),
    DEEPSLATE_NIKOLITE_ORE_SMELTING(ItemType.NIKOLITE_INGOT, ItemType.DEEPSLATE_NIKOLITE_ORE, 50f, 20),
    DEEPSLATE_TIN_ORE_SMELTING(ItemType.TIN_INGOT, ItemType.DEEPSLATE_TIN_ORE, 50f, 20),
    DEEPSLATE_IRIDIUM_ORE_SMELTING(ItemType.IRIDIUM_INGOT, ItemType.DEEPSLATE_IRIDIUM_ORE, 50f, 20),
    DEEPSLATE_ZINC_ORE_SMELTING(ItemType.ZINC_INGOT, ItemType.DEEPSLATE_ZINC_ORE, 50f, 20),
    NETHER_ZINC_ORE_SMELTING(ItemType.ZINC_INGOT, ItemType.NETHER_ZINC_ORE, 50f, 20),
    NETHER_TUNGSTEN_ORE_SMELTING(ItemType.TUNGSTEN_INGOT, ItemType.NETHER_TUNGSTEN_ORE, 50f, 20),
    NETHER_TIN_ORE_SMELTING(ItemType.TIN_INGOT, ItemType.NETHER_TIN_ORE, 50f, 20),
    END_NIKOLITE_ORE_SMELTING(ItemType.NIKOLITE_INGOT, ItemType.END_NIKOLITE_ORE, 50f, 20),
    END_IRIDIUM_ORE_SMELTING(ItemType.IRIDIUM_INGOT, ItemType.END_IRIDIUM_ORE, 50f, 20),
    END_TUNGSTEN_ORE_SMELTING(ItemType.TUNGSTEN_INGOT, ItemType.END_TUNGSTEN_ORE, 50f, 20),
    END_TIN_ORE_SMELTING(ItemType.TIN_INGOT, ItemType.END_TIN_ORE, 50f, 20),
    END_CINNEBAR_ORE_SMELTING(ItemType.CINNEBAR_INGOT, ItemType.END_CINNEBAR_ORE, 50f, 20);

    val recipe: Recipe

    constructor(result: ItemType, ingredient: ItemType, xp: Float, time: Int) {
        val key = NamespacedKey(inst(), name)
        recipe =
            FurnaceRecipe(key, result.getItemStack(), RecipeChoice.ExactChoice(ingredient.getItemStack()), xp, time)
    }

    constructor(shaped: Boolean, result: ItemType, amount: Int, vararg ingredients: ItemType?) {
        val key = NamespacedKey(inst(), name)
        if (shaped) {
            val shapedRecipe = ShapedRecipe(key, result.getItemStack(amount))

            // set shape
            val shape = charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'H', 'I', 'J')
            for (i in shape.indices) {
                if (ingredients[i] == null) {
                    shape[i] = ' '
                }
            }
            val shapeString = String(shape)
            shapedRecipe.shape(shapeString.substring(0, 3), shapeString.substring(3, 6), shapeString.substring(6, 9))

            // set ingredients
            for (i in shape.indices) {
                val ingredient = ingredients[i]
                if (shape[i] != ' ' && ingredient != null) {
                    shapedRecipe.setIngredient(shape[i], RecipeChoice.ExactChoice(ingredient.getItemStack()))
                }
            }
            recipe = shapedRecipe
        } else {
            val shapelessRecipe = ShapelessRecipe(key, result.getItemStack(amount))
            for (itemType in ingredients) {
                if (itemType == null) continue
                shapelessRecipe.addIngredient(RecipeChoice.ExactChoice(itemType.getItemStack()))
            }
            recipe = shapelessRecipe
        }
    }
}