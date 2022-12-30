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

    // other items
    ShapedRecipe(ItemType.PARACHUTE, 1, listOf(
        ItemType.CLOUD_SLOW_FALLING, ItemType.CLOUD_SLOW_FALLING, ItemType.CLOUD_SLOW_FALLING,
        ItemType.CLOUD_SLOW_FALLING, null, ItemType.CLOUD_SLOW_FALLING,
        ItemType.AIR_LEAVES, ItemType.AIR_LEAVES, ItemType.AIR_LEAVES)),

    // planks
    ShapelessRecipe(ItemType.AETHER_PLANKS, 4, listOf(ItemType.AETHER_LOG)),
    ShapelessRecipe(ItemType.FIRE_PLANKS, 4, listOf(ItemType.FIRE_LOG)),
    ShapelessRecipe(ItemType.WATER_PLANKS, 4, listOf(ItemType.WATER_LOG)),
    ShapelessRecipe(ItemType.AIR_PLANKS, 4, listOf(ItemType.AIR_LOG)),
    ShapelessRecipe(ItemType.EARTH_PLANKS, 4, listOf(ItemType.EARTH_LOG)),
    ShapelessRecipe(ItemType.HELL_PLANKS, 4, listOf(ItemType.HELL_LOG)),

    // sticks
    CraftingRecipe.StickRecipe(ItemType.AETHER_STICK, ItemType.AETHER_PLANKS),
    CraftingRecipe.StickRecipe(ItemType.AETHER_STICK, ItemType.FIRE_PLANKS),
    CraftingRecipe.StickRecipe(ItemType.AETHER_STICK, ItemType.WATER_PLANKS),
    CraftingRecipe.StickRecipe(ItemType.AETHER_STICK, ItemType.AIR_PLANKS),
    CraftingRecipe.StickRecipe(ItemType.AETHER_STICK, ItemType.EARTH_PLANKS)
)

private val armorRecipeList: List<Recipe> = listOf(

    // helmets
    HelmetRecipe(ItemType.ZANITE_HELMET, ItemType.ZANITE_STONE),
    HelmetRecipe(ItemType.GRAVITITE_HELMET, ItemType.GRAVITITE_PLATE),
    HelmetRecipe(ItemType.OBSIDIAN_HELMET, ItemType.POLISHED_OBSIDIAN),
    HelmetRecipe(ItemType.OBSIDIAN_HELMET, ItemType.SMOOTH_OBSIDIAN),
    HelmetRecipe(ItemType.THALLASIUM_HELMET, ItemType.THALLASIUM_INGOT),
    HelmetRecipe(ItemType.PENDORITE_HELMET, ItemType.PENDORITE_INGOT),
    HelmetRecipe(ItemType.ENDERITE_HELMET, ItemType.ENDERITE_PLATE),
    HelmetRecipe(ItemType.TIN_HELMET, ItemType.TIN_INGOT),
    HelmetRecipe(ItemType.LEAD_HELMET, ItemType.LEAD_PLATE),
    HelmetRecipe(ItemType.BAUXITE_HELMET, ItemType.BAUXITE_PLATE),
    HelmetRecipe(ItemType.CINNEBAR_HELMET, ItemType.CINNEBAR_INGOT),
    HelmetRecipe(ItemType.PYRITE_HELMET, ItemType.PYRITE_INGOT),
    HelmetRecipe(ItemType.RUBY_HELMET, ItemType.RUBY),
    HelmetRecipe(ItemType.SODALITE_HELMET, ItemType.SODALITE_INGOT),
    HelmetRecipe(ItemType.AETHERIUM_HELMET, ItemType.AETHERIUM_INGOT),
    HelmetRecipe(ItemType.TERMINITE_HELMET, ItemType.TERMINITE_INGOT),
    HelmetRecipe(ItemType.AURORA_HELMET, ItemType.AURORA_SHARD),

    // chestplates
    ChestplateRecipe(ItemType.ZANITE_CHESTPLATE, ItemType.ZANITE_STONE),
    ChestplateRecipe(ItemType.GRAVITITE_CHESTPLATE, ItemType.GRAVITITE_PLATE),
    ChestplateRecipe(ItemType.OBSIDIAN_CHESTPLATE, ItemType.SMOOTH_OBSIDIAN),
    ChestplateRecipe(ItemType.OBSIDIAN_CHESTPLATE, ItemType.POLISHED_OBSIDIAN),
    ChestplateRecipe(ItemType.THALLASIUM_CHESTPLATE, ItemType.THALLASIUM_INGOT),
    ChestplateRecipe(ItemType.PENDORITE_CHESTPLATE, ItemType.PENDORITE_INGOT),
    ChestplateRecipe(ItemType.ENDERITE_CHESTPLATE, ItemType.ENDERITE_PLATE),
    ChestplateRecipe(ItemType.TIN_CHESTPLATE, ItemType.TIN_INGOT),
    ChestplateRecipe(ItemType.LEAD_CHESTPLATE, ItemType.LEAD_PLATE),
    ChestplateRecipe(ItemType.BAUXITE_CHESTPLATE, ItemType.BAUXITE_PLATE),
    ChestplateRecipe(ItemType.CINNEBAR_CHESTPLATE, ItemType.CINNEBAR_INGOT),
    ChestplateRecipe(ItemType.PYRITE_CHESTPLATE, ItemType.PYRITE_INGOT),
    ChestplateRecipe(ItemType.RUBY_CHESTPLATE, ItemType.RUBY),
    ChestplateRecipe(ItemType.SODALITE_CHESTPLATE, ItemType.SODALITE_INGOT),
    ChestplateRecipe(ItemType.AETHERIUM_CHESTPLATE, ItemType.AETHERIUM_INGOT),
    ChestplateRecipe(ItemType.TERMINITE_CHESTPLATE, ItemType.TERMINITE_INGOT),
    ChestplateRecipe(ItemType.AURORA_CHESTPLATE, ItemType.AURORA_SHARD),

    // leggings
    LeggingsRecipe(ItemType.ZANITE_LEGGINGS, ItemType.ZANITE_STONE),
    LeggingsRecipe(ItemType.GRAVITITE_LEGGINGS, ItemType.GRAVITITE_PLATE),
    LeggingsRecipe(ItemType.OBSIDIAN_LEGGINGS, ItemType.SMOOTH_OBSIDIAN),
    LeggingsRecipe(ItemType.OBSIDIAN_LEGGINGS, ItemType.POLISHED_OBSIDIAN),
    LeggingsRecipe(ItemType.THALLASIUM_LEGGINGS, ItemType.THALLASIUM_INGOT),
    LeggingsRecipe(ItemType.PENDORITE_LEGGINGS, ItemType.PENDORITE_INGOT),
    LeggingsRecipe(ItemType.ENDERITE_LEGGINGS, ItemType.ENDERITE_PLATE),
    LeggingsRecipe(ItemType.TIN_LEGGINGS, ItemType.TIN_INGOT),
    LeggingsRecipe(ItemType.LEAD_LEGGINGS, ItemType.LEAD_PLATE),
    LeggingsRecipe(ItemType.BAUXITE_LEGGINGS, ItemType.BAUXITE_PLATE),
    LeggingsRecipe(ItemType.CINNEBAR_LEGGINGS, ItemType.CINNEBAR_INGOT),
    LeggingsRecipe(ItemType.PYRITE_LEGGINGS, ItemType.PYRITE_INGOT),
    LeggingsRecipe(ItemType.RUBY_LEGGINGS, ItemType.RUBY),
    LeggingsRecipe(ItemType.SODALITE_LEGGINGS, ItemType.SODALITE_INGOT),
    LeggingsRecipe(ItemType.AETHERIUM_LEGGINGS, ItemType.AETHERIUM_INGOT),
    LeggingsRecipe(ItemType.TERMINITE_LEGGINGS, ItemType.TERMINITE_INGOT),
    LeggingsRecipe(ItemType.AURORA_LEGGINGS, ItemType.AURORA_SHARD),

    // boots
    BootsRecipe(ItemType.ZANITE_BOOTS, ItemType.ZANITE_STONE),
    BootsRecipe(ItemType.GRAVITITE_BOOTS, ItemType.GRAVITITE_PLATE),
    BootsRecipe(ItemType.OBSIDIAN_BOOTS, ItemType.SMOOTH_OBSIDIAN),
    BootsRecipe(ItemType.OBSIDIAN_BOOTS, ItemType.POLISHED_OBSIDIAN),
    BootsRecipe(ItemType.THALLASIUM_BOOTS, ItemType.THALLASIUM_INGOT),
    BootsRecipe(ItemType.PENDORITE_BOOTS, ItemType.PENDORITE_INGOT),
    BootsRecipe(ItemType.ENDERITE_BOOTS, ItemType.ENDERITE_PLATE),
    BootsRecipe(ItemType.TIN_BOOTS, ItemType.TIN_INGOT),
    BootsRecipe(ItemType.LEAD_BOOTS, ItemType.LEAD_PLATE),
    BootsRecipe(ItemType.BAUXITE_BOOTS, ItemType.BAUXITE_PLATE),
    BootsRecipe(ItemType.CINNEBAR_BOOTS, ItemType.CINNEBAR_INGOT),
    BootsRecipe(ItemType.PYRITE_BOOTS, ItemType.PYRITE_INGOT),
    BootsRecipe(ItemType.RUBY_BOOTS, ItemType.RUBY),
    BootsRecipe(ItemType.SODALITE_BOOTS, ItemType.SODALITE_INGOT),
    BootsRecipe(ItemType.AETHERIUM_BOOTS, ItemType.AETHERIUM_INGOT),
    BootsRecipe(ItemType.TERMINITE_BOOTS, ItemType.TERMINITE_INGOT),
    BootsRecipe(ItemType.AURORA_BOOTS, ItemType.AURORA_SHARD),


)

private val toolRecipeList: List<Recipe> = listOf(

    // swords
    SwordRecipe(ItemType.AETHER_WOODEN_SWORD, ItemType.AETHER_PLANKS, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.AETHER_STONE_SWORD, ItemType.AETHER_STONE, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.ZANITE_SWORD, ItemType.ZANITE_STONE, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.GRAVITITE_SWORD, ItemType.GRAVITITE_PLATE, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.HELL_STONE_SWORD, ItemType.HELL_STONE, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.ENDER_SWORD, ItemType.ENDER_PLATE, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.ENDERITE_SWORD, ItemType.ENDERITE_INGOT, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.TUNGSTEN_SWORD, ItemType.TUNGSTEN_INGOT, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.RUBY_SWORD, ItemType.RUBY, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.SAPPHIRE_SWORD, ItemType.SAPPHIRE, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.AETHERIUM_SWORD, ItemType.AETHERIUM_INGOT, ItemType.AETHER_STICK),
    SwordRecipe(ItemType.DEMON_SWORD, ItemType.HELL_DIAMOND, ItemType.AETHER_STICK),


    // hoes
    HoeRecipe(ItemType.AETHER_WOODEN_HOE, ItemType.AETHER_PLANKS, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.AETHER_STONE_HOE, ItemType.AETHER_STONE, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.ZANITE_HOE, ItemType.ZANITE_STONE, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.GRAVITITE_HOE, ItemType.GRAVITITE_PLATE, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.ENDER_HOE, ItemType.ENDER_PLATE, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.ENDERITE_HOE, ItemType.ENDERITE_PLATE, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.TUNGSTEN_HOE, ItemType.TUNGSTEN_INGOT, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.RUBY_HOE, ItemType.RUBY, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.SAPPHIRE_HOE, ItemType.SAPPHIRE, ItemType.AETHER_STICK),
    HoeRecipe(ItemType.AETHERIUM_HOE, ItemType.AETHERIUM_INGOT, ItemType.AETHER_STICK),

    // pickaxes
    PickaxeRecipe(ItemType.AETHER_WOODEN_PICKAXE, ItemType.AETHER_PLANKS, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.AETHER_STONE_PICKAXE, ItemType.AETHER_STONE, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.ZANITE_PICKAXE, ItemType.ZANITE_STONE, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.GRAVITITE_PICKAXE, ItemType.GRAVITITE_PLATE, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.ENDER_PICKAXE, ItemType.ENDER_PLATE, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.ENDERITE_PICKAXE, ItemType.ENDERITE_PLATE, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.TUNGSTEN_PICKAXE, ItemType.TUNGSTEN_INGOT, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.RUBY_PICKAXE, ItemType.RUBY, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.SAPPHIRE_PICKAXE, ItemType.SAPPHIRE, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.AETHERIUM_PICKAXE, ItemType.AETHERIUM_INGOT, ItemType.AETHER_STICK),
    PickaxeRecipe(ItemType.HELL_STONE_PICKAXE, ItemType.HELL_STONE, ItemType.AETHER_STICK),

    // shovels
    ShovelRecipe(ItemType.AETHER_WOODEN_SHOVEL, ItemType.AETHER_PLANKS, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.AETHER_STONE_SHOVEL, ItemType.AETHER_STONE, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.ZANITE_SHOVEL, ItemType.ZANITE_STONE, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.GRAVITITE_SHOVEL, ItemType.GRAVITITE_PLATE, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.ENDER_SHOVEL, ItemType.ENDER_PLATE, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.ENDERITE_SHOVEL, ItemType.ENDERITE_PLATE, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.TUNGSTEN_SHOVEL, ItemType.TUNGSTEN_INGOT, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.RUBY_SHOVEL, ItemType.RUBY, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.SAPPHIRE_SHOVEL, ItemType.SAPPHIRE, ItemType.AETHER_STICK),
    ShovelRecipe(ItemType.AETHERIUM_SHOVEL, ItemType.AETHERIUM_INGOT, ItemType.AETHER_STICK),

    // axes
    AxeRecipe(ItemType.AETHER_WOODEN_AXE, ItemType.AETHER_PLANKS, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.AETHER_STONE_AXE, ItemType.AETHER_STONE, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.ZANITE_AXE, ItemType.ZANITE_STONE, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.GRAVITITE_AXE, ItemType.GRAVITITE_PLATE, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.ENDER_AXE, ItemType.ENDER_PLATE, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.ENDERITE_AXE, ItemType.ENDERITE_PLATE, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.TUNGSTEN_AXE, ItemType.TUNGSTEN_INGOT, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.RUBY_AXE, ItemType.RUBY, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.SAPPHIRE_AXE, ItemType.SAPPHIRE, ItemType.AETHER_STICK),
    AxeRecipe(ItemType.AETHERIUM_AXE, ItemType.AETHERIUM_INGOT, ItemType.AETHER_STICK),

    // extra swords
    ShapelessRecipe(ItemType.LIGHTNING_SWORD, 1, listOf(ItemType.ZANITE_SWORD, ItemType.LIGHTNING_ESSENCE)),
    ShapelessRecipe(ItemType.FIRE_SWORD, 1, listOf(ItemType.ZANITE_SWORD, ItemType.FIRE_ESSENCE)),
    ShapelessRecipe(ItemType.ICE_SWORD, 1, listOf(ItemType.ZANITE_SWORD, ItemType.ICE_ESSENCE)),
    ShapelessRecipe(ItemType.SUN_SWORD, 1, listOf(ItemType.FIRE_ESSENCE, ItemType.SUN_STONE, ItemType.FIRE_SWORD))
)

private val smeltingRecipeList: List<Recipe> = listOf(

    // single recipes per item
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

    //multiple recipes per item

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