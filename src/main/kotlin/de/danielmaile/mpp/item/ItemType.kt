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
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import java.util.*

const val MPP_ITEM_TAG_KEY = "mpp_item"

// don't change this, otherwise existing items will break
private const val MODEL_ID_OFFSET = 1000

enum class ItemType(
    private val material: Material?,
    private val attributeModifiers: Multimap<Attribute, AttributeModifier>?,
    val placeBlockType: BlockType?,
    private val color: Color?
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
    FIRE_SWORD(Material.DIAMOND_SWORD, ToolAttribute(9.0, 1.6).toAttributeMap(), null),
    GRAVITITE_AXE(Material.NETHERITE_AXE, null, null),
    GRAVITITE_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.GRAVITITE.color),
    GRAVITITE_CHESTPLATE(ArmorAttribute(10.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.GRAVITITE.color),
    GRAVITITE_HELMET(ArmorAttribute(5.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.GRAVITITE.color),
    GRAVITITE_HOE(Material.NETHERITE_HOE, null, null),
    GRAVITITE_LEGGINGS(ArmorAttribute(8.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.GRAVITITE.color),
    GRAVITITE_PICKAXE(Material.NETHERITE_PICKAXE, null, null),
    GRAVITITE_PLATE(Material.NETHERITE_SCRAP, null, null),
    GRAVITITE_SHOVEL(Material.NETHERITE_SHOVEL, null, null),
    GRAVITITE_SWORD(Material.NETHERITE_SWORD, ToolAttribute(9.0, 1.6).toAttributeMap(), null),
    ICE_ESSENCE(Material.LIGHT_BLUE_DYE, null, null),
    ICE_SWORD(Material.DIAMOND_SWORD, ToolAttribute(8.0, 1.6).toAttributeMap(), null),
    LIGHTNING_ESSENCE(Material.BLUE_DYE, null, null),
    LIGHTNING_SWORD(Material.DIAMOND_SWORD, ToolAttribute(9.0, 1.6).toAttributeMap(), null),
    SUN_STONE(Material.NETHER_STAR, null, null),
    SUN_SWORD(Material.NETHERITE_SWORD, ToolAttribute(12.0, 1.6).toAttributeMap(), null),
    VALKYRE_AXE(Material.IRON_AXE, null, null),
    VALKYRE_BOOTS(ArmorAttribute(3.0, 1.0, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.VALKYRIE.color),
    VALKYRE_LEGGINGS(ArmorAttribute(7.5, 1.0, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.VALKYRIE.color),
    VALKYRE_RING(ArmorAttribute(3.0, 1.0, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.VALKYRIE.color),
    VALKYRE_SWORD(Material.IRON_SWORD, ToolAttribute(13.0, 1.6).toAttributeMap(), null),
    VALKYRE_WINGS(ArmorAttribute(9.0, 1.0, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.VALKYRIE.color),
    ZANITE_AXE(Material.DIAMOND_AXE, null, null),
    ZANITE_BOOTS(ArmorAttribute(4.0, 2.3, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.ZANITE.color),
    ZANITE_CHESTPLATE(ArmorAttribute(9.0, 2.3, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.ZANITE.color),
    ZANITE_HELMET(ArmorAttribute(4.0, 2.3, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.ZANITE.color),
    ZANITE_HOE(Material.DIAMOND_HOE, null, null),
    ZANITE_LEGGINGS(ArmorAttribute(7.0, 2.3, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.ZANITE.color),
    ZANITE_PICKAXE(Material.DIAMOND_PICKAXE, null, null),
    ZANITE_SHOVEL(Material.DIAMOND_SHOVEL, null, null),
    ZANITE_STONE(Material.DIAMOND, null, null),
    ZANITE_SWORD(Material.DIAMOND_SWORD, ToolAttribute(8.0, 1.6).toAttributeMap(), null),
    MAGIC_WAND(Material.IRON_HORSE_ARMOR, null, null),
    FIRE_LEAVES(Material.OAK_LEAVES, null, BlockType.FIRE_LEAVES),
    WATER_LEAVES(Material.OAK_LEAVES, null, BlockType.WATER_LEAVES),
    AIR_LEAVES(Material.OAK_LEAVES, null, BlockType.AIR_LEAVES),
    EARTH_LEAVES(Material.OAK_LEAVES, null, BlockType.EARTH_LEAVES),
    OBSIDIAN_BOOTS(ArmorAttribute(6.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.OBSIDIAN.color),
    OBSIDIAN_CHESTPLATE(ArmorAttribute(11.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.OBSIDIAN.color),
    OBSIDIAN_HELMET(ArmorAttribute(6.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.OBSIDIAN.color),
    OBSIDIAN_LEGGINGS(ArmorAttribute(9.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.OBSIDIAN.color),
    SILVER_INGOT(Material.IRON_INGOT, null, null),
    TITANIUM_INGOT(Material.IRON_INGOT, null, null),
    CRYSTAL_SHARD(Material.QUARTZ, null, null),
    PHOENIX_HELMET(ArmorAttribute(7.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.PHOENIX.color),
    PHOENIX_CHESTPLATE(ArmorAttribute(12.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.PHOENIX.color),
    PHOENIX_LEGGINGS(ArmorAttribute(7.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.PHOENIX.color),
    PHOENIX_BOOTS(ArmorAttribute(10.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.PHOENIX.color),
    DEMON_SWORD(Material.DIAMOND_SWORD, ToolAttribute(14.0, 3.0).toAttributeMap(), null),
    CHERRY(Material.APPLE, null, null),
    STONE_OF_HEALING(Material.CLOCK, null, null),
    STONE_OF_SPEED(Material.CLOCK, null, null),
    STONE_OF_STRENGTH(Material.CLOCK, null, null),
    LIGHTNING_DAGGER(Material.DIAMOND_SWORD, ToolAttribute(8.0, 10.0).toAttributeMap(), null),
    VALKYRE_SPEAR(Material.IRON_SWORD, ToolAttribute(15.0, -2.5).toAttributeMap(), null),
    PARACHUTE(Material.CLOCK, null, null),
    GOLDEN_PARACHUTE(Material.CLOCK, null, null),
    ENDER_SHARD(Material.QUARTZ, null, null),
    AMBER_GEM(Material.QUARTZ, null, null),
    AMETRINE_CRYSTAL(Material.QUARTZ, null, null),
    PERIDOT_SHARD(Material.QUARTZ, null, null),
    RUBY(Material.QUARTZ, null, null),
    SAPPHIRE(Material.QUARTZ, null, null),
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
    GALANA_DUST(Material.GUNPOWDER, null, null),
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
    GALANA_NUGGET(Material.IRON_NUGGET, null, null),
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
    GALANA_PLATE(Material.NETHERITE_SCRAP, null, null),
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
    RAW_GALANA(Material.RAW_IRON, null, null),
    RAW_IRIDIUM(Material.RAW_IRON, null, null),
    RAW_PYRITE(Material.RAW_IRON, null, null),
    RAW_ZINC(Material.RAW_IRON, null, null),
    THALLASIUM_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.THALLASIUM.color),
    THALLASIUM_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.THALLASIUM.color),
    THALLASIUM_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.THALLASIUM.color),
    THALLASIUM_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.THALLASIUM.color),
    PENDORITE_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.PENDORITE.color),
    PENDORITE_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.PENDORITE.color),
    PENDORITE_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.PENDORITE.color),
    PENDORITE_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.PENDORITE.color),
    LEAD_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.LEAD.color),
    LEAD_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.LEAD.color),
    LEAD_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.LEAD.color),
    LEAD_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.LEAD.color),
    BAUXITE_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.BAUXITE.color),
    BAUXITE_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.BAUXITE.color),
    BAUXITE_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.BAUXITE.color),
    BAUXITE_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.BAUXITE.color),
    RUBY_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.RUBY.color),
    RUBY_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.RUBY.color),
    RUBY_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.RUBY.color),
    RUBY_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.RUBY.color),
    AETHERIUM_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.AETHERIUM.color),
    AETHERIUM_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.AETHERIUM.color),
    AETHERIUM_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.AETHERIUM.color),
    AETHERIUM_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.AETHERIUM.color),
    TERMINITE_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.TERMINITE.color),
    TERMINITE_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.TERMINITE.color),
    TERMINITE_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.TERMINITE.color),
    TERMINITE_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.TERMINITE.color),
    ENDERITE_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.ENDERITE.color),
    ENDERITE_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.ENDERITE.color),
    ENDERITE_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.ENDERITE.color),
    ENDERITE_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.ENDERITE.color),
    PYRITE_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.PYRITE.color),
    PYRITE_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.PYRITE.color),
    PYRITE_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.PYRITE.color),
    PYRITE_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.PYRITE.color),
    SODALITE_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.SODALITE.color),
    SODALITE_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.SODALITE.color),
    SODALITE_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.SODALITE.color),
    SODALITE_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.SODALITE.color),
    TIN_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.TIN.color),
    TIN_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.TIN.color),
    TIN_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.TIN.color),
    TIN_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.TIN.color),
    CINNEBAR_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.CINNEBAR.color),
    CINNEBAR_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.CINNEBAR.color),
    CINNEBAR_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.CINNEBAR.color),
    CINNEBAR_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.CINNEBAR.color),
    AURORA_HELMET(ArmorAttribute(3.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.AURORA.color),
    AURORA_CHESTPLATE(ArmorAttribute(8.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.AURORA.color),
    AURORA_LEGGINGS(ArmorAttribute(3.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), ArmorSet.AURORA.color),
    AURORA_BOOTS(ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), ArmorSet.AURORA.color),
    ENDER_SWORD(Material.IRON_SWORD, ToolAttribute(7.0, 1.0).toAttributeMap(), null),
    ENDERITE_SWORD(Material.IRON_SWORD, ToolAttribute(10.0, 1.0).toAttributeMap(), null),
    TUNGSTEN_SWORD(Material.IRON_SWORD, ToolAttribute(9.0, 1.0).toAttributeMap(), null),
    RUBY_SWORD(Material.IRON_SWORD, ToolAttribute(7.0, 1.0).toAttributeMap(), null),
    SAPPHIRE_SWORD(Material.IRON_SWORD, ToolAttribute(7.0, 1.0).toAttributeMap(), null),
    AETHERIUM_SWORD(Material.IRON_SWORD, ToolAttribute(12.0, 1.0).toAttributeMap(), null),
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
    VALKYRE_CHESTPLATE(ArmorAttribute(10.0, 1.0, EquipmentSlot.CHEST).toAttributeMap(), ArmorSet.VALKYRIE.color),
    VALKYRE_HELMET(ArmorAttribute(5.0, 1.0, EquipmentSlot.HEAD).toAttributeMap(), ArmorSet.VALKYRIE.color),
    POLISHED_OBSIDIAN(Material.STONE, null, BlockType.POLISHED_OBSIDIAN),
    SMOOTH_OBSIDIAN(Material.STONE, null, BlockType.SMOOTH_OBSIDIAN),
    PYROPE_PLATE(Material.NETHERITE_SCRAP, null, null),
    ENDER_AXE(Material.DIAMOND_AXE, null, null),
    ENDER_PICKAXE(Material.DIAMOND_PICKAXE, null, null),
    ENDER_SHOVEL(Material.DIAMOND_SHOVEL, null, null),
    ENDER_HOE(Material.DIAMOND_HOE, null, null),
    ENDERITE_AXE(Material.NETHERITE_AXE, null, null),
    ENDERITE_PICKAXE(Material.NETHERITE_PICKAXE, null, null),
    ENDERITE_SHOVEL(Material.NETHERITE_SHOVEL, null, null),
    ENDERITE_HOE(Material.NETHERITE_HOE, null, null),
    TUNGSTEN_AXE(Material.DIAMOND_AXE, null, null),
    TUNGSTEN_PICKAXE(Material.DIAMOND_PICKAXE, null, null),
    TUNGSTEN_SHOVEL(Material.DIAMOND_SHOVEL, null, null),
    TUNGSTEN_HOE(Material.DIAMOND_HOE, null, null),
    RUBY_AXE(Material.DIAMOND_AXE, null, null),
    RUBY_PICKAXE(Material.DIAMOND_PICKAXE, null, null),
    RUBY_SHOVEL(Material.DIAMOND_SHOVEL, null, null),
    RUBY_HOE(Material.DIAMOND_HOE, null, null),
    SAPPHIRE_AXE(Material.DIAMOND_AXE, null, null),
    SAPPHIRE_PICKAXE(Material.DIAMOND_PICKAXE, null, null),
    SAPPHIRE_SHOVEL(Material.DIAMOND_SHOVEL, null, null),
    SAPPHIRE_HOE(Material.DIAMOND_HOE, null, null),
    AETHERIUM_AXE(Material.NETHERITE_AXE, null, null),
    AETHERIUM_PICKAXE(Material.NETHERITE_PICKAXE, null, null),
    AETHERIUM_SHOVEL(Material.NETHERITE_SHOVEL, null, null),
    AETHERIUM_HOE(Material.NETHERITE_HOE, null, null),
    HELL_STONE(Material.STONE, null, BlockType.HELL_STONE),
    CRACKED_HELL_STONE(Material.STONE, null, BlockType.CRACKED_HELL_STONE),
    HELL_STONE_BRICKS(Material.STONE, null, BlockType.HELL_STONE_BRICKS),
    CRACKED_HELL_STONE_BRICKS(Material.STONE, null, BlockType.CRACKED_HELL_STONE_BRICKS),
    SMOOTH_HELL_STONE_BRICKS(Material.STONE, null, BlockType.SMOOTH_HELL_STONE_BRICKS),
    HELL_DIAMOND_BLOCK(Material.STONE, null, BlockType.HELL_DIAMOND_BLOCK),
    HELL_DIAMOND(Material.STONE, null, null),
    HELL_DIRT(Material.STONE, null, BlockType.HELL_DIRT),
    HELL_GOLD_ORE(Material.STONE, null, BlockType.HELL_GOLD_ORE),
    HELL_GRASS_BLOCK(Material.STONE, null, BlockType.HELL_GRASS_BLOCK),
    HELL_LOG(Material.STONE, null, BlockType.HELL_LOG),
    HELL_OBSIDIAN(Material.STONE, null, BlockType.HELL_OBSIDIAN),
    LAVA_STONE(Material.STONE, null, BlockType.LAVA_STONE),
    LAVA_STONE_BRICKS(Material.STONE, null, BlockType.LAVA_STONE_BRICKS),
    ORANGE_HELL_LEAVES(Material.OAK_LEAVES, null, BlockType.ORANGE_HELL_LEAVES),
    RED_HELL_LEAVES(Material.STONE, null, BlockType.RED_HELL_LEAVES),
    YELLOW_HELL_LEAVES(Material.STONE, null, BlockType.YELLOW_HELL_LEAVES),
    PLATIN_BLOCK(Material.STONE, null, BlockType.PLATIN_BLOCK),
    PLATIN_INGOT(Material.STONE, null, null),
    PLATIN_ORE(Material.STONE, null, BlockType.PLATIN_ORE),
    SILVER_BLOCK(Material.STONE, null, BlockType.SILVER_BLOCK),
    TITANIUM_BLOCK(Material.STONE, null, BlockType.TITANIUM_BLOCK),
    HELL_TRIDENT(Material.TRIDENT, ToolAttribute(12.0, 1.0).toAttributeMap(), null),
    HELL_STONE_PICKAXE(Material.STONE_PICKAXE, null, null),
    HELL_STONE_SWORD(Material.STONE_SWORD, ToolAttribute(6.0, 1.0).toAttributeMap(), null),
    HELL_CROWN(Material.NETHERITE_HELMET, ArmorAttribute(15.0, 1.0, EquipmentSlot.HEAD).toAttributeMap(), null),
    AETHER_LEAVES(Material.STONE, null, BlockType.AETHER_LEAVES),
    HELL_PLANKS(Material.OAK_PLANKS, null, BlockType.HELL_PLANKS),
    EMERALD_SHARD(Material.NETHERITE_SCRAP, null, null),
    AURORA_SHARD(Material.NETHERITE_SCRAP, null, null);

    private val displayName: Component
        get() = inst().getLanguageManager().getComponent("items.$name.name")
    private val description: List<Component>
        get() = inst().getLanguageManager().getComponentList("items.$name.description")

    val modelID = this.ordinal + MODEL_ID_OFFSET

    // constructors for items and custom armor
    constructor(
        material: Material,
        attributeModifiers: Multimap<Attribute, AttributeModifier>?,
        placeBlockType: BlockType?
    ) : this(material, attributeModifiers, placeBlockType, null)

    constructor(attributeModifiers: Multimap<Attribute, AttributeModifier>?, color: Color) : this(null, attributeModifiers, null, color)

    fun getMaterial(): Material {
        if (material != null) {
            return material
        }

        // if no material is given custom item has to be an armor item
        // return the correct armor piece depending on equipment slot
        if (attributeModifiers == null) {
            throw IllegalArgumentException("Item ${this.name} has no material assigned but isn't an armor piece!")
        }

        val genericArmor = attributeModifiers.get(Attribute.GENERIC_ARMOR)
        if (genericArmor.isEmpty()) {
            throw IllegalArgumentException("Item ${this.name} has no material assigned but isn't an armor piece!")
        }

        val armorModifier = genericArmor.first() as AttributeModifier
        return when (armorModifier.slot) {
            EquipmentSlot.HEAD -> Material.LEATHER_HELMET
            EquipmentSlot.CHEST -> Material.LEATHER_CHESTPLATE
            EquipmentSlot.LEGS -> Material.LEATHER_LEGGINGS
            EquipmentSlot.FEET -> Material.LEATHER_BOOTS
            else -> throw IllegalArgumentException("Armor item ${this.name} has an invalid equipment slot: ${armorModifier.slot}!")
        }
    }

    fun getItemStack(): ItemStack {
        return getItemStack(1)
    }

    fun getItemStack(amount: Int): ItemStack {
        val itemStack = ItemStack(getMaterial())
        val itemMeta = itemStack.itemMeta

        // set item name and remove default italic decoration
        itemMeta.displayName(displayName.decoration(TextDecoration.ITALIC, false))

        // set item lore and remove default italic decoration
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

        // assume that item type is armor, when color is present
        if (color != null) {
            val armorMeta = itemMeta as LeatherArmorMeta
            armorMeta.setColor(color)
            itemStack.itemMeta = armorMeta
        }

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
            attribute1: Attribute,
            value1: Double,
            slot1: EquipmentSlot,
            attribute2: Attribute,
            value2: Double,
            slot2: EquipmentSlot
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
                Attribute.GENERIC_ATTACK_DAMAGE,
                attackDamage,
                EquipmentSlot.HAND,
                Attribute.GENERIC_ATTACK_SPEED,
                attackSpeed,
                EquipmentSlot.HAND
            )
        }
    }

    private class ArmorAttribute(val armor: Double, val armorToughness: Double, val slot: EquipmentSlot) :
        CustomAttribute() {

        fun toAttributeMap(): Multimap<Attribute, AttributeModifier> {
            return toAttributeMap(
                Attribute.GENERIC_ARMOR,
                armor,
                slot,
                Attribute.GENERIC_ARMOR_TOUGHNESS,
                armorToughness,
                slot
            )
        }
    }
}
