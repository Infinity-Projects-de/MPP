package de.danielmaile.mpp.item

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
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
import kotlin.collections.ArrayList

const val AETHER_ITEM_TAG_KEY = "aether_item"

enum class ItemType(
    private val modelID: Int,
    val material: Material,
    private val attributeModifiers: Multimap<Attribute, AttributeModifier>?,
    val placeMaterial: Material?
) {

    AETHER_ACACIA_LOG(4005, Material.ACACIA_LOG, null, Material.ACACIA_LOG),
    AETHER_ACACIA_PLANKS(4011, Material.ACACIA_PLANKS, null, Material.ACACIA_PLANKS),
    AETHER_BIRCH_LOG(4003, Material.BIRCH_LOG, null, Material.BIRCH_LOG),
    AETHER_BIRCH_PLANKS(4009, Material.BIRCH_PLANKS, null, Material.BIRCH_PLANKS),
    AETHER_DARK_OAK_LOG(4006, Material.DARK_OAK_LOG, null, Material.DARK_OAK_LOG),
    AETHER_DARK_OAK_PLANKS(4012, Material.DARK_OAK_PLANKS, null, Material.DARK_OAK_PLANKS),
    AETHER_JUNGLE_LOG(4004, Material.JUNGLE_LOG, null, Material.JUNGLE_LOG),
    AETHER_JUNGLE_PLANKS(4010, Material.JUNGLE_PLANKS, null, Material.JUNGLE_PLANKS),
    AETHER_OAK_LOG(4001, Material.OAK_LOG, null, Material.OAK_LOG),
    AETHER_OAK_PLANKS(4007, Material.OAK_PLANKS, null, Material.OAK_PLANKS),
    AETHER_SPRUCE_LOG(4002, Material.SPRUCE_LOG, null, Material.SPRUCE_LOG),
    AETHER_SPRUCE_PLANKS(4008, Material.SPRUCE_PLANKS, null, Material.SPRUCE_PLANKS),
    AETHER_STICK(1001, Material.STICK, null, null),
    AETHER_STONE(4015, Material.STONE, null, Material.STONE),
    AETHER_STONE_AXE(2011, Material.STONE_AXE, null, null),
    AETHER_STONE_HOE(2010, Material.STONE_HOE, null, null),
    AETHER_STONE_PICKAXE(2012, Material.STONE_PICKAXE, null, null),
    AETHER_STONE_SHOVEL(2013, Material.STONE_SHOVEL, null, null),
    AETHER_STONE_SWORD(2002, Material.STONE_SWORD, null, null),
    AETHER_WOODEN_AXE(2007, Material.WOODEN_AXE, null, null),
    AETHER_WOODEN_HOE(2006, Material.WOODEN_HOE, null, null),
    AETHER_WOODEN_PICKAXE(2008, Material.WOODEN_PICKAXE, null, null),
    AETHER_WOODEN_SHOVEL(2009, Material.WOODEN_SHOVEL, null, null),
    AETHER_WOODEN_SWORD(2001, Material.WOODEN_SWORD, null, null),
    FIRE_ESSENCE(1003, Material.ORANGE_DYE, null, null),
    FLAME_SWORD(2200, Material.DIAMOND_SWORD, ToolAttribute(10.0, 1.6).toAttributeMap(), null),
    GRAVITITE_AXE(2020, Material.NETHERITE_AXE, null, null),
    GRAVITITE_BOOTS(3012, Material.NETHERITE_BOOTS,  ArmorAttribute(3.9, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    GRAVITITE_CHESTPLATE(3010, Material.NETHERITE_CHESTPLATE, ArmorAttribute(10.4, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    GRAVITITE_HELMET(3009, Material.NETHERITE_HELMET, ArmorAttribute(3.9, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    GRAVITITE_HOE(2019, Material.NETHERITE_HOE, null, null),
    GRAVITITE_LEGGINGS(3011, Material.NETHERITE_LEGGINGS, ArmorAttribute(7.8, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    GRAVITITE_ORE(4014, Material.ANCIENT_DEBRIS,null, Material.ANCIENT_DEBRIS),
    GRAVITITE_PICKAXE(2021, Material.NETHERITE_PICKAXE, null, null),
    GRAVITITE_PLATE(1006, Material.NETHERITE_SCRAP, null, null),
    GRAVITITE_SHOVEL(2022, Material.NETHERITE_SHOVEL, null, null),
    GRAVITITE_SWORD(2005, Material.NETHERITE_SWORD, ToolAttribute(8.0, 1.6).toAttributeMap(), null),
    ICE_ESSENCE(1004, Material.LIGHT_BLUE_DYE, null, null),
    ICE_SWORD(2300, Material.DIAMOND_SWORD, ToolAttribute(9.0, 1.6).toAttributeMap(), null),
    LIGHTNING_ESSENCE(1002, Material.BLUE_DYE, null, null),
    LIGHTNING_SWORD(2100, Material.DIAMOND_SWORD, ToolAttribute(9.0, 1.6).toAttributeMap(), null),
    SUN_STONE(1007, Material.NETHER_STAR, null, null),
    SUN_SWORD(2400, Material.NETHERITE_SWORD, ToolAttribute(10.0, 1.6).toAttributeMap(), null),
    VALKYRE_AXE(2014, Material.IRON_AXE, null, null),
    VALKYRE_BOOTS(3004, Material.IRON_BOOTS, ArmorAttribute(3.0, 1.0, EquipmentSlot.FEET).toAttributeMap(), null),
    VALKYRE_LEGGINGS(3003, Material.IRON_LEGGINGS, ArmorAttribute(7.5, 1.0, EquipmentSlot.LEGS).toAttributeMap(), null),
    VALKYRE_RING(3001, Material.IRON_HELMET, ArmorAttribute(3.0, 1.0, EquipmentSlot.HEAD).toAttributeMap(),null),
    VALKYRE_SWORD(2003, Material.IRON_SWORD, ToolAttribute(8.0, 1.6).toAttributeMap(), null),
    VALKYRE_WINGS(3002, Material.ELYTRA, ArmorAttribute(9.0, 1.0, EquipmentSlot.CHEST).toAttributeMap(), null),
    ZANITE_AXE(2016, Material.DIAMOND_AXE, null, null),
    ZANITE_BOOTS(3008, Material.DIAMOND_BOOTS, ArmorAttribute(3.45, 2.3, EquipmentSlot.FEET).toAttributeMap(), null),
    ZANITE_CHESTPLATE(3006, Material.DIAMOND_CHESTPLATE, ArmorAttribute(9.2, 2.3, EquipmentSlot.CHEST).toAttributeMap(), null),
    ZANITE_HELMET(3005, Material.DIAMOND_HELMET, ArmorAttribute(3.45, 2.3, EquipmentSlot.HEAD).toAttributeMap(), null),
    ZANITE_HOE(2015, Material.DIAMOND_HOE, null, null),
    ZANITE_LEGGINGS(3007, Material.DIAMOND_LEGGINGS, ArmorAttribute(6.9, 2.3, EquipmentSlot.LEGS).toAttributeMap(), null),
    ZANITE_ORE(4013, Material.DIAMOND_ORE, null, Material.DIAMOND_ORE),
    ZANITE_PICKAXE(2017, Material.DIAMOND_PICKAXE, null, null),
    ZANITE_SHOVEL(2018, Material.DIAMOND_SHOVEL, null, null),
    ZANITE_STONE(1005, Material.DIAMOND, null, null),
    ZANITE_SWORD(2004, Material.DIAMOND_SWORD, ToolAttribute(7.0, 1.6).toAttributeMap(), null),
    MAGIC_WAND(1008, Material.IRON_HORSE_ARMOR, null, null),
    CLOUD_HEAL(4016, Material.PINK_STAINED_GLASS, null, Material.PINK_STAINED_GLASS),
    CLOUD_SLOW_FALLING(4017, Material.WHITE_STAINED_GLASS, null, Material.WHITE_STAINED_GLASS),
    CLOUD_SPEED(4018, Material.YELLOW_STAINED_GLASS, null, Material.YELLOW_STAINED_GLASS),
    CLOUD_JUMP(4019, Material.LIME_STAINED_GLASS, null, Material.LIME_STAINED_GLASS),
    CLOUD_HEAL2(4020, Material.RED_STAINED_GLASS, null, Material.RED_STAINED_GLASS),
    CLOUD_SPEED2(4021, Material.ORANGE_STAINED_GLASS, null, Material.ORANGE_STAINED_GLASS),
    CLOUD_JUMP2(4022, Material.GREEN_STAINED_GLASS, null, Material.GREEN_STAINED_GLASS);

    private val displayName = inst().getLanguageManager().getComponent("items.$name.name")
    private val description = inst().getLanguageManager().getComponentList("items.$name.description")

    fun getItemStack(): ItemStack {
        return getItemStack(1)
    }

    fun getItemStack(amount: Int): ItemStack {
        val itemStack = ItemStack(material)
        val itemMeta = itemStack.itemMeta
        itemMeta.displayName(displayName.decoration(TextDecoration.ITALIC, false))

        val descriptionList = ArrayList<Component>()
        for(component in description) {
            descriptionList.add(component.decoration(TextDecoration.ITALIC, false))
        }
        itemMeta.lore(descriptionList)

        itemMeta.setCustomModelData(modelID)
        if(attributeModifiers != null)
        {
            itemMeta.attributeModifiers = attributeModifiers
        }
        itemStack.itemMeta = itemMeta
        itemStack.amount = amount

        itemStack.setDataString(AETHER_ITEM_TAG_KEY, name)
        return itemStack
    }

    companion object {

        @JvmStatic
        fun fromTag(itemStack: ItemStack?): ItemType? {
            if(itemStack == null) return null
            return try {
                val itemTag = itemStack.getDataString(AETHER_ITEM_TAG_KEY)?: return null
                valueOf(itemTag)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    private abstract class CustomAttribute {
        fun toAttributeMap(
            attribute1: Attribute, value1: Double, slot1: EquipmentSlot,
            attribute2: Attribute, value2: Double, slot2: EquipmentSlot,
        ): Multimap<Attribute, AttributeModifier> {
            val attributeMap: Multimap<Attribute, AttributeModifier> = ArrayListMultimap.create()
            attributeMap.put(attribute1, AttributeModifier(UUID.randomUUID(), attribute1.key.toString(), value1, AttributeModifier.Operation.ADD_NUMBER, slot1))
            attributeMap.put(attribute2, AttributeModifier(UUID.randomUUID(), attribute2.key.toString(), value2, AttributeModifier.Operation.ADD_NUMBER, slot2))
            return attributeMap
        }
    }

    private class ToolAttribute(val attackDamage: Double, val attackSpeed: Double) : CustomAttribute() {

        fun toAttributeMap(): Multimap<Attribute, AttributeModifier> {
            return toAttributeMap(Attribute.GENERIC_ATTACK_DAMAGE, attackDamage, EquipmentSlot.HAND,
                                    Attribute.GENERIC_ATTACK_SPEED, attackSpeed, EquipmentSlot.HAND)
        }
    }

    private class ArmorAttribute(val armor: Double, val armorToughness: Double, val slot: EquipmentSlot) : CustomAttribute() {

        fun toAttributeMap(): Multimap<Attribute, AttributeModifier> {
            return toAttributeMap(Attribute.GENERIC_ARMOR, armor, slot,
                Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughness, slot)
        }
    }
}