package de.danielmaile.aether.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import de.danielmaile.aether.Aether;
import de.danielmaile.aether.util.NBTEditor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public enum ItemType
{
    AETHER_ACACIA_LOG(4005, Material.ACACIA_LOG, Material.ACACIA_LOG),
    AETHER_ACACIA_PLANKS(4011, Material.ACACIA_PLANKS, Material.ACACIA_PLANKS),
    AETHER_BIRCH_LOG(4003, Material.BIRCH_LOG, Material.BIRCH_LOG),
    AETHER_BIRCH_PLANKS(4009, Material.BIRCH_PLANKS, Material.BIRCH_PLANKS),
    AETHER_DARK_OAK_LOG(4006, Material.DARK_OAK_LOG, Material.DARK_OAK_LOG),
    AETHER_DARK_OAK_PLANKS(4012, Material.DARK_OAK_PLANKS, Material.DARK_OAK_PLANKS),
    AETHER_JUNGLE_LOG(4004, Material.JUNGLE_LOG, Material.JUNGLE_LOG),
    AETHER_JUNGLE_PLANKS(4010, Material.JUNGLE_PLANKS, Material.JUNGLE_PLANKS),
    AETHER_OAK_LOG(4001, Material.OAK_LOG, Material.OAK_LOG),
    AETHER_OAK_PLANKS(4007, Material.OAK_PLANKS, Material.OAK_PLANKS),
    AETHER_SPRUCE_LOG(4002, Material.SPRUCE_LOG, Material.SPRUCE_LOG),
    AETHER_SPRUCE_PLANKS(4008, Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS),
    AETHER_STICK(1001, Material.STICK),
    AETHER_STONE(4015, Material.STONE, Material.STONE),
    AETHER_STONE_AXE(2011, Material.STONE_AXE),
    AETHER_STONE_HOE(2010, Material.STONE_HOE),
    AETHER_STONE_PICKAXE(2012, Material.STONE_PICKAXE),
    AETHER_STONE_SHOVEL(2013, Material.STONE_SHOVEL),
    AETHER_STONE_SWORD(2002, Material.STONE_SWORD),
    AETHER_WOODEN_AXE(2007, Material.WOODEN_AXE),
    AETHER_WOODEN_HOE(2006, Material.WOODEN_HOE),
    AETHER_WOODEN_PICKAXE(2008, Material.WOODEN_PICKAXE),
    AETHER_WOODEN_SHOVEL(2009, Material.WOODEN_SHOVEL),
    AETHER_WOODEN_SWORD(2001, Material.WOODEN_SWORD),
    FIRE_ESSENCE(1003, Material.ORANGE_DYE),
    FLAME_SWORD(2200, Material.DIAMOND_SWORD, new ToolAttribute(10, 1.6)),
    GRAVITITE_AXE(2020, Material.NETHERITE_AXE),
    GRAVITITE_BOOTS(3012, Material.NETHERITE_BOOTS, new ArmorAttribute(3.9, 3.9, EquipmentSlot.FEET)),
    GRAVITITE_CHESTPLATE(3010, Material.NETHERITE_CHESTPLATE, new ArmorAttribute(10.4, 3.9, EquipmentSlot.CHEST)),
    GRAVITITE_HELMET(3009, Material.NETHERITE_HELMET, new ArmorAttribute(3.9, 3.9, EquipmentSlot.HEAD)),
    GRAVITITE_HOE(2019, Material.NETHERITE_HOE),
    GRAVITITE_LEGGINGS(3011, Material.NETHERITE_LEGGINGS, new ArmorAttribute(7.8, 3.9, EquipmentSlot.LEGS)),
    GRAVITITE_ORE(4014, Material.ANCIENT_DEBRIS, Material.ANCIENT_DEBRIS),
    GRAVITITE_PICKAXE(2021, Material.NETHERITE_PICKAXE),
    GRAVITITE_PLATE(1006, Material.NETHERITE_SCRAP),
    GRAVITITE_SHOVEL(2022, Material.NETHERITE_SHOVEL),
    GRAVITITE_SWORD(2005, Material.NETHERITE_SWORD, new ToolAttribute(8, 1.6)),
    ICE_ESSENCE(1004, Material.LIGHT_BLUE_DYE),
    ICE_SWORD(2300, Material.DIAMOND_SWORD, new ToolAttribute(9, 1.6)),
    LIGHTNING_ESSENCE(1002, Material.BLUE_DYE),
    LIGHTNING_SWORD(2100, Material.DIAMOND_SWORD, new ToolAttribute(9, 1.6)),
    SUN_STONE(1007, Material.NETHER_STAR),
    SUN_SWORD(2400, Material.NETHERITE_SWORD, new ToolAttribute(10, 1.6)),
    VALKYRE_AXE(2014, Material.IRON_AXE),
    VALKYRE_BOOTS(3004, Material.IRON_BOOTS, new ArmorAttribute(3, 1, EquipmentSlot.FEET)),
    VALKYRE_LEGGINGS(3003, Material.IRON_LEGGINGS, new ArmorAttribute(7.5, 1, EquipmentSlot.LEGS)),
    VALKYRE_RING(3001, Material.IRON_HELMET, new ArmorAttribute(3, 1, EquipmentSlot.HEAD)),
    VALKYRE_SWORD(2003, Material.IRON_SWORD, new ToolAttribute(8, 1.6)),
    VALKYRE_WINGS(3002, Material.ELYTRA, new ArmorAttribute(9, 1, EquipmentSlot.CHEST)),
    ZANITE_AXE(2016, Material.DIAMOND_AXE),
    ZANITE_BOOTS(3008, Material.DIAMOND_BOOTS, new ArmorAttribute(3.45, 2.3, EquipmentSlot.FEET)),
    ZANITE_CHESTPLATE(3006, Material.DIAMOND_CHESTPLATE, new ArmorAttribute(9.2, 2.3, EquipmentSlot.CHEST)),
    ZANITE_HELMET(3005, Material.DIAMOND_HELMET, new ArmorAttribute(3.45, 2.3, EquipmentSlot.HEAD)),
    ZANITE_HOE(2015, Material.DIAMOND_HOE),
    ZANITE_LEGGINGS(3007, Material.DIAMOND_LEGGINGS, new ArmorAttribute(6.9, 2.3, EquipmentSlot.LEGS)),
    ZANITE_ORE(4013, Material.DIAMOND_ORE, Material.DIAMOND_ORE),
    ZANITE_PICKAXE(2017, Material.DIAMOND_PICKAXE),
    ZANITE_SHOVEL(2018, Material.DIAMOND_SHOVEL),
    ZANITE_STONE(1005, Material.DIAMOND),
    ZANITE_SWORD(2004, Material.DIAMOND_SWORD, new ToolAttribute(7, 1.6));

    public static final String AETHER_ITEM_TAG_KEY = "aether_item";

    private final Component name;
    private final List<Component> description;
    private final int modelID;
    private final Material itemMaterial;
    private final Material placeMaterial;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    ItemType(int modelID, Material itemMaterial, Multimap<Attribute, AttributeModifier> attributeModifiers, Material placeMaterial)
    {
        this.name = Aether.getLanguageManager().getComponent("items." + name() + ".name");
        this.description = Aether.getLanguageManager().getComponentList("items." + name() + ".description");
        this.modelID = modelID;
        this.itemMaterial = itemMaterial;
        this.attributeModifiers = attributeModifiers;
        this.placeMaterial = placeMaterial;
    }

    ItemType(int modelID, Material itemMaterial)
    {
        this(modelID, itemMaterial, null, null);
    }

    ItemType(int modelID, Material itemMaterial, Material placeMaterial)
    {
        this(modelID, itemMaterial, null, placeMaterial);
    }

    ItemType(int modelID, Material itemMaterial, CustomAttribute customAttribute)
    {
        this(modelID, itemMaterial, customAttribute.toAttributeMap(), null);
    }

    public Material getPlaceMaterial()
    {
        return placeMaterial;
    }

    public ItemStack getItemStack()
    {
        return getItemStack(1);
    }

    public @Nonnull
    ItemStack getItemStack(int amount)
    {
        ItemStack itemStack = new ItemStack(itemMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name.decoration(TextDecoration.ITALIC, false));

        List<Component> descriptionList = new ArrayList<>();
        for (Component component : description)
        {
            descriptionList.add(component.decoration(TextDecoration.ITALIC, false));
        }
        itemMeta.lore(descriptionList);

        itemMeta.setCustomModelData(modelID);
        if (attributeModifiers != null)
        {
            itemMeta.setAttributeModifiers(attributeModifiers);
        }
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);

        //Set nbt tag
        return NBTEditor.setString(itemStack, AETHER_ITEM_TAG_KEY, name());
    }

    public static ItemType getFromTag(ItemStack itemStack)
    {
        try
        {
            return ItemType.valueOf(NBTEditor.getString(itemStack, ItemType.AETHER_ITEM_TAG_KEY));
        }
        catch (NullPointerException | IllegalArgumentException exception)
        {
            return null;
        }
    }

    private static abstract class CustomAttribute
    {
        public abstract Multimap<Attribute, AttributeModifier> toAttributeMap();
    }

    private static class ToolAttribute extends CustomAttribute
    {
        public final double attackDamage;
        public final double attackSpeed;

        public ToolAttribute(double attackDamage, double attackSpeed)
        {
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
        }

        @Override
        public Multimap<Attribute, AttributeModifier> toAttributeMap()
        {
            Multimap<Attribute, AttributeModifier> attributeMap = ArrayListMultimap.create();
            attributeMap.put(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),
                    "generic.attack_damage", attackDamage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            attributeMap.put(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attack_speed", attackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            return attributeMap;
        }
    }

    private static class ArmorAttribute extends CustomAttribute
    {
        public final double armor;
        public final double armorToughness;
        public final EquipmentSlot slot;

        public ArmorAttribute(double armor, double armorToughness, EquipmentSlot slot)
        {
            this.armor = armor;
            this.armorToughness = armorToughness;
            this.slot = slot;
        }

        @Override
        public Multimap<Attribute, AttributeModifier> toAttributeMap()
        {
            Multimap<Attribute, AttributeModifier> attributeMap = ArrayListMultimap.create();
            attributeMap.put(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(),
                    "generic.armor", armor, AttributeModifier.Operation.ADD_NUMBER, slot));
            attributeMap.put(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(),
                    "generic.armor_toughness", armorToughness, AttributeModifier.Operation.ADD_NUMBER, slot));
            return attributeMap;
        }
    }
}
