package de.danielmaile.aether.item;

import de.tr7zw.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public enum CustomItemType
{
    AETHER_STICK("Himmelsstock", "Stock aus dem Himmel",
            "aether_stick", 1001, Material.STICK),
    LIGHTNING_ESSENCE(ChatColor.AQUA + "Blitzessenz", "Eine Essenz vom Himmelswal die zum verbessern von Waffen benutzt werden kann.",
            "lightning_essence", 1002, Material.BLUE_DYE),
    FIRE_ESSENCE(ChatColor.GOLD + "Feueressenz", "Eine Essenz vom Feuerteufel die zum verbessern von Waffen benutzt werden kann.",
            "fire_essence", 1003, Material.ORANGE_DYE),
    ICE_ESSENCE(ChatColor.BLUE + "Eisessenz", "Eine Essenz vom Eisgeist die zum verbessern von Waffen benutzt werden kann.",
            "ice_essence", 1004, Material.LIGHT_BLUE_DYE),
    ZANITE_STONE(ChatColor.DARK_PURPLE + "Zanitgestein", "Ein Gestein zum Herstellen starker Waffen und Werkzeuge.",
            "zanite_stone", 1005, Material.DIAMOND),
    GRAVITIT_PLATE(ChatColor.LIGHT_PURPLE + "Garavititplatte", "Platten so fest, das fast alles Abgewehrt werden kann.",
            "gravitit_plate", 1006, Material.NETHERITE_SCRAP),
    HOLYSTONE("Stein der Sonne", "Dieser besondere Stein besitzt die Kraft eine mächtige Waffe herzustellen.",
            "holystone", 1007, Material.NETHER_STAR),
    SKYROOT_SWORD("Himmelsholzschwert", null,
            "skyroot_sword", 2001, Material.WOODEN_SWORD),
    HOLYSTONE_SWORD("Heiligensteinschwert", null,
            "holystone_sword", 2002, Material.STONE_SWORD),
    VALKYRE_SWORD(ChatColor.YELLOW + "Rache der Valkyre", "Ein mächtiges Schwert welches Furcht und Angst hervorruft.",
            "valkyre_sword", 2003, Material.IRON_SWORD),
    ZANITE_SWORD(ChatColor.DARK_PURPLE + "Zaniteschwert", null,
            "zanite_sworde", 2004, Material.DIAMOND_SWORD),
    GRAVITIT_SWORD("Gavititschwert", null,
            "gravitite_sword", 2005, Material.NETHERITE_SWORD),
    AETHER_WOODEN_HOE("Himmelsharke", null,
            "aether_wooden_hoe", 2006, Material.WOODEN_HOE),
    AETHER_WOODEN_AXE("Himmelsaxt", null,
            "aether_wooden_axe", 2007, Material.WOODEN_AXE),
    AETHER_WOODEN_PICKAXE("Himmelsspitzhacke", null,
            "aether_wooden_pickaxe", 2008, Material.WOODEN_PICKAXE),
    AETHER_WOODEN_SHOVEL("Himmelschaufel", null,
            "aether_wooden_shovel", 2009, Material.WOODEN_SHOVEL),
    HOLY_STONE_HOE("Heiligsteinharke", null,
            "holy_stone_hoe", 2010, Material.STONE_HOE),
    HOLY_STONE_AXE("Heiligsteinaxt", null,
            "holy_stone_axe", 2011, Material.STONE_AXE),
    HOLY_STONE_PICKAXE("Heiligsteinspitzhacke", null,
            "holy_stone_pickaxe", 2012, Material.STONE_PICKAXE),
    STONE_SHOVEL("Heiligsteinschaufel", null,
            "holy_stone_shovel", 2013, Material.STONE_SHOVEL),
    VALKYRE_AXE(ChatColor.YELLOW + "Kampfaxt der Walkyren", "Dies ist die Kampfaxt der Walkyren. Sie zerteil alles was in den Weg kommt.",
            "valkyre_axe", 2014, Material.IRON_AXE),
    ZANITE_HOE(ChatColor.DARK_PURPLE + "Zaniteharke", null,
            "zanite_hoe", 2015, Material.DIAMOND_HOE),
    ZANITE_AXE(ChatColor.DARK_PURPLE + "Zaniteaxt", null,
            "zanite_axe", 2016, Material.DIAMOND_AXE),
    ZANITE_PICKAXE(ChatColor.DARK_PURPLE + "Zanitespitzhacke", null,
            "zanite_pickaxe", 2017, Material.DIAMOND_PICKAXE),
    ZANITE_SHOVEL(ChatColor.DARK_PURPLE + "Zaniteschaufel", null,
            "zanite_shovel", 2018, Material.DIAMOND_SHOVEL),
    GRAVITIT_HOE(ChatColor.LIGHT_PURPLE + "Gravititharke", null,
            "gravitit_hoe", 2019, Material.NETHERITE_HOE),
    GRAVITIT_AXE(ChatColor.LIGHT_PURPLE + "Gravititaxt", null,
            "gravitit_axe", 2020, Material.NETHERITE_AXE),
    GRAVITIT_PICKAXE(ChatColor.LIGHT_PURPLE + "Gravititspitzhacke", null,
            "gravitit_pickaxe", 2021, Material.NETHERITE_PICKAXE),
    GRVITIT_SHOVEL(ChatColor.LIGHT_PURPLE + "Gravititschaufel", null,
            "gravitit_shovel", 2022, Material.NETHERITE_SHOVEL),
    LIGHTNING_SWORD("Blitzschwert", "Beschwöre Blitze auf Jenen, der versucht dich zu verletzen.",
            "lightning_sword", 2100, Material.DIAMOND_SWORD),
    FLAME_SWORD("Feuerschwert", "Verbrenne deine Feinde, falls sie dir zu nahe kommen.",
            "flame_sword", 2200, Material.DIAMOND_SWORD),
    ICE_SWORD("Eisschwert", "Lasse deine Gegner bei Berührung erstarren.",
            "ice_sword", 2300, Material.DIAMOND_SWORD),
    HOLY_SWORD("Sonnenschwert", "Das Schwert des Sonnengottes ist eine mächtige Waffe die jeden erzittern lässt.",
            "holy_sword", 2400, Material.NETHERITE_SWORD),
    VALKYRE_RING(ChatColor.YELLOW + "Heiligenschein der Valkyren", "Der heilige Ring der Valkyre. Er besitzt die Kraft dich zu verstärken.",
            "valkyre_ring", 3001, Material.IRON_HELMET),
    VALKYRE_WINGS(ChatColor.YELLOW + "Valkyrenflügel", "Mit den Valkyrenflügeln kannst du durch den Himmel gleiten wie ein Engel.",
            "valkyre_wings", 3002, Material.ELYTRA),
    VALKYRE_LEGGINGS(ChatColor.YELLOW + "Beinschutz der Valkyre", "Der Beinschutz der Valkyre ist stabil und hält vieles aus.",
            "valkyre_leggings", 3003, Material.IRON_LEGGINGS),
    VALKYRE_BOOTS(ChatColor.YELLOW + "Stiefel der Valkyre", "Die Stiefel der Valkyre erlauben es dir, langsamer auf den Boden zu fallen.",
            "valkyre_boots", 3004, Material.IRON_BOOTS),
    ZANITE_HELMET(ChatColor.DARK_PURPLE + "Zanitehelm", null,
            "zanite_helmet", 3005, Material.DIAMOND_HELMET),
    ZANITE_CHESTPLATE(ChatColor.DARK_PURPLE + "Zanitebrustplatte", null,
            "zanite_chestplate", 3006, Material.DIAMOND_CHESTPLATE),
    ZANITE_LEGGINGS(ChatColor.DARK_PURPLE + "Zanitehose", null,
            "zanite_leggings", 3007, Material.DIAMOND_LEGGINGS),
    ZANITE_BOOTS(ChatColor.DARK_PURPLE + "Feueressenz", null,
            "zanite_boots", 3008, Material.DIAMOND_BOOTS),
    GRAVITIT_HELMET(ChatColor.LIGHT_PURPLE + "Gravitithelm", null,
            "gravitite_hemlet", 3009, Material.NETHERITE_HELMET),
    GRAVITIT_CHESTPLATE(ChatColor.LIGHT_PURPLE + "Gravititbrustplatte", null,
            "gravitite_chestplate", 3010, Material.NETHERITE_CHESTPLATE),
    GRAVITIT_LEGGINGS(ChatColor.LIGHT_PURPLE + "Gravitithose", null,
            "gravitite_leggings", 3011, Material.NETHERITE_LEGGINGS),
    GRAVITIT_BOOTS(ChatColor.LIGHT_PURPLE + "Gravititschuhe", null,
            "gravitite_boots", 3012, Material.NETHERITE_BOOTS),
    AETHER_OAK_LOG("Himmelseichenstamm", null,
            "aether_oak_log", 4001, Material.OAK_LOG),
    AETHER_SPRUCE_LOG("Himmelsfichtenstamm", null,
            "aether_spruce_log", 4002, Material.SPRUCE_LOG),
    AETHER_BIRCH_LOG("Himmelsbirkenstamm", null,
            "aether_birch_log", 4003, Material.BIRCH_LOG),
    AETHER_JUNGLE_LOG("Himmelsjungelstamm", null,
            "aether_jungle_log", 4004, Material.JUNGLE_LOG),
    AETHER_ACACIA_LOG("Himmelsakazienstamm", null,
            "aether_acacia_log", 4005, Material.ACACIA_LOG),
    AETHER_DARK_OAK_LOG("Himmelsschwarzeichenstamm", null,
            "aether_dark_oak_log", 4006, Material.DARK_OAK_LOG),
    AETHER_OAK_PLANKS("Himmelseichenplanke", null,
            "aether_oak_planks", 4007, Material.OAK_PLANKS),
    AETHER_SPRUCE_PLANKS("Himmelsfichtenplanke", null,
            "aether_spruce_planks", 4008, Material.SPRUCE_PLANKS),
    AETHER_BIRCH_PLANKS("Himmelsbirkenplanke", null,
            "aether_birch_planks", 4009, Material.BIRCH_PLANKS),
    AETHER_JUNGLE_PLANKS("Himmelsjungelplanke", null,
            "aether_jungle_planks", 4010, Material.JUNGLE_PLANKS),
    AETHER_ACACIA_PLANKS("Himmelsakazienplanke", null,
            "aether_acacia_planks", 4011, Material.ACACIA_PLANKS),
    AETHER_DARK_OAK_PLANKS("Himmelsschwarzeichenplanke", null,
            "aether_dark_oak_planks", 4012, Material.DARK_OAK_PLANKS),
    ZANITE_ORE(ChatColor.DARK_PURPLE + "Zaniteerz", null,
            "zanite_ore", 4013, Material.DIAMOND_ORE),
    GRAVITIT_ORE(ChatColor.LIGHT_PURPLE + "Gravititerz", null,
            "gravitit_ore", 4014, Material.ANCIENT_DEBRIS),
    AETHER_STONE("Himmelsstein", null,
            "aether_stone", 4015, Material.STONE);

    public static final String AETHER_ITEM_TAG_KEY = "aether_item";

    private final String name;
    private final String description;
    private final String tag;
    private final int modelID;
    private final Material baseMaterial;

    CustomItemType(String name, String description, String tag, int modelID, Material baseMaterial)
    {
        this.name = name;
        this.description = description;
        this.tag = tag;
        this.modelID = modelID;
        this.baseMaterial = baseMaterial;
    }

    public ItemStack getItemStack(int amount)
    {
        ItemStack itemStack = new ItemStack(baseMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(name).decoration(TextDecoration.ITALIC, false));
        if(description != null)
        {
            itemMeta.lore(List.of(Component.text(description).decoration(TextDecoration.ITALIC, false)));
        }
        itemMeta.setCustomModelData(modelID);
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);

        //Set nbt tags
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString(AETHER_ITEM_TAG_KEY, tag);
        return nbtItem.getItem();
    }
}
