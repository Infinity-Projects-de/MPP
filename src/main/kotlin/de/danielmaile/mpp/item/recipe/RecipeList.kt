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

package de.danielmaile.mpp.item.recipe

import de.danielmaile.mpp.item.ItemType

typealias HelmetRecipe = ArmorRecipe.HelmetRecipe
typealias ChestplateRecipe = ArmorRecipe.ChestplateRecipe
typealias LeggingsRecipe = ArmorRecipe.LeggingsRecipe
typealias BootsRecipe = ArmorRecipe.BootsRecipe

typealias SwordRecipe = ToolRecipe.SwordRecipe
typealias HoeRecipe = ToolRecipe.HoeRecipe
typealias PickaxeRecipe = ToolRecipe.PickaxeRecipe
typealias ShovelRecipe = ToolRecipe.ShovelRecipe
typealias AxeRecipe = ToolRecipe.AxeRecipe

private val craftingRecipeList: List<Recipe> = listOf(
    ShapedRecipe(
        ItemType.MAGIC_WAND, 1, listOf(
            null, ItemType.GRAVITITE_PLATE, null,
            null, ItemType.GRAVITITE_PLATE, null,
            null, ItemType.GRAVITITE_PLATE, null
        )
    ),

    // planks
    ShapelessRecipe(ItemType.AETHER_PLANKS, 4, listOf(ItemType.AETHER_LOG)),
    ShapelessRecipe(ItemType.FIRE_PLANKS, 4, listOf(ItemType.FIRE_LOG)),
    ShapelessRecipe(ItemType.WATER_PLANKS, 4, listOf(ItemType.WATER_LOG)),
    ShapelessRecipe(ItemType.AIR_PLANKS, 4, listOf(ItemType.AIR_LOG)),
    ShapelessRecipe(ItemType.EARTH_PLANKS, 4, listOf(ItemType.EARTH_LOG)),

    // sticks
    CraftingRecipe.StickRecipe(ItemType.AETHER_STICK, ItemType.AETHER_PLANKS),
    CraftingRecipe.StickRecipe(ItemType.AETHER_STICK, ItemType.FIRE_PLANKS),
    CraftingRecipe.StickRecipe(ItemType.AETHER_STICK, ItemType.WATER_PLANKS),
    CraftingRecipe.StickRecipe(ItemType.AETHER_STICK, ItemType.AIR_PLANKS),
    CraftingRecipe.StickRecipe(ItemType.AETHER_STICK, ItemType.EARTH_PLANKS)
)

private val armorRecipeList: List<Recipe> = listOf(
    // zanite
    HelmetRecipe(ItemType.ZANITE_HELMET, ItemType.ZANITE_STONE),
    ChestplateRecipe(ItemType.ZANITE_CHESTPLATE, ItemType.ZANITE_STONE),
    LeggingsRecipe(ItemType.ZANITE_LEGGINGS, ItemType.ZANITE_STONE),
    BootsRecipe(ItemType.ZANITE_BOOTS, ItemType.ZANITE_STONE),

    // gravitite
    HelmetRecipe(ItemType.GRAVITITE_HELMET, ItemType.GRAVITITE_PLATE),
    ChestplateRecipe(ItemType.GRAVITITE_CHESTPLATE, ItemType.GRAVITITE_PLATE),
    LeggingsRecipe(ItemType.GRAVITITE_LEGGINGS, ItemType.GRAVITITE_PLATE),
    BootsRecipe(ItemType.GRAVITITE_BOOTS, ItemType.GRAVITITE_PLATE),
)

private val toolRecipeList: List<Recipe> = listOf(
    // aether wood
    SwordRecipe(ItemType.AETHER_WOODEN_SWORD, ItemType.AETHER_PLANKS, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.AETHER_WOODEN_HOE, ItemType.AETHER_PLANKS, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.AETHER_WOODEN_PICKAXE, ItemType.AETHER_PLANKS, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.AETHER_WOODEN_SHOVEL, ItemType.AETHER_PLANKS, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.AETHER_WOODEN_AXE, ItemType.AETHER_PLANKS, ItemType.AETHER_STICK),

    // aether stone
    SwordRecipe(ItemType.AETHER_STONE_SWORD, ItemType.AETHER_STONE, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.AETHER_STONE_HOE, ItemType.AETHER_STONE, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.AETHER_STONE_PICKAXE, ItemType.AETHER_STONE, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.AETHER_STONE_SHOVEL, ItemType.AETHER_STONE, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.AETHER_STONE_AXE, ItemType.AETHER_STONE, ItemType.AETHER_STICK),

    // zanite
    SwordRecipe(ItemType.ZANITE_SWORD, ItemType.ZANITE_STONE, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.ZANITE_HOE, ItemType.ZANITE_STONE, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.ZANITE_PICKAXE, ItemType.ZANITE_STONE, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.ZANITE_SHOVEL, ItemType.ZANITE_STONE, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.ZANITE_AXE, ItemType.ZANITE_STONE, ItemType.AETHER_STICK),

    // gravitite
    SwordRecipe(ItemType.GRAVITITE_SWORD, ItemType.GRAVITITE_PLATE, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.GRAVITITE_HOE, ItemType.GRAVITITE_PLATE, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.GRAVITITE_PICKAXE, ItemType.GRAVITITE_PLATE, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.GRAVITITE_SHOVEL, ItemType.GRAVITITE_PLATE, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.GRAVITITE_AXE, ItemType.GRAVITITE_PLATE, ItemType.AETHER_STICK),

    // swords
    ShapelessRecipe(ItemType.LIGHTNING_SWORD, 1, listOf(ItemType.ZANITE_SWORD, ItemType.LIGHTNING_ESSENCE)),
    ShapelessRecipe(ItemType.FIRE_SWORD, 1, listOf(ItemType.ZANITE_SWORD, ItemType.FIRE_ESSENCE)),
    ShapelessRecipe(ItemType.ICE_SWORD, 1, listOf(ItemType.ZANITE_SWORD, ItemType.ICE_ESSENCE)),
    ShapelessRecipe(
        ItemType.SUN_SWORD,
        1,
        listOf(
            ItemType.LIGHTNING_ESSENCE,
            ItemType.FIRE_ESSENCE,
            ItemType.ICE_ESSENCE,
            ItemType.SUN_STONE,
            ItemType.FIRE_SWORD
        )
    )
)

private val smeltingRecipeList: List<Recipe> = listOf(
    FurnaceRecipe(ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_ORE, 50f, 30),
    FurnaceRecipe(ItemType.ZANITE_STONE, ItemType.ZANITE_ORE, 50f, 20),
    FurnaceRecipe(ItemType.SILVER_INGOT, ItemType.SILVER_ORE, 50f, 20),
    FurnaceRecipe(ItemType.TITANIUM_INGOT, ItemType.TITANIUM_ORE, 50f, 20),
    FurnaceRecipe(ItemType.AMBER_GEM, ItemType.AMBER_ORE, 50f, 20),
    FurnaceRecipe(ItemType.THALLASIUM_INGOT, ItemType.THALLASIUM_ORE, 50f, 20),
    FurnaceRecipe(ItemType.AMETRINE_CRYSTAL, ItemType.AMETRINE_ORE, 50f, 20),
    FurnaceRecipe(ItemType.PENDORITE_INGOT, ItemType.PENDORITE_ORE, 50f, 20),
    FurnaceRecipe(ItemType.EMERALD_SHARD, ItemType.NETHER_EMERALD_ORE, 50f, 20),
    FurnaceRecipe(ItemType.NIKOLITE_INGOT, ItemType.NIKOLITE_ORE, 50f, 20),
    FurnaceRecipe(ItemType.PYRITE_INGOT, ItemType.PYRITE_ORE, 50f, 20),
    FurnaceRecipe(ItemType.SHELDONITE_INGOT, ItemType.SHELDONITE_ORE, 50f, 20),
    FurnaceRecipe(ItemType.SODALITE_INGOT, ItemType.SODALITE_ORE, 50f, 20),
    FurnaceRecipe(ItemType.CINCINNASITE_INGOT, ItemType.CINCINNASITE_ORE, 50f, 20),
    FurnaceRecipe(ItemType.AETHERIUM_INGOT, ItemType.AETHERIUM_ORE, 50f, 20),
    FurnaceRecipe(ItemType.TERMINITE_INGOT, ItemType.TERMINITE_ORE, 50f, 20),

    // cinnebar ingot
    FurnaceRecipe(ItemType.CINNEBAR_INGOT, ItemType.CINNEBAR_ORE, 50f, 20),
    FurnaceRecipe(ItemType.CINNEBAR_INGOT, ItemType.END_CINNEBAR_ORE, 50f, 20),

    // nikolite ingot
    FurnaceRecipe(ItemType.NIKOLITE_INGOT, ItemType.DEEPSLATE_NIKOLITE_ORE, 50f, 20),
    FurnaceRecipe(ItemType.NIKOLITE_INGOT, ItemType.END_NIKOLITE_ORE, 50f, 20),

    // iridium ingot
    FurnaceRecipe(ItemType.IRIDIUM_INGOT, ItemType.IRIDIUM_ORE, 50f, 20),
    FurnaceRecipe(ItemType.IRIDIUM_INGOT, ItemType.DEEPSLATE_IRIDIUM_ORE, 50f, 20),
    FurnaceRecipe(ItemType.IRIDIUM_INGOT, ItemType.END_IRIDIUM_ORE, 50f, 20),

    // tungsten ingot
    FurnaceRecipe(ItemType.TUNGSTEN_INGOT, ItemType.TUNGSTEN_ORE, 50f, 20),
    FurnaceRecipe(ItemType.TUNGSTEN_INGOT, ItemType.DEEPSLATE_TUNGSTEN_ORE, 50f, 20),
    FurnaceRecipe(ItemType.TUNGSTEN_INGOT, ItemType.NETHER_TUNGSTEN_ORE, 50f, 20),
    FurnaceRecipe(ItemType.TUNGSTEN_INGOT, ItemType.END_TUNGSTEN_ORE, 50f, 20),

    // enderite ingot
    FurnaceRecipe(ItemType.ENDERITE_INGOT, ItemType.ENDERITE_ORE, 50f, 20),
    FurnaceRecipe(ItemType.ENDERITE_INGOT, ItemType.CRACKED_ENDERITE_ORE, 50f, 20),

    // zinc ingot
    FurnaceRecipe(ItemType.ZINC_INGOT, ItemType.ZINC_ORE, 50f, 20),
    FurnaceRecipe(ItemType.ZINC_INGOT, ItemType.DEEPSLATE_ZINC_ORE, 50f, 20),
    FurnaceRecipe(ItemType.ZINC_INGOT, ItemType.NETHER_ZINC_ORE, 50f, 20),

    // tin ingot
    FurnaceRecipe(ItemType.TIN_INGOT, ItemType.TIN_ORE, 50f, 20),
    FurnaceRecipe(ItemType.TIN_INGOT, ItemType.DEEPSLATE_TIN_ORE, 50f, 20),
    FurnaceRecipe(ItemType.TIN_INGOT, ItemType.NETHER_TIN_ORE, 50f, 20),
    FurnaceRecipe(ItemType.TIN_INGOT, ItemType.END_TIN_ORE, 50f, 20)
)

val recipeList: List<Recipe> =
    arrayListOf(craftingRecipeList, armorRecipeList, toolRecipeList, smeltingRecipeList).flatten()