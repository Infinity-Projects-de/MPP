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

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import de.danielmaile.mpp.block.BlockType
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.getDataString
import de.danielmaile.mpp.util.setDataString
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import java.util.*

const val MPP_ITEM_TAG_KEY = "mpp_item"

// don't change this, otherwise existing items will break
private const val modelIdOffset = 1000

enum class ItemType(
    val material: Material,
    private val attributeModifiers: Multimap<Attribute, AttributeModifier>?,
    val placeBlockType: BlockType?
) {

    // don't change order because existing items will break
    AETHER_LOG(Material.OAK_WOOD, null, BlockType.AETHER_LOG),
    FIRE_LOG(Material.OAK_WOOD, null, BlockType.FIRE_LOG),
    WATER_LOG(Material.OAK_WOOD, null, BlockType.WATER_LOG),
    AIR_LOG(Material.OAK_WOOD, null, BlockType.AIR_LOG),
    EARTH_LOG(Material.OAK_WOOD, null, BlockType.EARTH_LOG),
    AETHER_PLANKS(Material.OAK_PLANKS, null, BlockType.AETHER_PLANKS),
    FIRE_PLANKS(Material.OAK_PLANKS, null, BlockType.FIRE_PLANKS),
    WATER_PLANKS(Material.OAK_PLANKS, null, BlockType.WATER_PLANKS),
    AIR_PLANKS(Material.OAK_PLANKS, null, BlockType.AIR_PLANKS),
    EARTH_PLANKS(Material.OAK_PLANKS, null, BlockType.EARTH_PLANKS),
    SILVER_ORE(Material.STONE, null, BlockType.SILVER_ORE),
    TITANIUM_ORE(Material.STONE, null, BlockType.TITANIUM_ORE),
    ZANITE_ORE(Material.STONE, null, BlockType.ZANITE_ORE),
    GRAVITITE_ORE(Material.STONE, null, BlockType.GRAVITITE_ORE),
    AETHER_STONE(Material.STONE, null, BlockType.AETHER_STONE),
    CLOUD_HEAL(Material.WHITE_WOOL, null, BlockType.CLOUD_HEAL),
    CLOUD_SLOW_FALLING(Material.WHITE_WOOL, null, BlockType.CLOUD_SLOW_FALLING),
    CLOUD_SPEED(Material.WHITE_WOOL, null, BlockType.CLOUD_SPEED),
    CLOUD_JUMP(Material.WHITE_WOOL, null, BlockType.CLOUD_JUMP),
    CLOUD_HEAL2(Material.WHITE_WOOL, null, BlockType.CLOUD_HEAL2),
    CLOUD_SPEED2(Material.WHITE_WOOL, null, BlockType.CLOUD_SPEED2),
    CLOUD_JUMP2(Material.WHITE_WOOL, null, BlockType.CLOUD_JUMP2),
    AETHER_GRASS_BLOCK(Material.DIRT, null, BlockType.AETHER_GRASS_BLOCK),
    AETHER_DIRT(Material.DIRT, null, BlockType.AETHER_DIRT),
    CRYSTAL_ORE(Material.AMETHYST_BLOCK, null, BlockType.CRYSTAL_ORE),
    AETHER_STICK(Material.STICK, null, null),
    AETHER_STONE_AXE(Material.STONE_AXE, null, null),
    AETHER_STONE_HOE(Material.STONE_HOE, null, null),
    AETHER_STONE_PICKAXE(Material.STONE_PICKAXE, null, null),
    AETHER_STONE_SHOVEL(Material.STONE_SHOVEL, null, null),
    AETHER_STONE_SWORD(Material.STONE_SWORD, null, null),
    AETHER_WOODEN_AXE(Material.WOODEN_AXE, null, null),
    AETHER_WOODEN_HOE(Material.WOODEN_HOE, null, null),
    AETHER_WOODEN_PICKAXE(Material.WOODEN_PICKAXE, null, null),
    AETHER_WOODEN_SHOVEL(Material.WOODEN_SHOVEL, null, null),
    AETHER_WOODEN_SWORD(Material.WOODEN_SWORD, null, null),
    ADMIN_SWORD(Material.NETHERITE_SWORD, ToolAttribute(1000000.0, 0.1).toAttributeMap(), null),
    FIRE_ESSENCE(Material.ORANGE_DYE, null, null),
    FIRE_SWORD(Material.DIAMOND_SWORD, ToolAttribute(11.0, 1.6).toAttributeMap(), null),
    GRAVITITE_AXE(Material.NETHERITE_AXE, null, null),
    GRAVITITE_BOOTS(Material.NETHERITE_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    GRAVITITE_CHESTPLATE(Material.NETHERITE_CHESTPLATE, ArmorAttribute(10.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    GRAVITITE_HELMET(Material.NETHERITE_HELMET, ArmorAttribute(5.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    GRAVITITE_HOE(Material.NETHERITE_HOE, null, null),
    GRAVITITE_LEGGINGS(Material.NETHERITE_LEGGINGS, ArmorAttribute(8.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    GRAVITITE_PICKAXE(Material.NETHERITE_PICKAXE, null, null),
    GRAVITITE_PLATE(Material.NETHERITE_SCRAP, null, null),
    GRAVITITE_SHOVEL(Material.NETHERITE_SHOVEL, null, null),
    GRAVITITE_SWORD(Material.NETHERITE_SWORD, ToolAttribute(10.0, 1.6).toAttributeMap(), null),
    ICE_ESSENCE(Material.LIGHT_BLUE_DYE, null, null),
    ICE_SWORD(Material.DIAMOND_SWORD, ToolAttribute(11.0, 1.6).toAttributeMap(), null),
    LIGHTNING_ESSENCE(Material.BLUE_DYE, null, null),
    LIGHTNING_SWORD(Material.DIAMOND_SWORD, ToolAttribute(11.0, 1.6).toAttributeMap(), null),
    SUN_STONE(Material.NETHER_STAR, null, null),
    SUN_SWORD(Material.NETHERITE_SWORD, ToolAttribute(13.0, 1.6).toAttributeMap(), null),
    VALKYRE_AXE(Material.IRON_AXE, null, null),
    VALKYRE_BOOTS(Material.IRON_BOOTS, ArmorAttribute(3.0, 1.0, EquipmentSlot.FEET).toAttributeMap(), null),
    VALKYRE_LEGGINGS(Material.IRON_LEGGINGS, ArmorAttribute(7.5, 1.0, EquipmentSlot.LEGS).toAttributeMap(), null),
    VALKYRE_RING(Material.IRON_HELMET, ArmorAttribute(3.0, 1.0, EquipmentSlot.HEAD).toAttributeMap(), null),
    VALKYRE_SWORD(Material.IRON_SWORD, ToolAttribute(8.0, 1.6).toAttributeMap(), null),
    VALKYRE_WINGS(Material.ELYTRA, ArmorAttribute(9.0, 1.0, EquipmentSlot.CHEST).toAttributeMap(), null),
    ZANITE_AXE(Material.DIAMOND_AXE, null, null),
    ZANITE_BOOTS(Material.DIAMOND_BOOTS, ArmorAttribute(4.0, 2.3, EquipmentSlot.FEET).toAttributeMap(), null),
    ZANITE_CHESTPLATE(Material.DIAMOND_CHESTPLATE, ArmorAttribute(9.0, 2.3, EquipmentSlot.CHEST).toAttributeMap(), null),
    ZANITE_HELMET(Material.DIAMOND_HELMET, ArmorAttribute(4.0, 2.3, EquipmentSlot.HEAD).toAttributeMap(), null),
    ZANITE_HOE(Material.DIAMOND_HOE, null, null),
    ZANITE_LEGGINGS(Material.DIAMOND_LEGGINGS, ArmorAttribute(7.0, 2.3, EquipmentSlot.LEGS).toAttributeMap(), null),
    ZANITE_PICKAXE(Material.DIAMOND_PICKAXE, null, null),
    ZANITE_SHOVEL(Material.DIAMOND_SHOVEL, null, null),
    ZANITE_STONE(Material.DIAMOND, null, null),
    ZANITE_SWORD(Material.DIAMOND_SWORD, ToolAttribute(9.0, 1.6).toAttributeMap(), null),
    MAGIC_WAND(Material.IRON_HORSE_ARMOR, null, null),
    FIRE_LEAVES(Material.OAK_LEAVES, null, BlockType.FIRE_LEAVES),
    WATER_LEAVES(Material.OAK_LEAVES, null, BlockType.WATER_LEAVES),
    AIR_LEAVES(Material.OAK_LEAVES, null, BlockType.AIR_LEAVES),
    EARTH_LEAVES(Material.OAK_LEAVES, null, BlockType.EARTH_LEAVES),
    OBSIDIAN_BOOTS(Material.NETHERITE_BOOTS, ArmorAttribute(6.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    OBSIDIAN_CHESTPLATE(Material.NETHERITE_CHESTPLATE, ArmorAttribute(11.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    OBSIDIAN_HELMET(Material.NETHERITE_HELMET, ArmorAttribute(6.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    OBSIDIAN_LEGGINGS(Material.NETHERITE_LEGGINGS, ArmorAttribute(9.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    SILVER_INGOT(Material.IRON_INGOT, null, null),
    TITANIUM_INGOT(Material.IRON_INGOT, null, null),
    CRYSTAL_SHARD(Material.QUARTZ, null, null),
    PHOENIX_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(7.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    PHOENIX_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(12.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    PHOENIX_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(7.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    PHOENIX_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(10.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    AETHERIUM_BLADE(Material.DIAMOND_SWORD, ToolAttribute(12.0, 10.0).toAttributeMap(), null),
    DEMON_BLADE(Material.DIAMOND_SWORD, ToolAttribute(12.0, 3.0).toAttributeMap(), null),
    CHERRY(Material.APPLE, null, null),
    STONE_OF_HEALING(Material.CLOCK, null, null),
    STONE_OF_SPEED(Material.CLOCK, null, null),
    STONE_OF_STRENGTH(Material.CLOCK, null, null),
    LIGHTNING_DAGGER(Material.DIAMOND_SWORD, ToolAttribute(8.0, 100.0).toAttributeMap(), null),
    VALKYRE_SPEAR(Material.IRON_SWORD, ToolAttribute(10.0, 1.0).toAttributeMap(), null),
    PARACHUTE(Material.CLOCK, null, null),
    GOLDEN_PARACHUTE(Material.CLOCK, null, null),
    ENDER_SHARD(Material.QUARTZ, null, null),
    AMBER_GEM(Material.QUARTZ, null, null),
    AMETRINE_CRYSTAL(Material.QUARTZ, null, null),
    PERIDOT_SHARD(Material.QUARTZ, null, null),
    RUBY(Material.QUARTZ, null, null),
    SAPPHIRE(Material.QUARTZ, null, null),
    CINCINNASITE(Material.QUARTZ, null, null),
    THALLASIUM_INGOT(Material.IRON_INGOT, null, null),
    PENDORITE_INGOT(Material.IRON_INGOT, null, null),
    ENDERITE_INGOT(Material.IRON_INGOT, null, null),
    TUNGSTEN_INGOT(Material.IRON_INGOT, null, null),
    TIN_INGOT(Material.IRON_INGOT, null, null),
    NIKOLITE_INGOT(Material.IRON_INGOT, null, null),
    CINNEBAR_INGOT(Material.IRON_INGOT, null, null),
    IRIDIUM_INGOT(Material.IRON_INGOT, null, null),
    PYRITE_INGOT(Material.IRON_INGOT, null, null),
    SHELDONITE_INGOT(Material.IRON_INGOT, null, null),
    SODALITE_INGOT(Material.IRON_INGOT, null, null),
    SPHALERITE_INGOT(Material.IRON_INGOT, null, null),
    ZINC_INGOT(Material.IRON_INGOT, null, null),
    CINCINNASITE_INGOT(Material.IRON_INGOT, null, null),
    AETHERIUM_INGOT(Material.IRON_INGOT, null, null),
    STEEL_INGOT(Material.IRON_INGOT, null, null),
    REFINED_IRON_INGOT(Material.IRON_INGOT, null, null),
    IRIDIUM_ALLOY_INGOT(Material.IRON_INGOT, null, null),
    TERMINITE_INGOT(Material.IRON_INGOT, null, null),
    CHARGED_NIKOLITE_INGOT(Material.IRON_INGOT, null, null),
    ENDER_DUST(Material.GUNPOWDER, null, null),
    AMBER_DUST(Material.GUNPOWDER, null, null),
    THALLASIUM_DUST(Material.GUNPOWDER, null, null),
    AMETRINE_DUST(Material.GUNPOWDER, null, null),
    TUNGSTEN_DUST(Material.GUNPOWDER, null, null),
    TIN_DUST(Material.GUNPOWDER, null, null),
    NIKOLITE_DUST(Material.GUNPOWDER, null, null),
    LEAD_DUST(Material.GUNPOWDER, null, null),
    BAUXITE_DUST(Material.GUNPOWDER, null, null),
    CINNEBAR_DUST(Material.GUNPOWDER, null, null),
    GELANA_DUST(Material.GUNPOWDER, null, null),
    IRIDIUM_DUST(Material.GUNPOWDER, null, null),
    PERIDOT_DUST(Material.GUNPOWDER, null, null),
    PYRITE_DUST(Material.GUNPOWDER, null, null),
    RUBY_DUST(Material.GUNPOWDER, null, null),
    SHELDONITE_DUST(Material.GUNPOWDER, null, null),
    SAPPHIRE_DUST(Material.GUNPOWDER, null, null),
    SODALITE_DUST(Material.GUNPOWDER, null, null),
    SPHALERITE_DUST(Material.GUNPOWDER, null, null),
    ZINC_DUST(Material.GUNPOWDER, null, null),
    TERMINITE_DUST(Material.GUNPOWDER, null, null),
    PYROPE_DUST(Material.GUNPOWDER, null, null),
    CHARGED_NIKOLITE_DUST(Material.GUNPOWDER, null, null),
    STEEL_DUST(Material.GUNPOWDER, null, null),
    TUNGSTEN_NUGGET(Material.IRON_NUGGET, null, null),
    STEEL_NUGGET(Material.IRON_NUGGET, null, null),
    TIN_NUGGET(Material.IRON_NUGGET, null, null),
    NIKOLITE_NUGGET(Material.IRON_NUGGET, null, null),
    LEAD_NUGGET(Material.IRON_NUGGET, null, null),
    BAUXITE_NUGGET(Material.IRON_NUGGET, null, null),
    CINNEBAR_NUGGET(Material.IRON_NUGGET, null, null),
    GELANA_NUGGET(Material.IRON_NUGGET, null, null),
    IRIDIUM_NUGGET(Material.IRON_NUGGET, null, null),
    SODALITE_NUGGET(Material.IRON_NUGGET, null, null),
    SPHALERITE_NUGGET(Material.IRON_NUGGET, null, null),
    ZINC_NUGGET(Material.IRON_NUGGET, null, null),
    ENDER_PLATE(Material.NETHERITE_SCRAP, null, null),
    THALLASIUM_PLATE(Material.NETHERITE_SCRAP, null, null),
    ENDERITE_PLATE(Material.NETHERITE_SCRAP, null, null),
    TUNGSTEN_PLATE(Material.NETHERITE_SCRAP, null, null),
    TIN_PLATE(Material.NETHERITE_SCRAP, null, null),
    NIKOLITE_PLATE(Material.NETHERITE_SCRAP, null, null),
    LEAD_PLATE(Material.NETHERITE_SCRAP, null, null),
    BAUXITE_PLATE(Material.NETHERITE_SCRAP, null, null),
    CINNEBAR_PLATE(Material.NETHERITE_SCRAP, null, null),
    GELANA_PLATE(Material.NETHERITE_SCRAP, null, null),
    IRIDIUM_PLATE(Material.NETHERITE_SCRAP, null, null),
    PERIDOT_PLATE(Material.NETHERITE_SCRAP, null, null),
    PYRITE_PLATE(Material.NETHERITE_SCRAP, null, null),
    RUBY_PLATE(Material.NETHERITE_SCRAP, null, null),
    SHELDONITE_PLATE(Material.NETHERITE_SCRAP, null, null),
    SODALITE_PLATE(Material.NETHERITE_SCRAP, null, null),
    SPHALERITE_PLATE(Material.NETHERITE_SCRAP, null, null),
    ZINC_PLATE(Material.NETHERITE_SCRAP, null, null),
    AETHERIUM_PLATE(Material.NETHERITE_SCRAP, null, null),
    TERMINITE_PLATE(Material.NETHERITE_SCRAP, null, null),
    STEEL_PLATE(Material.NETHERITE_SCRAP, null, null),
    IRIDIUM_ALLOY_PLATE(Material.NETHERITE_SCRAP, null, null),
    RAW_PENDORITE(Material.RAW_IRON, null, null),
    RAW_TIN(Material.RAW_IRON, null, null),
    RAW_NIKOLITE(Material.RAW_IRON, null, null),
    RAW_LEAD(Material.RAW_IRON, null, null),
    RAW_BAUXITE(Material.RAW_IRON, null, null),
    RAW_CINNEBAR(Material.RAW_IRON, null, null),
    RAW_GELANA(Material.RAW_IRON, null, null),
    RAW_IRIDIUM(Material.RAW_IRON, null, null),
    RAW_PYRITE(Material.RAW_IRON, null, null),
    RAW_ZINC(Material.RAW_IRON, null, null),
    THALLASIUM_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    THALLASIUM_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    THALLASIUM_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    THALLASIUM_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    PENDORITE_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    PENDORITE_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    PENDORITE_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    PENDORITE_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    LEAD_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    LEAD_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    LEAD_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    LEAD_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    BAUXITE_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    BAUXITE_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    BAUXITE_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    BAUXITE_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    RUBY_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    RUBY_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    RUBY_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    RUBY_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    AETHERIUM_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    AETHERIUM_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    AETHERIUM_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    AETHERIUM_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    TERMINITE_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    TERMINITE_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    TERMINITE_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    TERMINITE_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    ENDERITE_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    ENDERITE_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    ENDERITE_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    ENDERITE_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    PYRITE_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    PYRITE_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    PYRITE_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    PYRITE_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    SODALITE_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    SODALITE_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    SODALITE_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    SODALITE_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    TIN_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    TIN_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    TIN_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    TIN_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    CINNEBAR_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    CINNEBAR_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    CINNEBAR_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    CINNEBAR_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    AURORA_HELMET(Material.GOLDEN_HELMET, ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    AURORA_CHESTPLATE(Material.GOLDEN_CHESTPLATE, ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    AURORA_LEGGINGS(Material.GOLDEN_LEGGINGS, ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    AURORA_BOOTS(Material.GOLDEN_BOOTS, ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    ENDER_SWORD(Material.IRON_SWORD, ToolAttribute(10.0, 1.0).toAttributeMap(), null),
    ENDERITE_SWORD(Material.IRON_SWORD, ToolAttribute(10.0, 1.0).toAttributeMap(), null),
    TUNGSTEN_SWORD(Material.IRON_SWORD, ToolAttribute(10.0, 1.0).toAttributeMap(), null),
    RUBY_SWORD(Material.IRON_SWORD, ToolAttribute(10.0, 1.0).toAttributeMap(), null),
    SAPPHIRE_SWORD(Material.IRON_SWORD, ToolAttribute(10.0, 1.0).toAttributeMap(), null),
    AETHERIUM_SWORD(Material.IRON_SWORD, ToolAttribute(10.0, 1.0).toAttributeMap(), null),
    ENDER_ORE(Material.STONE, null, BlockType.ENDER_ORE),
    AMBER_ORE(Material.STONE, null, BlockType.AMBER_ORE),
    THALLASIUM_ORE(Material.STONE, null, BlockType.THALLASIUM_ORE),
    AMETRINE_ORE(Material.STONE, null, BlockType.AMETRINE_ORE),
    PENDORITE_ORE(Material.STONE, null, BlockType.PENDORITE_ORE),
    NETHER_EMERALD_ORE(Material.STONE, null, BlockType.NETHER_EMERALD_ORE),
    ENDERITE_ORE(Material.STONE, null, BlockType.ENDERITE_ORE),
    CRACKED_ENDERITE_ORE(Material.STONE, null, BlockType.CRACKED_ENDERITE_ORE),
    TUNGSTEN_ORE(Material.STONE, null, BlockType.TUNGSTEN_ORE),
    TIN_ORE(Material.STONE, null, BlockType.TIN_ORE),
    NIKOLITE_ORE(Material.STONE, null, BlockType.NIKOLITE_ORE),
    LEAD_ORE(Material.STONE, null, BlockType.LEAD_ORE),
    BAUXITE_ORE(Material.STONE, null, BlockType.BAUXITE_ORE),
    CINNEBAR_ORE(Material.STONE, null, BlockType.CINNEBAR_ORE),
    GALANA_ORE(Material.STONE, null, BlockType.GALANA_ORE),
    IRIDIUM_ORE(Material.STONE, null, BlockType.IRIDIUM_ORE),
    PERIDOT_ORE(Material.STONE, null, BlockType.PERIDOT_ORE),
    PYRITE_ORE(Material.STONE, null, BlockType.PYRITE_ORE),
    RUBY_ORE(Material.STONE, null, BlockType.RUBY_ORE),
    SHELDONITE_ORE(Material.STONE, null, BlockType.SHELDONITE_ORE),
    SAPPHIRE_ORE(Material.STONE, null, BlockType.SAPPHIRE_ORE),
    SODALITE_ORE(Material.STONE, null, BlockType.SODALITE_ORE),
    SPHALERITE_ORE(Material.STONE, null, BlockType.SPHALERITE_ORE),
    ZINC_ORE(Material.STONE, null, BlockType.ZINC_ORE),
    CINCINNASITE_ORE(Material.STONE, null, BlockType.CINCINNASITE_ORE),
    AETHERIUM_ORE(Material.STONE, null, BlockType.AETHERIUM_ORE),
    TERMINITE_ORE(Material.STONE, null, BlockType.TERMINITE_ORE),
    DEEPSLATE_SILVER_ORE(Material.STONE, null, BlockType.DEEPSLATE_SILVER_ORE),
    DEEPSLATE_TUNGSTEN_ORE(Material.STONE, null, BlockType.DEEPSLATE_TUNGSTEN_ORE),
    DEEPSLATE_LEAD_ORE(Material.STONE, null, BlockType.DEEPSLATE_LEAD_ORE),
    DEEPSLATE_NIKOLITE_ORE(Material.STONE, null, BlockType.DEEPSLATE_NIKOLITE_ORE),
    DEEPSLATE_TIN_ORE(Material.STONE, null, BlockType.DEEPSLATE_TIN_ORE),
    DEEPSLATE_BAUXITE_ORE(Material.STONE, null, BlockType.DEEPSLATE_BAUXITE_ORE),
    DEEPSLATE_GALANA_ORE(Material.STONE, null, BlockType.DEEPSLATE_GALANA_ORE),
    DEEPSLATE_IRIDIUM_ORE(Material.STONE, null, BlockType.DEEPSLATE_IRIDIUM_ORE),
    DEEPSLATE_PERIDOT_ORE(Material.STONE, null, BlockType.DEEPSLATE_PERIDOT_ORE),
    DEEPSLATE_ZINC_ORE(Material.STONE, null, BlockType.DEEPSLATE_ZINC_ORE),
    NETHER_ZINC_ORE(Material.STONE, null, BlockType.NETHER_ZINC_ORE),
    NETHER_TUNGSTEN_ORE(Material.STONE, null, BlockType.NETHER_TUNGSTEN_ORE),
    NETHER_LEAD_ORE(Material.STONE, null, BlockType.NETHER_LEAD_ORE),
    NETHER_BAUXITE_ORE(Material.STONE, null, BlockType.NETHER_BAUXITE_ORE),
    NETHER_RUBY_ORE(Material.STONE, null, BlockType.NETHER_RUBY_ORE),
    NETHER_SAPPHIRE_ORE(Material.STONE, null, BlockType.NETHER_SAPPHIRE_ORE),
    NETHER_TIN_ORE(Material.STONE, null, BlockType.NETHER_TIN_ORE),
    END_NIKOLITE_ORE(Material.STONE, null, BlockType.END_NIKOLITE_ORE),
    END_IRIDIUM_ORE(Material.STONE, null, BlockType.END_IRIDIUM_ORE),
    END_TUNGSTEN_ORE(Material.STONE, null, BlockType.END_TUNGSTEN_ORE),
    END_TIN_ORE(Material.STONE, null, BlockType.END_TIN_ORE),
    END_LEAD_ORE(Material.STONE, null, BlockType.END_LEAD_ORE),
    END_BAUXITE_ORE(Material.STONE, null, BlockType.END_BAUXITE_ORE),
    END_CINNEBAR_ORE(Material.STONE, null, BlockType.END_CINNEBAR_ORE),
    END_GALANA_ORE(Material.STONE, null, BlockType.END_GALANA_ORE),
    CRACKED_AETHER_STONE(Material.STONE, null, BlockType.CRACKED_AETHER_STONE),
    AETHER_QUARTZ_PILLAR(Material.STONE, null, BlockType.AETHER_QUARTZ_PILLAR),
    AETHER_QUARTZ_BLOCK(Material.STONE, null, BlockType.AETHER_QUARTZ_BLOCK),
    ZANITE_BLOCK(Material.STONE, null, BlockType.ZANITE_BLOCK),
    GRAVITITE_BLOCK(Material.STONE, null, BlockType.GRAVITITE_BLOCK),
    CHISELED_AETHER_STONE_BRICKS(Material.STONE, null, BlockType.CHISELED_AETHER_STONE_BRICKS),
    AETHER_THIN_QUARTZ_PILLAR(Material.STONE, null, BlockType.AETHER_THIN_QUARTZ_PILLAR),
    VALKYRE_CHESTPLATE(Material.IRON_CHESTPLATE, ArmorAttribute(10.0, 1.0, EquipmentSlot.CHEST).toAttributeMap(), null),
    VALKYRE_HELMET(Material.IRON_HELMET, ArmorAttribute(5.0, 1.0, EquipmentSlot.HEAD).toAttributeMap(), null),
    POLISHED_OBSIDIAN(Material.STONE, null, BlockType.POLISHED_OBSIDIAN),
    SMOOTH_OBSIDIAN(Material.STONE, null, BlockType.SMOOTH_OBSIDIAN);

    private val displayName = inst().getLanguageManager().getComponent("items.$name.name")
    private val description = inst().getLanguageManager().getComponentList("items.$name.description")

    val modelID = this.ordinal + modelIdOffset

    fun getItemStack(): ItemStack {
        return getItemStack(1)
    }

    fun getItemStack(amount: Int): ItemStack {
        val itemStack = ItemStack(material)
        val itemMeta = itemStack.itemMeta
        itemMeta.displayName(displayName.decoration(TextDecoration.ITALIC, false))

        val descriptionList = ArrayList<Component>()
        for (component in description) {
            descriptionList.add(component.decoration(TextDecoration.ITALIC, false))
        }
        itemMeta.lore(descriptionList)

        itemMeta.setCustomModelData(modelID)
        if (attributeModifiers != null) {
            itemMeta.attributeModifiers = attributeModifiers
        }
        itemStack.itemMeta = itemMeta
        itemStack.amount = amount

        itemStack.setDataString(MPP_ITEM_TAG_KEY, name)
        return itemStack
    }

    companion object {

        @JvmStatic
        fun fromTag(itemStack: ItemStack?): ItemType? {
            if (itemStack == null) return null
            return try {
                val itemTag = itemStack.getDataString(MPP_ITEM_TAG_KEY) ?: return null
                valueOf(itemTag)
            } catch (e: IllegalArgumentException) {
                null
            }
        }

        @JvmStatic
        fun fromPlaceBlockType(placeBlockType: BlockType): ItemType? {
            return values().firstOrNull {
                it.placeBlockType == placeBlockType
            }
        }
    }

    private abstract class CustomAttribute {
        fun toAttributeMap(
            attribute1: Attribute, value1: Double, slot1: EquipmentSlot,
            attribute2: Attribute, value2: Double, slot2: EquipmentSlot,
        ): Multimap<Attribute, AttributeModifier> {
            val attributeMap: Multimap<Attribute, AttributeModifier> = ArrayListMultimap.create()
            attributeMap.put(
                attribute1,
                AttributeModifier(
                    UUID.randomUUID(),
                    attribute1.key.toString(),
                    value1,
                    AttributeModifier.Operation.ADD_NUMBER,
                    slot1
                )
            )
            attributeMap.put(
                attribute2,
                AttributeModifier(
                    UUID.randomUUID(),
                    attribute2.key.toString(),
                    value2,
                    AttributeModifier.Operation.ADD_NUMBER,
                    slot2
                )
            )
            return attributeMap
        }
    }

    private class ToolAttribute(val attackDamage: Double, val attackSpeed: Double) : CustomAttribute() {

        fun toAttributeMap(): Multimap<Attribute, AttributeModifier> {
            return toAttributeMap(
                Attribute.GENERIC_ATTACK_DAMAGE, attackDamage, EquipmentSlot.HAND,
                Attribute.GENERIC_ATTACK_SPEED, attackSpeed, EquipmentSlot.HAND
            )
        }
    }

    private class ArmorAttribute(val armor: Double, val armorToughness: Double, val slot: EquipmentSlot) :
        CustomAttribute() {

        fun toAttributeMap(): Multimap<Attribute, AttributeModifier> {
            return toAttributeMap(
                Attribute.GENERIC_ARMOR, armor, slot,
                Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughness, slot
            )
        }
    }
}