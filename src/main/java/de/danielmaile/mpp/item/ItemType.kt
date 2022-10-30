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
import kotlin.collections.ArrayList

const val MPP_ITEM_TAG_KEY = "mpp_item"

enum class ItemType(
    private val modelID: Int,
    val material: Material,
    private val attributeModifiers: Multimap<Attribute, AttributeModifier>?,
    val placeBlockType: BlockType?
) {

    AETHER_ACACIA_LOG(1, Material.ACACIA_LOG, null, BlockType.AETHER_ACACIA_LOG),
    AETHER_ACACIA_PLANKS(2, Material.ACACIA_PLANKS, null, BlockType.AETHER_ACACIA_PLANKS),
    AETHER_BIRCH_LOG(3, Material.BIRCH_LOG, null, BlockType.AETHER_BIRCH_LOG),
    AETHER_BIRCH_PLANKS(4, Material.BIRCH_PLANKS, null, BlockType.AETHER_BIRCH_PLANKS),
    AETHER_DARK_OAK_LOG(5, Material.DARK_OAK_LOG, null, BlockType.AETHER_DARK_OAK_LOG),
    AETHER_DARK_OAK_PLANKS(6, Material.DARK_OAK_PLANKS, null, BlockType.AETHER_DARK_OAK_PLANKS),
    AETHER_JUNGLE_LOG(7, Material.JUNGLE_LOG, null, BlockType.AETHER_JUNGLE_LOG),
    AETHER_JUNGLE_PLANKS(8, Material.JUNGLE_PLANKS, null, BlockType.AETHER_JUNGLE_PLANKS),
    AETHER_OAK_LOG(9, Material.OAK_LOG, null, BlockType.AETHER_OAK_LOG),
    AETHER_OAK_PLANKS(10, Material.OAK_PLANKS, null, BlockType.AETHER_OAK_PLANKS),
    AETHER_SPRUCE_LOG(11, Material.SPRUCE_LOG, null, BlockType.AETHER_SPRUCE_LOG),
    AETHER_SPRUCE_PLANKS(12, Material.SPRUCE_PLANKS, null, BlockType.AETHER_SPRUCE_PLANKS),
    AETHER_STICK(13, Material.STICK, null, null),
    AETHER_STONE(14, Material.STONE, null, BlockType.AETHER_STONE),
    AETHER_STONE_AXE(15, Material.STONE_AXE, null, null),
    AETHER_STONE_HOE(16, Material.STONE_HOE, null, null),
    AETHER_STONE_PICKAXE(17, Material.STONE_PICKAXE, null, null),
    AETHER_STONE_SHOVEL(18, Material.STONE_SHOVEL, null, null),
    AETHER_STONE_SWORD(19, Material.STONE_SWORD, null, null),
    AETHER_WOODEN_AXE(20, Material.WOODEN_AXE, null, null),
    AETHER_WOODEN_HOE(21, Material.WOODEN_HOE, null, null),
    AETHER_WOODEN_PICKAXE(22, Material.WOODEN_PICKAXE, null, null),
    AETHER_WOODEN_SHOVEL(23, Material.WOODEN_SHOVEL, null, null),
    AETHER_WOODEN_SWORD(24, Material.WOODEN_SWORD, null, null),
    ADMIN_SWORD(25, Material.NETHERITE_SWORD, ToolAttribute(1000000.0, 0.1).toAttributeMap(), null),
    FIRE_ESSENCE(26, Material.ORANGE_DYE, null, null),
    FLAME_SWORD(27, Material.DIAMOND_SWORD, ToolAttribute(10.0, 1.6).toAttributeMap(), null),
    GRAVITITE_AXE(28, Material.NETHERITE_AXE, null, null),
    GRAVITITE_BOOTS(29, Material.NETHERITE_BOOTS,  ArmorAttribute(3.9, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    GRAVITITE_CHESTPLATE(30, Material.NETHERITE_CHESTPLATE, ArmorAttribute(10.4, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    GRAVITITE_HELMET(31, Material.NETHERITE_HELMET, ArmorAttribute(3.9, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    GRAVITITE_HOE(32, Material.NETHERITE_HOE, null, null),
    GRAVITITE_LEGGINGS(33, Material.NETHERITE_LEGGINGS, ArmorAttribute(7.8, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    GRAVITITE_ORE(34, Material.ANCIENT_DEBRIS,null, BlockType.GRAVITITE_ORE),
    GRAVITITE_PICKAXE(35, Material.NETHERITE_PICKAXE, null, null),
    GRAVITITE_PLATE(36, Material.NETHERITE_SCRAP, null, null),
    GRAVITITE_SHOVEL(37, Material.NETHERITE_SHOVEL, null, null),
    GRAVITITE_SWORD(38, Material.NETHERITE_SWORD, ToolAttribute(8.0, 1.6).toAttributeMap(), null),
    ICE_ESSENCE(39, Material.LIGHT_BLUE_DYE, null, null),
    ICE_SWORD(40, Material.DIAMOND_SWORD, ToolAttribute(9.0, 1.6).toAttributeMap(), null),
    LIGHTNING_ESSENCE(41, Material.BLUE_DYE, null, null),
    LIGHTNING_SWORD(42, Material.DIAMOND_SWORD, ToolAttribute(9.0, 1.6).toAttributeMap(), null),
    SUN_STONE(43, Material.NETHER_STAR, null, null),
    SUN_SWORD(44, Material.NETHERITE_SWORD, ToolAttribute(10.0, 1.6).toAttributeMap(), null),
    VALKYRE_AXE(45, Material.IRON_AXE, null, null),
    VALKYRE_BOOTS(46, Material.IRON_BOOTS, ArmorAttribute(3.0, 1.0, EquipmentSlot.FEET).toAttributeMap(), null),
    VALKYRE_LEGGINGS(47, Material.IRON_LEGGINGS, ArmorAttribute(7.5, 1.0, EquipmentSlot.LEGS).toAttributeMap(), null),
    VALKYRE_RING(48, Material.IRON_HELMET, ArmorAttribute(3.0, 1.0, EquipmentSlot.HEAD).toAttributeMap(),null),
    VALKYRE_SWORD(49, Material.IRON_SWORD, ToolAttribute(8.0, 1.6).toAttributeMap(), null),
    VALKYRE_WINGS(50, Material.ELYTRA, ArmorAttribute(9.0, 1.0, EquipmentSlot.CHEST).toAttributeMap(), null),
    ZANITE_AXE(51, Material.DIAMOND_AXE, null, null),
    ZANITE_BOOTS(52, Material.DIAMOND_BOOTS, ArmorAttribute(3.45, 2.3, EquipmentSlot.FEET).toAttributeMap(), null),
    ZANITE_CHESTPLATE(53, Material.DIAMOND_CHESTPLATE, ArmorAttribute(9.2, 2.3, EquipmentSlot.CHEST).toAttributeMap(), null),
    ZANITE_HELMET(54, Material.DIAMOND_HELMET, ArmorAttribute(3.45, 2.3, EquipmentSlot.HEAD).toAttributeMap(), null),
    ZANITE_HOE(55, Material.DIAMOND_HOE, null, null),
    ZANITE_LEGGINGS(56, Material.DIAMOND_LEGGINGS, ArmorAttribute(6.9, 2.3, EquipmentSlot.LEGS).toAttributeMap(), null),
    ZANITE_ORE(57, Material.DIAMOND_ORE, null, BlockType.ZANITE_ORE),
    ZANITE_PICKAXE(58, Material.DIAMOND_PICKAXE, null, null),
    ZANITE_SHOVEL(59, Material.DIAMOND_SHOVEL, null, null),
    ZANITE_STONE(60, Material.DIAMOND, null, null),
    ZANITE_SWORD(61, Material.DIAMOND_SWORD, ToolAttribute(7.0, 1.6).toAttributeMap(), null),
    MAGIC_WAND(62, Material.IRON_HORSE_ARMOR, null, null),
    CLOUD_HEAL(63, Material.PINK_STAINED_GLASS, null, BlockType.CLOUD_HEAL),
    CLOUD_SLOW_FALLING(64, Material.WHITE_STAINED_GLASS, null, BlockType.CLOUD_SLOW_FALLING),
    CLOUD_SPEED(65, Material.YELLOW_STAINED_GLASS, null, BlockType.CLOUD_SPEED),
    CLOUD_JUMP(66, Material.LIME_STAINED_GLASS, null, BlockType.CLOUD_JUMP),
    CLOUD_HEAL2(67, Material.RED_STAINED_GLASS, null, BlockType.CLOUD_HEAL2),
    CLOUD_SPEED2(68, Material.ORANGE_STAINED_GLASS, null, BlockType.CLOUD_SPEED2),
    CLOUD_JUMP2(69, Material.GREEN_STAINED_GLASS, null, BlockType.CLOUD_JUMP2),
    AETHER_GRASS_BLOCK(70, Material.GRASS_BLOCK, null, BlockType.AETHER_GRASS_BLOCK),
    AETHER_DIRT(71, Material.DIRT, null, BlockType.AETHER_DIRT);

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

        itemStack.setDataString(MPP_ITEM_TAG_KEY, name)
        return itemStack
    }

    companion object {

        @JvmStatic
        fun fromTag(itemStack: ItemStack?): ItemType? {
            if(itemStack == null) return null
            return try {
                val itemTag = itemStack.getDataString(MPP_ITEM_TAG_KEY)?: return null
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