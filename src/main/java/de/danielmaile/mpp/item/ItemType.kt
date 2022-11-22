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

// Don't change this, otherwise existing items will break
private const val modelIdOffset = 1000

enum class ItemType(
    val material: Material,
    private val attributeModifiers: Multimap<Attribute, AttributeModifier>?,
    val placeBlockType: BlockType?
) {

    // Don't change order because existing items will break
    AETHER_LOG(Material.OAK_WOOD, null, BlockType.AETHER_LOG),
    FIRE_LOG(Material.OAK_WOOD, null, BlockType.FIRE_LOG),
    WATER_LOG(Material.OAK_WOOD, null, BlockType.WATER_LOG),
    AIR_LOG(Material.OAK_WOOD, null, BlockType.AIR_LOG),
    EARTH_LOG(Material.OAK_WOOD, null, BlockType.EARTH_LOG),
    AETHER_PLANKS( Material.OAK_PLANKS, null, BlockType.AETHER_PLANKS),
    FIRE_PLANKS(Material.OAK_PLANKS, null, BlockType.FIRE_PLANKS),
    WATER_PLANKS(Material.OAK_PLANKS, null, BlockType.WATER_PLANKS),
    AIR_PLANKS(Material.OAK_PLANKS, null, BlockType.AIR_PLANKS),
    EARTH_PLANKS(Material.OAK_PLANKS, null, BlockType.EARTH_PLANKS),
    SILVER_ORE( Material.STONE, null, BlockType.SILVER_ORE),
    TITANIUM_ORE( Material.STONE, null, BlockType.TITANIUM_ORE),
    ZANITE_ORE( Material.STONE, null, BlockType.ZANITE_ORE),
    GRAVITITE_ORE( Material.STONE,null, BlockType.GRAVITITE_ORE),
    AETHER_STONE( Material.STONE, null, BlockType.AETHER_STONE),
    CLOUD_HEAL( Material.WHITE_WOOL, null, BlockType.CLOUD_HEAL),
    CLOUD_SLOW_FALLING( Material.WHITE_WOOL, null, BlockType.CLOUD_SLOW_FALLING),
    CLOUD_SPEED( Material.WHITE_WOOL, null, BlockType.CLOUD_SPEED),
    CLOUD_JUMP( Material.WHITE_WOOL, null, BlockType.CLOUD_JUMP),
    CLOUD_HEAL2( Material.WHITE_WOOL, null, BlockType.CLOUD_HEAL2),
    CLOUD_SPEED2( Material.WHITE_WOOL, null, BlockType.CLOUD_SPEED2),
    CLOUD_JUMP2( Material.WHITE_WOOL, null, BlockType.CLOUD_JUMP2),
    AETHER_GRASS_BLOCK( Material.DIRT, null, BlockType.AETHER_GRASS_BLOCK),
    AETHER_DIRT( Material.DIRT, null, BlockType.AETHER_DIRT),
    CRYSTAL_ORE(Material.AMETHYST_BLOCK, null, BlockType.CRYSTAL_ORE),
    AETHER_STICK( Material.STICK, null, null),
    AETHER_STONE_AXE( Material.STONE_AXE, null, null),
    AETHER_STONE_HOE(Material.STONE_HOE, null, null),
    AETHER_STONE_PICKAXE( Material.STONE_PICKAXE, null, null),
    AETHER_STONE_SHOVEL( Material.STONE_SHOVEL, null, null),
    AETHER_STONE_SWORD( Material.STONE_SWORD, null, null),
    AETHER_WOODEN_AXE( Material.WOODEN_AXE, null, null),
    AETHER_WOODEN_HOE(Material.WOODEN_HOE, null, null),
    AETHER_WOODEN_PICKAXE(Material.WOODEN_PICKAXE, null, null),
    AETHER_WOODEN_SHOVEL(Material.WOODEN_SHOVEL, null, null),
    AETHER_WOODEN_SWORD( Material.WOODEN_SWORD, null, null),
    ADMIN_SWORD(Material.NETHERITE_SWORD, ToolAttribute(1000000.0, 0.1).toAttributeMap(), null),
    FIRE_ESSENCE( Material.ORANGE_DYE, null, null),
    FIRE_SWORD( Material.DIAMOND_SWORD, ToolAttribute(10.0, 1.6).toAttributeMap(), null),
    GRAVITITE_AXE( Material.NETHERITE_AXE, null, null),
    GRAVITITE_BOOTS( Material.NETHERITE_BOOTS,  ArmorAttribute(5.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    GRAVITITE_CHESTPLATE( Material.NETHERITE_CHESTPLATE, ArmorAttribute(10.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    GRAVITITE_HELMET( Material.NETHERITE_HELMET, ArmorAttribute(5.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    GRAVITITE_HOE( Material.NETHERITE_HOE, null, null),
    GRAVITITE_LEGGINGS( Material.NETHERITE_LEGGINGS, ArmorAttribute(8.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    GRAVITITE_PICKAXE( Material.NETHERITE_PICKAXE, null, null),
    GRAVITITE_PLATE( Material.NETHERITE_SCRAP, null, null),
    GRAVITITE_SHOVEL( Material.NETHERITE_SHOVEL, null, null),
    GRAVITITE_SWORD( Material.NETHERITE_SWORD, ToolAttribute(8.0, 1.6).toAttributeMap(), null),
    ICE_ESSENCE(Material.LIGHT_BLUE_DYE, null, null),
    ICE_SWORD( Material.DIAMOND_SWORD, ToolAttribute(9.0, 1.6).toAttributeMap(), null),
    LIGHTNING_ESSENCE( Material.BLUE_DYE, null, null),
    LIGHTNING_SWORD( Material.DIAMOND_SWORD, ToolAttribute(9.0, 1.6).toAttributeMap(), null),
    SUN_STONE( Material.NETHER_STAR, null, null),
    SUN_SWORD( Material.NETHERITE_SWORD, ToolAttribute(10.0, 1.6).toAttributeMap(), null),
    VALKYRE_AXE( Material.IRON_AXE, null, null),
    VALKYRE_BOOTS( Material.IRON_BOOTS, ArmorAttribute(3.0, 1.0, EquipmentSlot.FEET).toAttributeMap(), null),
    VALKYRE_LEGGINGS( Material.IRON_LEGGINGS, ArmorAttribute(7.5, 1.0, EquipmentSlot.LEGS).toAttributeMap(), null),
    VALKYRE_RING( Material.IRON_HELMET, ArmorAttribute(3.0, 1.0, EquipmentSlot.HEAD).toAttributeMap(),null),
    VALKYRE_SWORD(Material.IRON_SWORD, ToolAttribute(8.0, 1.6).toAttributeMap(), null),
    VALKYRE_WINGS( Material.ELYTRA, ArmorAttribute(9.0, 1.0, EquipmentSlot.CHEST).toAttributeMap(), null),
    ZANITE_AXE( Material.DIAMOND_AXE, null, null),
    ZANITE_BOOTS(Material.DIAMOND_BOOTS, ArmorAttribute(4.0, 2.3, EquipmentSlot.FEET).toAttributeMap(), null),
    ZANITE_CHESTPLATE(Material.DIAMOND_CHESTPLATE, ArmorAttribute(9.0, 2.3, EquipmentSlot.CHEST).toAttributeMap(), null),
    ZANITE_HELMET(Material.DIAMOND_HELMET, ArmorAttribute(4.0, 2.3, EquipmentSlot.HEAD).toAttributeMap(), null),
    ZANITE_HOE( Material.DIAMOND_HOE, null, null),
    ZANITE_LEGGINGS( Material.DIAMOND_LEGGINGS, ArmorAttribute(7.0, 2.3, EquipmentSlot.LEGS).toAttributeMap(), null),
    ZANITE_PICKAXE(Material.DIAMOND_PICKAXE, null, null),
    ZANITE_SHOVEL( Material.DIAMOND_SHOVEL, null, null),
    ZANITE_STONE( Material.DIAMOND, null, null),
    ZANITE_SWORD( Material.DIAMOND_SWORD, ToolAttribute(7.0, 1.6).toAttributeMap(), null),
    MAGIC_WAND( Material.IRON_HORSE_ARMOR, null, null),
    FIRE_LEAVES(Material.OAK_LEAVES, null, BlockType.FIRE_LEAVES),
    WATER_LEAVES(Material.OAK_LEAVES, null, BlockType.WATER_LEAVES),
    AIR_LEAVES(Material.OAK_LEAVES, null, BlockType.AIR_LEAVES),
    EARTH_LEAVES(Material.OAK_LEAVES, null, BlockType.EARTH_LEAVES),
    OBSIDIAN_BOOTS( Material.NETHERITE_BOOTS,  ArmorAttribute(6.0, 3.9, EquipmentSlot.FEET).toAttributeMap(), null),
    OBSIDIAN_CHESTPLATE( Material.NETHERITE_CHESTPLATE, ArmorAttribute(11.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    OBSIDIAN_HELMET( Material.NETHERITE_HELMET, ArmorAttribute(6.0, 3.9, EquipmentSlot.HEAD).toAttributeMap(), null),
    OBSIDIAN_LEGGINGS( Material.NETHERITE_LEGGINGS, ArmorAttribute(9.0, 3.9, EquipmentSlot.LEGS).toAttributeMap(), null),
    SILVER_INGOT( Material.IRON_INGOT, null, null),
    TITANIUM_INGOT( Material.IRON_INGOT, null, null),
    CRYSTAL_SHARD( Material.QUARTZ, null, null),
    PHOENIX_HELMET( Material.ELYTRA, ArmorAttribute(7.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    PHOENIX_CHESTPLATE( Material.ELYTRA, ArmorAttribute(12.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    PHOENIX_LEGGINGS( Material.ELYTRA, ArmorAttribute(7.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null),
    PHOENIX_BOOTS( Material.ELYTRA, ArmorAttribute(10.0, 3.9, EquipmentSlot.CHEST).toAttributeMap(), null);

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