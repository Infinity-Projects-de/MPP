package de.danielmaile.aether.item;

import de.danielmaile.aether.Aether;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public enum CustomRecipe
{
    AETHER_WOODEN_SWORD(true, CustomItemType.AETHER_WOODEN_SWORD, 1,
            null, CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_WOODEN_SWORD1(true, CustomItemType.AETHER_WOODEN_SWORD, 1,
            null, null, CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_STICK),
    AETHER_WOODEN_SWORD2(true, CustomItemType.AETHER_WOODEN_SWORD, 1,
            CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_STICK, null, null),
    AETHER_WOODEN_SWORD3(true, CustomItemType.AETHER_WOODEN_SWORD, 1,
            null, CustomItemType.AETHER_BIRCH_PLANKS, null, null, CustomItemType.AETHER_BIRCH_PLANKS, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_WOODEN_SWORD4(true, CustomItemType.AETHER_WOODEN_SWORD, 1,
            null, null, CustomItemType.AETHER_BIRCH_PLANKS, null, null, CustomItemType.AETHER_BIRCH_PLANKS, null, null, CustomItemType.AETHER_STICK),
    AETHER_WOODEN_SWORD5(true, CustomItemType.AETHER_WOODEN_SWORD, 1,
            CustomItemType.AETHER_BIRCH_PLANKS, null, null, CustomItemType.AETHER_BIRCH_PLANKS, null, null, CustomItemType.AETHER_STICK, null, null),
    AETHER_WOODEN_HOE(true, CustomItemType.AETHER_WOODEN_HOE, 1,
            CustomItemType.AETHER_OAK_PLANKS, CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_WOODEN_HOE1(true, CustomItemType.AETHER_WOODEN_HOE, 1,
            CustomItemType.AETHER_OAK_PLANKS, CustomItemType.AETHER_OAK_PLANKS, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null, null),
    AETHER_WOODEN_HOE2(true, CustomItemType.AETHER_WOODEN_HOE, 1,
            null, CustomItemType.AETHER_OAK_PLANKS, CustomItemType.AETHER_OAK_PLANKS, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_WOODEN_HOE3(true, CustomItemType.AETHER_WOODEN_HOE, 1,
            null, CustomItemType.AETHER_OAK_PLANKS, CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK),
    AETHER_WOODEN_PICKAXE(true, CustomItemType.AETHER_WOODEN_PICKAXE,
            1, CustomItemType.AETHER_OAK_PLANKS, CustomItemType.AETHER_OAK_PLANKS, CustomItemType.AETHER_OAK_PLANKS, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_WOODEN_SHOVEL(true, CustomItemType.AETHER_WOODEN_SHOVEL,
            1, CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null, null),
    AETHER_WOODEN_SHOVEL1(true, CustomItemType.AETHER_WOODEN_SHOVEL,
            1, null, CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_WOODEN_SHOVEL2(true, CustomItemType.AETHER_WOODEN_SHOVEL,
            1, null, null, CustomItemType.AETHER_OAK_PLANKS, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK),
    AETHER_WOODEN_AXE(true, CustomItemType.AETHER_WOODEN_AXE,
            1, CustomItemType.AETHER_OAK_PLANKS, CustomItemType.AETHER_OAK_PLANKS, null, CustomItemType.AETHER_OAK_PLANKS, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_WOODEN_AXE1(true, CustomItemType.AETHER_WOODEN_AXE,
            1, null, CustomItemType.AETHER_OAK_PLANKS, CustomItemType.AETHER_OAK_PLANKS, null, CustomItemType.AETHER_STICK, CustomItemType.AETHER_OAK_PLANKS, null, CustomItemType.AETHER_STICK, null),
    AETHER_STONE_SWORD(true, CustomItemType.AETHER_STONE_SWORD,
            1, CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STICK, null, null),
    AETHER_STONE_SWORD1(true, CustomItemType.AETHER_STONE_SWORD,
            1, null, CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_STONE_SWORD2(true, CustomItemType.AETHER_STONE_SWORD,
            1, null, null, CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STICK),
    AETHER_STONE_HOE(true, CustomItemType.AETHER_STONE_HOE,
            1, CustomItemType.AETHER_STONE, CustomItemType.AETHER_STONE, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null, null),
    AETHER_STONE_HOE1(true, CustomItemType.AETHER_STONE_HOE,
            1, CustomItemType.AETHER_STONE, CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_STONE_HOE2(true, CustomItemType.AETHER_STONE_HOE,
            1, null, CustomItemType.AETHER_STONE, CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK),
    AETHER_STONE_HOE3(true, CustomItemType.AETHER_STONE_HOE, 1,
            null, CustomItemType.AETHER_STONE, CustomItemType.AETHER_STONE, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_STONE_PICKAXE(true, CustomItemType.AETHER_STONE_PICKAXE, 1,
            CustomItemType.AETHER_STONE, CustomItemType.AETHER_STONE, CustomItemType.AETHER_STONE, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_STONE_SHOVEL(true, CustomItemType.AETHER_STONE_SHOVEL, 1,
            CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null, null),
    AETHER_STONE_SHOVEL1(true, CustomItemType.AETHER_STONE_SHOVEL, 1,
            null, CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_STONE_SHOVEL2(true, CustomItemType.AETHER_STONE_SHOVEL, 1,
            null, null, CustomItemType.AETHER_STONE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK),
    AETHER_STONE_AXE(true, CustomItemType.AETHER_STONE_AXE, 1,
            CustomItemType.AETHER_STONE, CustomItemType.AETHER_STONE, null, CustomItemType.AETHER_STONE, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    AETHER_STONE_AXE1(true, CustomItemType.AETHER_STONE_AXE, 1,
            null, CustomItemType.AETHER_STONE, CustomItemType.AETHER_STONE, null, CustomItemType.AETHER_STICK, CustomItemType.AETHER_STONE, null, CustomItemType.AETHER_STICK, null),
    AETHER_STICK(false, CustomItemType.AETHER_STICK, 2,
            CustomItemType.AETHER_OAK_PLANKS),
    AETHER_STICK1(false, CustomItemType.AETHER_STICK, 2,
            CustomItemType.AETHER_BIRCH_PLANKS),
    ZANITE_SWORD(true, CustomItemType.ZANITE_SWORD, 1,
            CustomItemType.ZANITE_STONE, null, null, CustomItemType.ZANITE_STONE, null, null, CustomItemType.AETHER_STICK, null, null),
    ZANITE_SWORD1(true, CustomItemType.ZANITE_SWORD, 1,
            null, CustomItemType.ZANITE_STONE, null, null, CustomItemType.ZANITE_STONE, null, null, CustomItemType.AETHER_STICK, null),
    ZANITE_SWORD2(true, CustomItemType.ZANITE_SWORD, 1,
            null, null, CustomItemType.ZANITE_STONE, null, null, CustomItemType.ZANITE_STONE, null, null, CustomItemType.AETHER_STICK),
    ZANITE_HOE(true, CustomItemType.ZANITE_HOE, 1,
            CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null, null),
    ZANITE_HOE1(true, CustomItemType.ZANITE_HOE, 1,
            CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    ZANITE_HOE2(true, CustomItemType.ZANITE_HOE, 1,
            null, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    ZANITE_HOE3(true, CustomItemType.ZANITE_HOE, 1,
            null, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK),
    ZANITE_PICKAXE(true, CustomItemType.ZANITE_PICKAXE, 1,
            CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    ZANITE_SHOVEL(true, CustomItemType.ZANITE_SHOVEL, 1,
            CustomItemType.ZANITE_STONE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null, null),
    ZANITE_SHOVEL1(true, CustomItemType.ZANITE_SHOVEL, 1,
            null, CustomItemType.ZANITE_STONE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    ZANITE_SHOVEL2(true, CustomItemType.ZANITE_SHOVEL, 1,
            null, null, CustomItemType.ZANITE_STONE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK),
    ZANITE_AXE(true, CustomItemType.ZANITE_AXE, 1,
            CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, CustomItemType.ZANITE_STONE, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    ZANITE_AXE1(true, CustomItemType.ZANITE_AXE, 1,
            null, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, CustomItemType.AETHER_STICK, CustomItemType.ZANITE_STONE, null, CustomItemType.AETHER_STICK, null),
    GRAVITITE_SWORD(true, CustomItemType.GRAVITITE_SWORD, 1,
            CustomItemType.GRAVITITE_PLATE, null, null, CustomItemType.GRAVITITE_PLATE, null, null, CustomItemType.AETHER_STICK, null, null),
    GRAVITITE_SWORD1(true, CustomItemType.GRAVITITE_SWORD, 1,
            null, CustomItemType.GRAVITITE_PLATE, null, null, CustomItemType.GRAVITITE_PLATE, null, null, CustomItemType.AETHER_STICK, null),
    GRAVITITE_SWORD2(true, CustomItemType.GRAVITITE_SWORD, 1,
            null, null, CustomItemType.GRAVITITE_PLATE, null, null, CustomItemType.GRAVITITE_PLATE, null, null, CustomItemType.AETHER_STICK),
    GRAVITITE_HOE(true, CustomItemType.GRAVITITE_HOE, 1,
            CustomItemType.GRAVITITE_ORE, CustomItemType.GRAVITITE_ORE, null, CustomItemType.AETHER_STICK, null, CustomItemType.AETHER_STICK, null, null, null),
    GRAVITITE_HOE1(true, CustomItemType.GRAVITITE_HOE, 1,
            CustomItemType.GRAVITITE_ORE, CustomItemType.GRAVITITE_ORE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    GRAVITITE_HOE2(true, CustomItemType.GRAVITITE_HOE, 1,
            null, CustomItemType.GRAVITITE_ORE, CustomItemType.GRAVITITE_ORE, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    GRAVITITE_HOE3(true, CustomItemType.GRAVITITE_HOE, 1,
            null, CustomItemType.GRAVITITE_ORE, CustomItemType.GRAVITITE_ORE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK),
    GRAVITITE_PICKAXE(true, CustomItemType.GRAVITITE_PICKAXE, 1,
            CustomItemType.GRAVITITE_ORE, CustomItemType.GRAVITITE_ORE, CustomItemType.GRAVITITE_ORE, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    GRAVITITE_SHOVEL(true, CustomItemType.GRAVITITE_SHOVEL, 1,
            CustomItemType.GRAVITITE_ORE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null, null),
    GRAVITITE_SHOVEL1(true, CustomItemType.GRAVITITE_SHOVEL, 1,
            null, CustomItemType.GRAVITITE_ORE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    GRAVITITE_SHOVEL2(true, CustomItemType.GRAVITITE_SHOVEL, 1,
            null, null, CustomItemType.GRAVITITE_ORE, null, null, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK),
    GRAVITITE_AXE(true, CustomItemType.GRAVITITE_AXE, 1,
            CustomItemType.GRAVITITE_ORE, CustomItemType.GRAVITITE_ORE, null, CustomItemType.GRAVITITE_ORE, CustomItemType.AETHER_STICK, null, null, CustomItemType.AETHER_STICK, null),
    GRAVITITE_AXE1(true, CustomItemType.GRAVITITE_AXE, 1,
            null, CustomItemType.GRAVITITE_ORE, CustomItemType.GRAVITITE_ORE, null, CustomItemType.AETHER_STICK, CustomItemType.GRAVITITE_ORE, null, CustomItemType.AETHER_STICK, null),
    LIGHTNING_SWORD(false, CustomItemType.LIGHTNING_SWORD, 1,
            CustomItemType.ZANITE_SWORD, CustomItemType.LIGHTNING_ESSENCE),
    FLAME_SWORD(false, CustomItemType.FLAME_SWORD, 1,
            CustomItemType.ZANITE_SWORD, CustomItemType.FIRE_ESSENCE),
    ICE_SWORD(false, CustomItemType.ICE_SWORD, 1,
            CustomItemType.ZANITE_SWORD, CustomItemType.ICE_ESSENCE),
    SUN_SWORD(true, CustomItemType.SUN_SWORD, 1,
            CustomItemType.LIGHTNING_ESSENCE, CustomItemType.FIRE_ESSENCE, CustomItemType.ICE_ESSENCE, null, null, CustomItemType.SUN_STONE, null, CustomItemType.AETHER_STICK, null),
    ZANITE_HELMET(true, CustomItemType.ZANITE_HELMET, 1,
            CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, CustomItemType.ZANITE_STONE, null, null, null),
    ZANITE_CHESTPLATE(true, CustomItemType.ZANITE_CHESTPLATE, 1,
            CustomItemType.ZANITE_STONE, null, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE),
    ZANITE_LEGGINGS(true, CustomItemType.ZANITE_LEGGINGS, 1,
            CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, CustomItemType.ZANITE_STONE),
    ZANITE_BOOTS(true, CustomItemType.ZANITE_BOOTS, 1,
            null, null, null, CustomItemType.ZANITE_STONE, null, CustomItemType.ZANITE_STONE, CustomItemType.ZANITE_STONE, null, CustomItemType.ZANITE_STONE),
    GRAVITITE_HELMET(true, CustomItemType.GRAVITITE_HELMET, 1,
            CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, null, CustomItemType.GRAVITITE_PLATE, null, null, null),
    GRAVITITE_CHESTPLATE(true, CustomItemType.GRAVITITE_CHESTPLATE, 1,
            CustomItemType.GRAVITITE_PLATE, null, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE),
    GRAVITITE_LEGGINGS(true, CustomItemType.GRAVITITE_LEGGINGS, 1,
            CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, null, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, null, CustomItemType.GRAVITITE_PLATE),
    GRAVITITE_BOOTS(true, CustomItemType.GRAVITITE_BOOTS, 1,
            null, null, null, CustomItemType.GRAVITITE_PLATE, null, CustomItemType.GRAVITITE_PLATE, CustomItemType.GRAVITITE_PLATE, null, CustomItemType.GRAVITITE_PLATE),
    AETHER_OAK_PLANKS(false, CustomItemType.AETHER_OAK_PLANKS, 1,
            CustomItemType.AETHER_OAK_LOG),
    AETHER_BIRCH_PLANKS(false, CustomItemType.AETHER_BIRCH_PLANKS, 1,
            CustomItemType.AETHER_BIRCH_LOG),
    AETHER_ACACIA_PLANKS(false, CustomItemType.AETHER_ACACIA_PLANKS, 1,
            CustomItemType.AETHER_ACACIA_LOG),
    AETHER_SPRUCE_PLANKS(false, CustomItemType.AETHER_SPRUCE_PLANKS, 1,
            CustomItemType.AETHER_SPRUCE_LOG),
    AETHER_DARK_JUNGLE_PLANKS(false, CustomItemType.AETHER_JUNGLE_PLANKS, 1,
            CustomItemType.AETHER_JUNGLE_LOG),
    AETHER_DARK_OAK_PLANKS(false, CustomItemType.AETHER_DARK_OAK_PLANKS, 1,
            CustomItemType.AETHER_DARK_OAK_LOG);

    private final Recipe recipe;

    CustomRecipe(boolean shaped, CustomItemType result, int amount, CustomItemType... ingredients)
    {
        if (shaped && ingredients.length != 9)
        {
            throw new IllegalArgumentException("Shaped Recipes need 9 ingredients");
        }

        NamespacedKey key = new NamespacedKey(Aether.getInstance(), name());

        if (shaped)
        {
            ShapedRecipe shapedRecipe = new ShapedRecipe(key, result.getItemStack(amount));

            //Set shape
            char[] shape = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'H', 'I', 'J'};
            for (int i = 0; i < shape.length; i++)
            {
                if (ingredients[i] == null)
                {
                    shape[i] = ' ';
                }
            }
            String shapeString = new String(shape);
            shapedRecipe.shape(shapeString.substring(0, 3), shapeString.substring(3, 6), shapeString.substring(6, 9));

            //Set ingredients
            for (int i = 0; i < shape.length; i++)
            {
                if (shape[i] != ' ')
                {
                    shapedRecipe.setIngredient(shape[i], new RecipeChoice.ExactChoice(ingredients[i].getItemStack()));
                }
            }
            recipe = shapedRecipe;
        }
        else
        {
            ShapelessRecipe shapelessRecipe = new ShapelessRecipe(key, result.getItemStack(amount));
            for (CustomItemType customItemType : ingredients)
            {
                shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(customItemType.getItemStack()));
            }
            recipe = shapelessRecipe;
        }
    }

    public Recipe getRecipe()
    {
        return recipe;
    }
}
