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

import de.danielmaile.mpp.item.ItemType.*
import org.bukkit.Material.*
import org.bukkit.inventory.ItemStack

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
    ShapedRecipe(PARACHUTE.getItemStack(), listOf(
        CLOUD_SLOW_FALLING.getItemStack(), CLOUD_SLOW_FALLING.getItemStack(), CLOUD_SLOW_FALLING.getItemStack(),
        CLOUD_SLOW_FALLING.getItemStack(), null, CLOUD_SLOW_FALLING.getItemStack(),
        AIR_LEAVES.getItemStack(), AIR_LEAVES.getItemStack(), AIR_LEAVES.getItemStack()
    )),

    // planks
    ShapelessRecipe(AETHER_PLANKS.getItemStack(4), listOf(AETHER_LOG.getItemStack())),
    ShapelessRecipe(FIRE_PLANKS.getItemStack(4), listOf(FIRE_LOG.getItemStack())),
    ShapelessRecipe(WATER_PLANKS.getItemStack(4), listOf(WATER_LOG.getItemStack())),
    ShapelessRecipe(AIR_PLANKS.getItemStack(4), listOf(AIR_LOG.getItemStack())),
    ShapelessRecipe(EARTH_PLANKS.getItemStack(4), listOf(EARTH_LOG.getItemStack())),
    ShapelessRecipe(HELL_PLANKS.getItemStack(4), listOf(HELL_LOG.getItemStack())),

    // sticks
    CraftingRecipe.StickRecipe(AETHER_STICK.getItemStack(4), AETHER_PLANKS.getItemStack()),
    CraftingRecipe.StickRecipe(AETHER_STICK.getItemStack(4), FIRE_PLANKS.getItemStack()),
    CraftingRecipe.StickRecipe(AETHER_STICK.getItemStack(4), WATER_PLANKS.getItemStack()),
    CraftingRecipe.StickRecipe(AETHER_STICK.getItemStack(4), AIR_PLANKS.getItemStack()),
    CraftingRecipe.StickRecipe(AETHER_STICK.getItemStack(4), EARTH_PLANKS.getItemStack())
)

private val armorRecipeList: List<Recipe> = listOf(

    // helmets
    HelmetRecipe(ZANITE_HELMET.getItemStack(), ZANITE_STONE.getItemStack()),
    HelmetRecipe(GRAVITITE_HELMET.getItemStack(), GRAVITITE_PLATE.getItemStack()),
    HelmetRecipe(OBSIDIAN_HELMET.getItemStack(), POLISHED_OBSIDIAN.getItemStack()),
    HelmetRecipe(OBSIDIAN_HELMET.getItemStack(), SMOOTH_OBSIDIAN.getItemStack()),
    HelmetRecipe(THALLASIUM_HELMET.getItemStack(), THALLASIUM_INGOT.getItemStack()),
    HelmetRecipe(PENDORITE_HELMET.getItemStack(), PENDORITE_INGOT.getItemStack()),
    HelmetRecipe(ENDERITE_HELMET.getItemStack(), ENDERITE_PLATE.getItemStack()),
    HelmetRecipe(TIN_HELMET.getItemStack(), TIN_INGOT.getItemStack()),
    HelmetRecipe(LEAD_HELMET.getItemStack(), LEAD_PLATE.getItemStack()),
    HelmetRecipe(BAUXITE_HELMET.getItemStack(), BAUXITE_PLATE.getItemStack()),
    HelmetRecipe(CINNEBAR_HELMET.getItemStack(), CINNEBAR_INGOT.getItemStack()),
    HelmetRecipe(PYRITE_HELMET.getItemStack(), PYRITE_INGOT.getItemStack()),
    HelmetRecipe(RUBY_HELMET.getItemStack(), RUBY.getItemStack()),
    HelmetRecipe(SODALITE_HELMET.getItemStack(), SODALITE_INGOT.getItemStack()),
    HelmetRecipe(AETHERIUM_HELMET.getItemStack(), AETHERIUM_INGOT.getItemStack()),
    HelmetRecipe(TERMINITE_HELMET.getItemStack(), TERMINITE_INGOT.getItemStack()),
    HelmetRecipe(AURORA_HELMET.getItemStack(), AURORA_SHARD.getItemStack()),

    // chestplates
    ChestplateRecipe(ZANITE_CHESTPLATE.getItemStack(), ZANITE_STONE.getItemStack()),
    ChestplateRecipe(GRAVITITE_CHESTPLATE.getItemStack(), GRAVITITE_PLATE.getItemStack()),
    ChestplateRecipe(OBSIDIAN_CHESTPLATE.getItemStack(), SMOOTH_OBSIDIAN.getItemStack()),
    ChestplateRecipe(OBSIDIAN_CHESTPLATE.getItemStack(), POLISHED_OBSIDIAN.getItemStack()),
    ChestplateRecipe(THALLASIUM_CHESTPLATE.getItemStack(), THALLASIUM_INGOT.getItemStack()),
    ChestplateRecipe(PENDORITE_CHESTPLATE.getItemStack(), PENDORITE_INGOT.getItemStack()),
    ChestplateRecipe(ENDERITE_CHESTPLATE.getItemStack(), ENDERITE_PLATE.getItemStack()),
    ChestplateRecipe(TIN_CHESTPLATE.getItemStack(), TIN_INGOT.getItemStack()),
    ChestplateRecipe(LEAD_CHESTPLATE.getItemStack(), LEAD_PLATE.getItemStack()),
    ChestplateRecipe(BAUXITE_CHESTPLATE.getItemStack(), BAUXITE_PLATE.getItemStack()),
    ChestplateRecipe(CINNEBAR_CHESTPLATE.getItemStack(), CINNEBAR_INGOT.getItemStack()),
    ChestplateRecipe(PYRITE_CHESTPLATE.getItemStack(), PYRITE_INGOT.getItemStack()),
    ChestplateRecipe(RUBY_CHESTPLATE.getItemStack(), RUBY.getItemStack()),
    ChestplateRecipe(SODALITE_CHESTPLATE.getItemStack(), SODALITE_INGOT.getItemStack()),
    ChestplateRecipe(AETHERIUM_CHESTPLATE.getItemStack(), AETHERIUM_INGOT.getItemStack()),
    ChestplateRecipe(TERMINITE_CHESTPLATE.getItemStack(), TERMINITE_INGOT.getItemStack()),
    ChestplateRecipe(AURORA_CHESTPLATE.getItemStack(), AURORA_SHARD.getItemStack()),

    // leggings
    LeggingsRecipe(ZANITE_LEGGINGS.getItemStack(), ZANITE_STONE.getItemStack()),
    LeggingsRecipe(GRAVITITE_LEGGINGS.getItemStack(), GRAVITITE_PLATE.getItemStack()),
    LeggingsRecipe(OBSIDIAN_LEGGINGS.getItemStack(), SMOOTH_OBSIDIAN.getItemStack()),
    LeggingsRecipe(OBSIDIAN_LEGGINGS.getItemStack(), POLISHED_OBSIDIAN.getItemStack()),
    LeggingsRecipe(THALLASIUM_LEGGINGS.getItemStack(), THALLASIUM_INGOT.getItemStack()),
    LeggingsRecipe(PENDORITE_LEGGINGS.getItemStack(), PENDORITE_INGOT.getItemStack()),
    LeggingsRecipe(ENDERITE_LEGGINGS.getItemStack(), ENDERITE_PLATE.getItemStack()),
    LeggingsRecipe(TIN_LEGGINGS.getItemStack(), TIN_INGOT.getItemStack()),
    LeggingsRecipe(LEAD_LEGGINGS.getItemStack(), LEAD_PLATE.getItemStack()),
    LeggingsRecipe(BAUXITE_LEGGINGS.getItemStack(), BAUXITE_PLATE.getItemStack()),
    LeggingsRecipe(CINNEBAR_LEGGINGS.getItemStack(), CINNEBAR_INGOT.getItemStack()),
    LeggingsRecipe(PYRITE_LEGGINGS.getItemStack(), PYRITE_INGOT.getItemStack()),
    LeggingsRecipe(RUBY_LEGGINGS.getItemStack(), RUBY.getItemStack()),
    LeggingsRecipe(SODALITE_LEGGINGS.getItemStack(), SODALITE_INGOT.getItemStack()),
    LeggingsRecipe(AETHERIUM_LEGGINGS.getItemStack(), AETHERIUM_INGOT.getItemStack()),
    LeggingsRecipe(TERMINITE_LEGGINGS.getItemStack(), TERMINITE_INGOT.getItemStack()),
    LeggingsRecipe(AURORA_LEGGINGS.getItemStack(), AURORA_SHARD.getItemStack()),

    // boots
    BootsRecipe(ZANITE_BOOTS.getItemStack(), ZANITE_STONE.getItemStack()),
    BootsRecipe(GRAVITITE_BOOTS.getItemStack(), GRAVITITE_PLATE.getItemStack()),
    BootsRecipe(OBSIDIAN_BOOTS.getItemStack(), SMOOTH_OBSIDIAN.getItemStack()),
    BootsRecipe(OBSIDIAN_BOOTS.getItemStack(), POLISHED_OBSIDIAN.getItemStack()),
    BootsRecipe(THALLASIUM_BOOTS.getItemStack(), THALLASIUM_INGOT.getItemStack()),
    BootsRecipe(PENDORITE_BOOTS.getItemStack(), PENDORITE_INGOT.getItemStack()),
    BootsRecipe(ENDERITE_BOOTS.getItemStack(), ENDERITE_PLATE.getItemStack()),
    BootsRecipe(TIN_BOOTS.getItemStack(), TIN_INGOT.getItemStack()),
    BootsRecipe(LEAD_BOOTS.getItemStack(), LEAD_PLATE.getItemStack()),
    BootsRecipe(BAUXITE_BOOTS.getItemStack(), BAUXITE_PLATE.getItemStack()),
    BootsRecipe(CINNEBAR_BOOTS.getItemStack(), CINNEBAR_INGOT.getItemStack()),
    BootsRecipe(PYRITE_BOOTS.getItemStack(), PYRITE_INGOT.getItemStack()),
    BootsRecipe(RUBY_BOOTS.getItemStack(), RUBY.getItemStack()),
    BootsRecipe(SODALITE_BOOTS.getItemStack(), SODALITE_INGOT.getItemStack()),
    BootsRecipe(AETHERIUM_BOOTS.getItemStack(), AETHERIUM_INGOT.getItemStack()),
    BootsRecipe(TERMINITE_BOOTS.getItemStack(), TERMINITE_INGOT.getItemStack()),
    BootsRecipe(AURORA_BOOTS.getItemStack(), AURORA_SHARD.getItemStack()),


)

private val toolRecipeList: List<Recipe> = listOf(

    // swords - normal stick
    SwordRecipe(HELL_STONE_SWORD.getItemStack(), HELL_STONE.getItemStack(), ItemStack(STICK)),
    SwordRecipe(ENDER_SWORD.getItemStack(), ENDER_PLATE.getItemStack(), ItemStack(STICK)),
    SwordRecipe(ENDERITE_SWORD.getItemStack(), ENDERITE_INGOT.getItemStack(), ItemStack(STICK)),
    SwordRecipe(TUNGSTEN_SWORD.getItemStack(), TUNGSTEN_INGOT.getItemStack(), ItemStack(STICK)),
    SwordRecipe(RUBY_SWORD.getItemStack(), RUBY.getItemStack(), ItemStack(STICK)),
    SwordRecipe(SAPPHIRE_SWORD.getItemStack(), SAPPHIRE.getItemStack(), ItemStack(STICK)),
    SwordRecipe(DEMON_SWORD.getItemStack(), HELL_DIAMOND.getItemStack(), ItemStack(STICK)),

    // swords - aether stick
    SwordRecipe(AETHER_WOODEN_SWORD.getItemStack(), AETHER_PLANKS.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(AETHER_STONE_SWORD.getItemStack(), AETHER_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(ZANITE_SWORD.getItemStack(), ZANITE_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(GRAVITITE_SWORD.getItemStack(), GRAVITITE_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(HELL_STONE_SWORD.getItemStack(), HELL_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(ENDER_SWORD.getItemStack(), ENDER_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(ENDERITE_SWORD.getItemStack(), ENDERITE_INGOT.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(TUNGSTEN_SWORD.getItemStack(), TUNGSTEN_INGOT.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(RUBY_SWORD.getItemStack(), RUBY.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(SAPPHIRE_SWORD.getItemStack(), SAPPHIRE.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(AETHERIUM_SWORD.getItemStack(), AETHERIUM_INGOT.getItemStack(), AETHER_STICK.getItemStack()),
    SwordRecipe(DEMON_SWORD.getItemStack(), HELL_DIAMOND.getItemStack(), AETHER_STICK.getItemStack()),

    //hoes - normal stick
    HoeRecipe(ENDER_HOE.getItemStack(), ENDER_PLATE.getItemStack(), ItemStack(STICK)),
    HoeRecipe(ENDERITE_HOE.getItemStack(), ENDERITE_PLATE.getItemStack(), ItemStack(STICK)),
    HoeRecipe(TUNGSTEN_HOE.getItemStack(), TUNGSTEN_INGOT.getItemStack(), ItemStack(STICK)),
    HoeRecipe(RUBY_HOE.getItemStack(), RUBY.getItemStack(), ItemStack(STICK)),
    HoeRecipe(SAPPHIRE_HOE.getItemStack(), SAPPHIRE.getItemStack(), ItemStack(STICK)),


    // hoes - aether stick
    HoeRecipe(AETHER_WOODEN_HOE.getItemStack(), AETHER_PLANKS.getItemStack(), AETHER_STICK.getItemStack()),
    HoeRecipe(AETHER_STONE_HOE.getItemStack(), AETHER_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    HoeRecipe(ZANITE_HOE.getItemStack(), ZANITE_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    HoeRecipe(GRAVITITE_HOE.getItemStack(), GRAVITITE_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    HoeRecipe(ENDER_HOE.getItemStack(), ENDER_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    HoeRecipe(ENDERITE_HOE.getItemStack(), ENDERITE_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    HoeRecipe(TUNGSTEN_HOE.getItemStack(), TUNGSTEN_INGOT.getItemStack(), AETHER_STICK.getItemStack()),
    HoeRecipe(RUBY_HOE.getItemStack(), RUBY.getItemStack(), AETHER_STICK.getItemStack()),
    HoeRecipe(SAPPHIRE_HOE.getItemStack(), SAPPHIRE.getItemStack(), AETHER_STICK.getItemStack()),
    HoeRecipe(AETHERIUM_HOE.getItemStack(), AETHERIUM_INGOT.getItemStack(), AETHER_STICK.getItemStack()),

    // pickaxes - normal stick
    PickaxeRecipe(ENDER_PICKAXE.getItemStack(), ENDER_PLATE.getItemStack(), ItemStack(STICK)),
    PickaxeRecipe(ENDERITE_PICKAXE.getItemStack(), ENDERITE_PLATE.getItemStack(), ItemStack(STICK)),
    PickaxeRecipe(TUNGSTEN_PICKAXE.getItemStack(), TUNGSTEN_INGOT.getItemStack(), ItemStack(STICK)),
    PickaxeRecipe(RUBY_PICKAXE.getItemStack(), RUBY.getItemStack(), ItemStack(STICK)),
    PickaxeRecipe(SAPPHIRE_PICKAXE.getItemStack(), SAPPHIRE.getItemStack(), ItemStack(STICK)),
    PickaxeRecipe(HELL_STONE_PICKAXE.getItemStack(), HELL_STONE.getItemStack(), ItemStack(STICK)),

    // pickaxes - aether stick
    PickaxeRecipe(AETHER_WOODEN_PICKAXE.getItemStack(), AETHER_PLANKS.getItemStack(), AETHER_STICK.getItemStack()),
    PickaxeRecipe(AETHER_STONE_PICKAXE.getItemStack(), AETHER_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    PickaxeRecipe(ZANITE_PICKAXE.getItemStack(), ZANITE_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    PickaxeRecipe(GRAVITITE_PICKAXE.getItemStack(), GRAVITITE_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    PickaxeRecipe(ENDER_PICKAXE.getItemStack(), ENDER_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    PickaxeRecipe(ENDERITE_PICKAXE.getItemStack(), ENDERITE_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    PickaxeRecipe(TUNGSTEN_PICKAXE.getItemStack(), TUNGSTEN_INGOT.getItemStack(), AETHER_STICK.getItemStack()),
    PickaxeRecipe(RUBY_PICKAXE.getItemStack(), RUBY.getItemStack(), AETHER_STICK.getItemStack()),
    PickaxeRecipe(SAPPHIRE_PICKAXE.getItemStack(), SAPPHIRE.getItemStack(), AETHER_STICK.getItemStack()),
    PickaxeRecipe(AETHERIUM_PICKAXE.getItemStack(), AETHERIUM_INGOT.getItemStack(), AETHER_STICK.getItemStack()),
    PickaxeRecipe(HELL_STONE_PICKAXE.getItemStack(), HELL_STONE.getItemStack(), AETHER_STICK.getItemStack()),

    // shovels - normal stick
    ShovelRecipe(ENDER_SHOVEL.getItemStack(), ENDER_PLATE.getItemStack(), ItemStack(STICK)),
    ShovelRecipe(ENDERITE_SHOVEL.getItemStack(), ENDERITE_PLATE.getItemStack(), ItemStack(STICK)),
    ShovelRecipe(TUNGSTEN_SHOVEL.getItemStack(), TUNGSTEN_INGOT.getItemStack(), ItemStack(STICK)),
    ShovelRecipe(RUBY_SHOVEL.getItemStack(), RUBY.getItemStack(), ItemStack(STICK)),
    ShovelRecipe(SAPPHIRE_SHOVEL.getItemStack(), SAPPHIRE.getItemStack(), ItemStack(STICK)),

    // shovels - aether stick
    ShovelRecipe(AETHER_WOODEN_SHOVEL.getItemStack(), AETHER_PLANKS.getItemStack(), AETHER_STICK.getItemStack()),
    ShovelRecipe(AETHER_STONE_SHOVEL.getItemStack(), AETHER_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    ShovelRecipe(ZANITE_SHOVEL.getItemStack(), ZANITE_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    ShovelRecipe(GRAVITITE_SHOVEL.getItemStack(), GRAVITITE_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    ShovelRecipe(ENDER_SHOVEL.getItemStack(), ENDER_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    ShovelRecipe(ENDERITE_SHOVEL.getItemStack(), ENDERITE_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    ShovelRecipe(TUNGSTEN_SHOVEL.getItemStack(), TUNGSTEN_INGOT.getItemStack(), AETHER_STICK.getItemStack()),
    ShovelRecipe(RUBY_SHOVEL.getItemStack(), RUBY.getItemStack(), AETHER_STICK.getItemStack()),
    ShovelRecipe(SAPPHIRE_SHOVEL.getItemStack(), SAPPHIRE.getItemStack(), AETHER_STICK.getItemStack()),
    ShovelRecipe(AETHERIUM_SHOVEL.getItemStack(), AETHERIUM_INGOT.getItemStack(), AETHER_STICK.getItemStack()),

    // axes - normal stick
    AxeRecipe(ENDER_AXE.getItemStack(), ENDER_PLATE.getItemStack(), ItemStack(STICK)),
    AxeRecipe(ENDERITE_AXE.getItemStack(), ENDERITE_PLATE.getItemStack(), ItemStack(STICK)),
    AxeRecipe(TUNGSTEN_AXE.getItemStack(), TUNGSTEN_INGOT.getItemStack(), ItemStack(STICK)),
    AxeRecipe(RUBY_AXE.getItemStack(), RUBY.getItemStack(), ItemStack(STICK)),
    AxeRecipe(SAPPHIRE_AXE.getItemStack(), SAPPHIRE.getItemStack(), ItemStack(STICK)),

    // axes - aether stick
    AxeRecipe(AETHER_WOODEN_AXE.getItemStack(), AETHER_PLANKS.getItemStack(), AETHER_STICK.getItemStack()),
    AxeRecipe(AETHER_STONE_AXE.getItemStack(), AETHER_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    AxeRecipe(ZANITE_AXE.getItemStack(), ZANITE_STONE.getItemStack(), AETHER_STICK.getItemStack()),
    AxeRecipe(GRAVITITE_AXE.getItemStack(), GRAVITITE_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    AxeRecipe(ENDER_AXE.getItemStack(), ENDER_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    AxeRecipe(ENDERITE_AXE.getItemStack(), ENDERITE_PLATE.getItemStack(), AETHER_STICK.getItemStack()),
    AxeRecipe(TUNGSTEN_AXE.getItemStack(), TUNGSTEN_INGOT.getItemStack(), AETHER_STICK.getItemStack()),
    AxeRecipe(RUBY_AXE.getItemStack(), RUBY.getItemStack(), AETHER_STICK.getItemStack()),
    AxeRecipe(SAPPHIRE_AXE.getItemStack(), SAPPHIRE.getItemStack(), AETHER_STICK.getItemStack()),
    AxeRecipe(AETHERIUM_AXE.getItemStack(), AETHERIUM_INGOT.getItemStack(), AETHER_STICK.getItemStack()),

    // extra swords
    ShapelessRecipe(LIGHTNING_SWORD.getItemStack(), listOf(ZANITE_SWORD.getItemStack(), LIGHTNING_ESSENCE.getItemStack())),
    ShapelessRecipe(FIRE_SWORD.getItemStack(), listOf(ZANITE_SWORD.getItemStack(), FIRE_ESSENCE.getItemStack())),
    ShapelessRecipe(ICE_SWORD.getItemStack(), listOf(ZANITE_SWORD.getItemStack(), ICE_ESSENCE.getItemStack())),
    ShapelessRecipe(SUN_SWORD.getItemStack(), listOf(FIRE_ESSENCE.getItemStack(), SUN_STONE.getItemStack(), FIRE_SWORD.getItemStack()))
)

private val smeltingRecipeList: List<Recipe> = listOf(

    // single recipes per item
    FurnaceRecipe(GRAVITITE_PLATE.getItemStack(), GRAVITITE_ORE.getItemStack(), 50f, 30),
    FurnaceRecipe(ZANITE_STONE.getItemStack(), ZANITE_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(SILVER_INGOT.getItemStack(), SILVER_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(TITANIUM_INGOT.getItemStack(), TITANIUM_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(AMBER_GEM.getItemStack(), AMBER_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(THALLASIUM_INGOT.getItemStack(), THALLASIUM_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(AMETRINE_CRYSTAL.getItemStack(), AMETRINE_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(PENDORITE_INGOT.getItemStack(), PENDORITE_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(EMERALD_SHARD.getItemStack(), NETHER_EMERALD_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(NIKOLITE_INGOT.getItemStack(), NIKOLITE_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(PYRITE_INGOT.getItemStack(), PYRITE_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(SHELDONITE_INGOT.getItemStack(), SHELDONITE_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(SODALITE_INGOT.getItemStack(), SODALITE_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(CINCINNASITE_INGOT.getItemStack(), CINCINNASITE_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(AETHERIUM_INGOT.getItemStack(), AETHERIUM_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(TERMINITE_INGOT.getItemStack(), TERMINITE_ORE.getItemStack(), 50f, 20),

    //multiple recipes per item

    // cinnebar ingot
    FurnaceRecipe(CINNEBAR_INGOT.getItemStack(), CINNEBAR_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(CINNEBAR_INGOT.getItemStack(), END_CINNEBAR_ORE.getItemStack(), 50f, 20),

    // nikolite ingot
    FurnaceRecipe(NIKOLITE_INGOT.getItemStack(), DEEPSLATE_NIKOLITE_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(NIKOLITE_INGOT.getItemStack(), END_NIKOLITE_ORE.getItemStack(), 50f, 20),

    // iridium ingot
    FurnaceRecipe(IRIDIUM_INGOT.getItemStack(), IRIDIUM_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(IRIDIUM_INGOT.getItemStack(), DEEPSLATE_IRIDIUM_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(IRIDIUM_INGOT.getItemStack(), END_IRIDIUM_ORE.getItemStack(), 50f, 20),

    // tungsten ingot
    FurnaceRecipe(TUNGSTEN_INGOT.getItemStack(), TUNGSTEN_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(TUNGSTEN_INGOT.getItemStack(), DEEPSLATE_TUNGSTEN_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(TUNGSTEN_INGOT.getItemStack(), NETHER_TUNGSTEN_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(TUNGSTEN_INGOT.getItemStack(), END_TUNGSTEN_ORE.getItemStack(), 50f, 20),

    // enderite ingot
    FurnaceRecipe(ENDERITE_INGOT.getItemStack(), ENDERITE_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(ENDERITE_INGOT.getItemStack(), CRACKED_ENDERITE_ORE.getItemStack(), 50f, 20),

    // zinc ingot
    FurnaceRecipe(ZINC_INGOT.getItemStack(), ZINC_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(ZINC_INGOT.getItemStack(), DEEPSLATE_ZINC_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(ZINC_INGOT.getItemStack(), NETHER_ZINC_ORE.getItemStack(), 50f, 20),

    // tin ingot
    FurnaceRecipe(TIN_INGOT.getItemStack(), TIN_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(TIN_INGOT.getItemStack(), DEEPSLATE_TIN_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(TIN_INGOT.getItemStack(), NETHER_TIN_ORE.getItemStack(), 50f, 20),
    FurnaceRecipe(TIN_INGOT.getItemStack(), END_TIN_ORE.getItemStack(), 50f, 20)
)

val recipeList: List<Recipe> =
    arrayListOf(craftingRecipeList, armorRecipeList, toolRecipeList, smeltingRecipeList).flatten()