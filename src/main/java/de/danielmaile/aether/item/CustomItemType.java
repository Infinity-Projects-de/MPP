package de.danielmaile.aether.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import de.danielmaile.aether.util.NBTEditor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public enum CustomItemType
{
    AETHER_ACACIA_LOG("Himmelsakazienstamm", null,
            4005, Material.ACACIA_LOG, Material.ACACIA_LOG),
    AETHER_ACACIA_PLANKS("Himmelsakazienplanke", null,
            4011, Material.ACACIA_PLANKS, Material.ACACIA_PLANKS),
    AETHER_BIRCH_LOG("Himmelsbirkenstamm", null,
            4003, Material.BIRCH_LOG, Material.BIRCH_LOG),
    AETHER_BIRCH_PLANKS("Himmelsbirkenplanke", null,
            4009, Material.BIRCH_PLANKS, Material.BIRCH_PLANKS),
    AETHER_DARK_OAK_LOG("Himmelsschwarzeichenstamm", null,
            4006, Material.DARK_OAK_LOG, Material.DARK_OAK_LOG),
    AETHER_DARK_OAK_PLANKS("Himmelsschwarzeichenplanke", null,
            4012, Material.DARK_OAK_PLANKS, Material.DARK_OAK_PLANKS),
    AETHER_JUNGLE_LOG("Himmelsjungelstamm", null,
            4004, Material.JUNGLE_LOG, Material.JUNGLE_LOG),
    AETHER_JUNGLE_PLANKS("Himmelsjungelplanke", null,
            4010, Material.JUNGLE_PLANKS, Material.JUNGLE_PLANKS),
    AETHER_OAK_LOG("Himmelseichenstamm", null,
            4001, Material.OAK_LOG, Material.OAK_LOG),
    AETHER_OAK_PLANKS("Himmelseichenplanke", null,
            4007, Material.OAK_PLANKS, Material.OAK_PLANKS),
    AETHER_SPRUCE_LOG("Himmelsfichtenstamm", null,
            4002, Material.SPRUCE_LOG, Material.SPRUCE_LOG),
    AETHER_SPRUCE_PLANKS("Himmelsfichtenplanke", null,
            4008, Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS),
    AETHER_STICK("Himmelsstock", "Stock aus dem Himmel",
            1001, Material.STICK),
    AETHER_STONE("Himmelsstein", null,
            4015, Material.STONE, Material.STONE),
    AETHER_STONE_AXE("Heiligsteinaxt", null,
            2011, Material.STONE_AXE),
    AETHER_STONE_HOE("Heiligsteinharke", null,
            2010, Material.STONE_HOE),
    AETHER_STONE_PICKAXE("Heiligsteinspitzhacke", null,
            2012, Material.STONE_PICKAXE),
    AETHER_STONE_SHOVEL("Heiligsteinschaufel", null,
            2013, Material.STONE_SHOVEL),
    AETHER_STONE_SWORD("Heiligsteinschwert", null,
            2002, Material.STONE_SWORD),
    AETHER_WOODEN_AXE("Himmelsaxt", null,
            2007, Material.WOODEN_AXE),
    AETHER_WOODEN_HOE("Himmelsharke", null,
            2006, Material.WOODEN_HOE),
    AETHER_WOODEN_PICKAXE("Himmelsspitzhacke", null,
            2008, Material.WOODEN_PICKAXE),
    AETHER_WOODEN_SHOVEL("Himmelschaufel", null,
            2009, Material.WOODEN_SHOVEL),
    AETHER_WOODEN_SWORD("Himmelsholzschwert", null,
            2001, Material.WOODEN_SWORD),
    FIRE_ESSENCE(ChatColor.BOLD.toString() + ChatColor.RED + "Feueressenz", "Eine Essenz vom Feuerteufel die zum verbessern von Waffen benutzt werden kann.",
            1003, Material.ORANGE_DYE),
    FLAME_SWORD(ChatColor.BOLD.toString() + ChatColor.RED + "Feuerschwert", "Verbrenne deine Feinde, falls sie dir zu nahe kommen.",
            2200, Material.DIAMOND_SWORD, new ToolAttribute(10, 1.6)),
    GRAVITIT_AXE(ChatColor.LIGHT_PURPLE + "Gravititaxt", null,
            2020, Material.NETHERITE_AXE),
    GRAVITIT_BOOTS(ChatColor.LIGHT_PURPLE + "Gravititschuhe", null,
            3012, Material.NETHERITE_BOOTS, new ArmorAttribute(3.9, 3.9, EquipmentSlot.FEET)),
    GRAVITIT_CHESTPLATE(ChatColor.LIGHT_PURPLE + "Gravititbrustplatte", null,
            3010, Material.NETHERITE_CHESTPLATE, new ArmorAttribute(10.4, 3.9, EquipmentSlot.CHEST)),
    GRAVITIT_HELMET(ChatColor.LIGHT_PURPLE + "Gravitithelm", null,
            3009, Material.NETHERITE_HELMET, new ArmorAttribute(3.9, 3.9, EquipmentSlot.HEAD)),
    GRAVITIT_HOE(ChatColor.LIGHT_PURPLE + "Gravititharke", null,
            2019, Material.NETHERITE_HOE),
    GRAVITIT_LEGGINGS(ChatColor.LIGHT_PURPLE + "Gravitithose", null,
            3011, Material.NETHERITE_LEGGINGS, new ArmorAttribute(7.8, 3.9, EquipmentSlot.LEGS)),
    GRAVITIT_ORE(ChatColor.LIGHT_PURPLE + "Gravititerz", null,
            4014, Material.ANCIENT_DEBRIS, Material.ANCIENT_DEBRIS),
    GRAVITIT_PICKAXE(ChatColor.LIGHT_PURPLE + "Gravititspitzhacke", null,
            2021, Material.NETHERITE_PICKAXE),
    GRAVITIT_PLATE(ChatColor.LIGHT_PURPLE + "Garavititplatte", "Platten so fest, das fast alles Abgewehrt werden kann.",
            1006, Material.NETHERITE_SCRAP),
    GRAVITIT_SHOVEL(ChatColor.LIGHT_PURPLE + "Gravititschaufel", null,
            2022, Material.NETHERITE_SHOVEL),
    GRAVITIT_SWORD("Gavititschwert", null,
            2005, Material.NETHERITE_SWORD, new ToolAttribute(8, 1.6)),
    ICE_ESSENCE(ChatColor.BOLD.toString() + ChatColor.BLUE + "Eisessenz", "Eine Essenz vom Eisgeist die zum verbessern von Waffen benutzt werden kann.",
            1004, Material.LIGHT_BLUE_DYE),
    ICE_SWORD(ChatColor.BOLD.toString() + ChatColor.BLUE + "Eisschwert", "Lasse deine Gegner bei Berührung erstarren.",
            2300, Material.DIAMOND_SWORD, new ToolAttribute(9, 1.6)),
    LIGHTNING_ESSENCE(ChatColor.BOLD.toString() + ChatColor.AQUA + "Blitzessenz", "Eine Essenz vom Himmelswal die zum verbessern von Waffen benutzt werden kann.",
            1002, Material.BLUE_DYE),
    LIGHTNING_SWORD(ChatColor.BOLD.toString() + ChatColor.AQUA + "Blitzschwert", "Beschwöre Blitze auf Jenen, der versucht dich zu verletzen.",
            2100, Material.DIAMOND_SWORD, new ToolAttribute(9, 1.6)),
    SUNSTONE(ChatColor.BOLD.toString() + ChatColor.GOLD + "Stein der Sonne", "Dieser besondere Stein besitzt die Kraft eine mächtige Waffe herzustellen.",
            1007, Material.NETHER_STAR),
    SUN_SWORD(ChatColor.BOLD.toString() + ChatColor.GOLD + "Sonnenschwert", "Das Schwert des Sonnengottes ist eine mächtige Waffe die jeden erzittern lässt.",
            2400, Material.NETHERITE_SWORD, new ToolAttribute(10, 1.6)),
    VALKYRE_AXE(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Kampfaxt der Walkyren", "Dies ist die Kampfaxt der Walkyren. Sie zerteil alles was in den Weg kommt.",
            2014, Material.IRON_AXE),
    VALKYRE_BOOTS(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Stiefel der Valkyre", "Die Stiefel der Valkyre erlauben es dir, langsamer auf den Boden zu fallen.",
            3004, Material.IRON_BOOTS, new ArmorAttribute(3, 1, EquipmentSlot.FEET)),
    VALKYRE_LEGGINGS(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Beinschutz der Valkyre", "Der Beinschutz der Valkyre ist stabil und hält vieles aus.",
            3003, Material.IRON_LEGGINGS, new ArmorAttribute(7.5, 1, EquipmentSlot.LEGS)),
    VALKYRE_RING(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Heiligenschein der Valkyren", "Der heilige Ring der Valkyre. Er besitzt die Kraft dich zu verstärken.",
            3001, Material.IRON_HELMET, new ArmorAttribute(3, 1, EquipmentSlot.HEAD)),
    VALKYRE_SWORD(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Rache der Valkyre", "Ein mächtiges Schwert welches Furcht und Angst hervorruft.",
            2003, Material.IRON_SWORD, new ToolAttribute(8, 1.6)),
    VALKYRE_WINGS(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Valkyrenflügel", "Mit den Valkyrenflügeln kannst du durch den Himmel gleiten wie ein Engel.",
            3002, Material.ELYTRA, new ArmorAttribute(9, 1, EquipmentSlot.CHEST)),
    ZANITE_AXE(ChatColor.DARK_PURPLE + "Zaniteaxt", null,
            2016, Material.DIAMOND_AXE),
    ZANITE_BOOTS(ChatColor.DARK_PURPLE + "Zaniteschuhe", null,
            3008, Material.DIAMOND_BOOTS, new ArmorAttribute(3.45, 2.3, EquipmentSlot.FEET)),
    ZANITE_CHESTPLATE(ChatColor.DARK_PURPLE + "Zanitebrustplatte", null,
            3006, Material.DIAMOND_CHESTPLATE, new ArmorAttribute(9.2, 2.3, EquipmentSlot.CHEST)),
    ZANITE_HELMET(ChatColor.DARK_PURPLE + "Zanitehelm", null,
            3005, Material.DIAMOND_HELMET, new ArmorAttribute(3.45, 2.3, EquipmentSlot.HEAD)),
    ZANITE_HOE(ChatColor.DARK_PURPLE + "Zaniteharke", null,
            2015, Material.DIAMOND_HOE),
    ZANITE_LEGGINGS(ChatColor.DARK_PURPLE + "Zanitehose", null,
            3007, Material.DIAMOND_LEGGINGS, new ArmorAttribute(6.9, 2.3, EquipmentSlot.LEGS)),
    ZANITE_ORE(ChatColor.DARK_PURPLE + "Zaniteerz", null,
            4013, Material.DIAMOND_ORE, Material.DIAMOND_ORE),
    ZANITE_PICKAXE(ChatColor.DARK_PURPLE + "Zanitespitzhacke", null,
            2017, Material.DIAMOND_PICKAXE),
    ZANITE_SHOVEL(ChatColor.DARK_PURPLE + "Zaniteschaufel", null,
            2018, Material.DIAMOND_SHOVEL),
    ZANITE_STONE(ChatColor.DARK_PURPLE + "Zanitgestein", "Ein Gestein zum Herstellen starker Waffen und Werkzeuge.",
            1005, Material.DIAMOND),
    ZANITE_SWORD(ChatColor.DARK_PURPLE + "Zaniteschwert", null,
            2004, Material.DIAMOND_SWORD, new ToolAttribute(7, 1.6));

    public static final String AETHER_ITEM_TAG_KEY = "aether_item";

    private final String name;
    private final String description;
    private final int modelID;
    private final Material itemMaterial;
    private final Material placeMaterial;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    CustomItemType(String name, String description, int modelID, Material itemMaterial,
                   Multimap<Attribute, AttributeModifier> attributeModifiers, Material placeMaterial)
    {
        this.name = name;
        this.description = description;
        this.modelID = modelID;
        this.itemMaterial = itemMaterial;
        this.attributeModifiers = attributeModifiers;
        this.placeMaterial = placeMaterial;
    }

    CustomItemType(String name, String description, int modelID, Material itemMaterial)
    {
        this(name, description, modelID, itemMaterial, null, null);
    }

    CustomItemType(String name, String description, int modelID, Material itemMaterial, Material placeMaterial)
    {
        this(name, description, modelID, itemMaterial, null, placeMaterial);
    }

    CustomItemType(String name, String description, int modelID, Material itemMaterial, CustomAttribute customAttribute)
    {
        this(name, description, modelID, itemMaterial, customAttribute.toAttributeMap(), null);
    }

    public Material getPlaceMaterial()
    {
        return placeMaterial;
    }

    public ItemStack getItemStack()
    {
        return getItemStack(1);
    }

    public ItemStack getItemStack(int amount)
    {
        ItemStack itemStack = new ItemStack(itemMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(name).decoration(TextDecoration.ITALIC, false));
        if (description != null)
        {
            itemMeta.lore(List.of(Component.text(description).decoration(TextDecoration.ITALIC, false)));
        }
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

    public static CustomItemType getFromTag(ItemStack itemStack)
    {
        try
        {
            return CustomItemType.valueOf(NBTEditor.getString(itemStack, CustomItemType.AETHER_ITEM_TAG_KEY));
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
