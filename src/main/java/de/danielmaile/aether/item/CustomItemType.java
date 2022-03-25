package de.danielmaile.aether.item;

import de.danielmaile.aether.util.NBTEditor;
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
            1001, Material.STICK, null),
    LIGHTNING_ESSENCE(ChatColor.BOLD.toString() + ChatColor.AQUA + "Blitzessenz", "Eine Essenz vom Himmelswal die zum verbessern von Waffen benutzt werden kann.",
            1002, Material.BLUE_DYE, null),
    FIRE_ESSENCE(ChatColor.BOLD.toString() + ChatColor.RED + "Feueressenz", "Eine Essenz vom Feuerteufel die zum verbessern von Waffen benutzt werden kann.",
            1003, Material.ORANGE_DYE, null),
    ICE_ESSENCE(ChatColor.BOLD.toString() + ChatColor.BLUE + "Eisessenz", "Eine Essenz vom Eisgeist die zum verbessern von Waffen benutzt werden kann.",
            1004, Material.LIGHT_BLUE_DYE, null),
    ZANITE_STONE(ChatColor.DARK_PURPLE + "Zanitgestein", "Ein Gestein zum Herstellen starker Waffen und Werkzeuge.",
            1005, Material.DIAMOND, null),
    GRAVITIT_PLATE(ChatColor.LIGHT_PURPLE + "Garavititplatte", "Platten so fest, das fast alles Abgewehrt werden kann.",
            1006, Material.NETHERITE_SCRAP, null),
    SUNSTONE(ChatColor.BOLD.toString() + ChatColor.GOLD + "Stein der Sonne", "Dieser besondere Stein besitzt die Kraft eine mächtige Waffe herzustellen.",
            1007, Material.NETHER_STAR, null),
    AETHER_WOODEN_SWORD("Himmelsholzschwert", null,
            2001, Material.WOODEN_SWORD, null),
    AETHER_STONE_SWORD("Heiligsteinschwert", null,
            2002, Material.STONE_SWORD, null),
    VALKYRE_SWORD(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Rache der Valkyre", "Ein mächtiges Schwert welches Furcht und Angst hervorruft.",
            2003, Material.IRON_SWORD, null),
    ZANITE_SWORD(ChatColor.DARK_PURPLE + "Zaniteschwert", null,
            2004, Material.DIAMOND_SWORD, null),
    GRAVITIT_SWORD("Gavititschwert", null,
            2005, Material.NETHERITE_SWORD, null),
    AETHER_WOODEN_HOE("Himmelsharke", null,
            2006, Material.WOODEN_HOE, null),
    AETHER_WOODEN_AXE("Himmelsaxt", null,
            2007, Material.WOODEN_AXE, null),
    AETHER_WOODEN_PICKAXE("Himmelsspitzhacke", null,
            2008, Material.WOODEN_PICKAXE, null),
    AETHER_WOODEN_SHOVEL("Himmelschaufel", null,
            2009, Material.WOODEN_SHOVEL, null),
    AETHER_STONE_HOE("Heiligsteinharke", null,
            2010, Material.STONE_HOE, null),
    AETHER_STONE_AXE("Heiligsteinaxt", null,
            2011, Material.STONE_AXE, null),
    AETHER_STONE_PICKAXE("Heiligsteinspitzhacke", null,
            2012, Material.STONE_PICKAXE, null),
    AETHER_STONE_SHOVEL("Heiligsteinschaufel", null,
            2013, Material.STONE_SHOVEL, null),
    VALKYRE_AXE(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Kampfaxt der Walkyren", "Dies ist die Kampfaxt der Walkyren. Sie zerteil alles was in den Weg kommt.",
            2014, Material.IRON_AXE, null),
    ZANITE_HOE(ChatColor.DARK_PURPLE + "Zaniteharke", null,
            2015, Material.DIAMOND_HOE, null),
    ZANITE_AXE(ChatColor.DARK_PURPLE + "Zaniteaxt", null,
            2016, Material.DIAMOND_AXE, null),
    ZANITE_PICKAXE(ChatColor.DARK_PURPLE + "Zanitespitzhacke", null,
            2017, Material.DIAMOND_PICKAXE, null),
    ZANITE_SHOVEL(ChatColor.DARK_PURPLE + "Zaniteschaufel", null,
            2018, Material.DIAMOND_SHOVEL, null),
    GRAVITIT_HOE(ChatColor.LIGHT_PURPLE + "Gravititharke", null,
            2019, Material.NETHERITE_HOE, null),
    GRAVITIT_AXE(ChatColor.LIGHT_PURPLE + "Gravititaxt", null,
            2020, Material.NETHERITE_AXE, null),
    GRAVITIT_PICKAXE(ChatColor.LIGHT_PURPLE + "Gravititspitzhacke", null,
            2021, Material.NETHERITE_PICKAXE, null),
    GRAVITIT_SHOVEL(ChatColor.LIGHT_PURPLE + "Gravititschaufel", null,
            2022, Material.NETHERITE_SHOVEL, null),
    LIGHTNING_SWORD(ChatColor.BOLD.toString() + ChatColor.AQUA + "Blitzschwert", "Beschwöre Blitze auf Jenen, der versucht dich zu verletzen.",
            2100, Material.DIAMOND_SWORD, null),
    FLAME_SWORD(ChatColor.BOLD.toString() + ChatColor.RED + "Feuerschwert", "Verbrenne deine Feinde, falls sie dir zu nahe kommen.",
            2200, Material.DIAMOND_SWORD, null),
    ICE_SWORD(ChatColor.BOLD.toString() + ChatColor.BLUE + "Eisschwert", "Lasse deine Gegner bei Berührung erstarren.",
            2300, Material.DIAMOND_SWORD, null),
    SUN_SWORD(ChatColor.BOLD.toString() + ChatColor.GOLD + "Sonnenschwert", "Das Schwert des Sonnengottes ist eine mächtige Waffe die jeden erzittern lässt.",
            2400, Material.NETHERITE_SWORD, null),
    VALKYRE_RING(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Heiligenschein der Valkyren", "Der heilige Ring der Valkyre. Er besitzt die Kraft dich zu verstärken.",
            3001, Material.IRON_HELMET, null),
    VALKYRE_WINGS(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Valkyrenflügel", "Mit den Valkyrenflügeln kannst du durch den Himmel gleiten wie ein Engel.",
            3002, Material.ELYTRA, null),
    VALKYRE_LEGGINGS(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Beinschutz der Valkyre", "Der Beinschutz der Valkyre ist stabil und hält vieles aus.",
            3003, Material.IRON_LEGGINGS, null),
    VALKYRE_BOOTS(ChatColor.BOLD.toString() + ChatColor.YELLOW + "Stiefel der Valkyre", "Die Stiefel der Valkyre erlauben es dir, langsamer auf den Boden zu fallen.",
            3004, Material.IRON_BOOTS, null),
    ZANITE_HELMET(ChatColor.DARK_PURPLE + "Zanitehelm", null,
            3005, Material.DIAMOND_HELMET, null),
    ZANITE_CHESTPLATE(ChatColor.DARK_PURPLE + "Zanitebrustplatte", null,
            3006, Material.DIAMOND_CHESTPLATE, null),
    ZANITE_LEGGINGS(ChatColor.DARK_PURPLE + "Zanitehose", null,
            3007, Material.DIAMOND_LEGGINGS, null),
    ZANITE_BOOTS(ChatColor.DARK_PURPLE + "Feueressenz", null,
            3008, Material.DIAMOND_BOOTS, null),
    GRAVITIT_HELMET(ChatColor.LIGHT_PURPLE + "Gravitithelm", null,
            3009, Material.NETHERITE_HELMET, null),
    GRAVITIT_CHESTPLATE(ChatColor.LIGHT_PURPLE + "Gravititbrustplatte", null,
            3010, Material.NETHERITE_CHESTPLATE, null),
    GRAVITIT_LEGGINGS(ChatColor.LIGHT_PURPLE + "Gravitithose", null,
            3011, Material.NETHERITE_LEGGINGS, null),
    GRAVITIT_BOOTS(ChatColor.LIGHT_PURPLE + "Gravititschuhe", null,
            3012, Material.NETHERITE_BOOTS, null),
    AETHER_OAK_LOG("Himmelseichenstamm", null,
            4001, Material.OAK_LOG, Material.OAK_LOG),
    AETHER_SPRUCE_LOG("Himmelsfichtenstamm", null,
            4002, Material.SPRUCE_LOG, Material.SPRUCE_LOG),
    AETHER_BIRCH_LOG("Himmelsbirkenstamm", null,
            4003, Material.BIRCH_LOG, Material.BIRCH_LOG),
    AETHER_JUNGLE_LOG("Himmelsjungelstamm", null,
            4004, Material.JUNGLE_LOG, Material.JUNGLE_LOG),
    AETHER_ACACIA_LOG("Himmelsakazienstamm", null,
            4005, Material.ACACIA_LOG, Material.ACACIA_LOG),
    AETHER_DARK_OAK_LOG("Himmelsschwarzeichenstamm", null,
            4006, Material.DARK_OAK_LOG, Material.DARK_OAK_LOG),
    AETHER_OAK_PLANKS("Himmelseichenplanke", null,
            4007, Material.OAK_PLANKS, Material.OAK_PLANKS),
    AETHER_SPRUCE_PLANKS("Himmelsfichtenplanke", null,
            4008, Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS),
    AETHER_BIRCH_PLANKS("Himmelsbirkenplanke", null,
            4009, Material.BIRCH_PLANKS, Material.BIRCH_PLANKS),
    AETHER_JUNGLE_PLANKS("Himmelsjungelplanke", null,
            4010, Material.JUNGLE_PLANKS, Material.JUNGLE_PLANKS),
    AETHER_ACACIA_PLANKS("Himmelsakazienplanke", null,
            4011, Material.ACACIA_PLANKS, Material.ACACIA_PLANKS),
    AETHER_DARK_OAK_PLANKS("Himmelsschwarzeichenplanke", null,
            4012, Material.DARK_OAK_PLANKS, Material.DARK_OAK_PLANKS),
    ZANITE_ORE(ChatColor.DARK_PURPLE + "Zaniteerz", null,
            4013, Material.DIAMOND_ORE, Material.DIAMOND_ORE),
    GRAVITIT_ORE(ChatColor.LIGHT_PURPLE + "Gravititerz", null,
            4014, Material.ANCIENT_DEBRIS, Material.ANCIENT_DEBRIS),
    AETHER_STONE("Himmelsstein", null,
            4015, Material.STONE, Material.STONE);

    public static final String AETHER_ITEM_TAG_KEY = "aether_item";

    private final String name;
    private final String description;
    private final int modelID;
    private final Material itemMaterial;
    private final Material placeMaterial;

    CustomItemType(String name, String description, int modelID, Material itemMaterial, Material placeMaterial)
    {
        this.name = name;
        this.description = description;
        this.modelID = modelID;
        this.itemMaterial = itemMaterial;
        this.placeMaterial = placeMaterial;
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
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);

        //Set nbt tag
        return NBTEditor.setString(itemStack, AETHER_ITEM_TAG_KEY, name());
    }
}
