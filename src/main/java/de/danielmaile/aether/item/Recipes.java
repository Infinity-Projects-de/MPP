package de.danielmaile.aether.item;

import de.danielmaile.aether.Aether;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

public enum Recipes
{
    AETHER_WOODEN_SWORD(true, ItemType.AETHER_WOODEN_SWORD, 1,
            null, ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_STICK, null),
    AETHER_WOODEN_SWORD1(true, ItemType.AETHER_WOODEN_SWORD, 1,
            null, null, ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_STICK),
    AETHER_WOODEN_SWORD2(true, ItemType.AETHER_WOODEN_SWORD, 1,
            ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_STICK, null, null),
    AETHER_WOODEN_SWORD3(true, ItemType.AETHER_WOODEN_SWORD, 1,
            null, ItemType.AETHER_BIRCH_PLANKS, null, null, ItemType.AETHER_BIRCH_PLANKS, null, null, ItemType.AETHER_STICK, null),
    AETHER_WOODEN_SWORD4(true, ItemType.AETHER_WOODEN_SWORD, 1,
            null, null, ItemType.AETHER_BIRCH_PLANKS, null, null, ItemType.AETHER_BIRCH_PLANKS, null, null, ItemType.AETHER_STICK),
    AETHER_WOODEN_SWORD5(true, ItemType.AETHER_WOODEN_SWORD, 1,
            ItemType.AETHER_BIRCH_PLANKS, null, null, ItemType.AETHER_BIRCH_PLANKS, null, null, ItemType.AETHER_STICK, null, null),
    AETHER_WOODEN_HOE(true, ItemType.AETHER_WOODEN_HOE, 1,
            ItemType.AETHER_OAK_PLANKS, ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    AETHER_WOODEN_HOE1(true, ItemType.AETHER_WOODEN_HOE, 1,
            ItemType.AETHER_OAK_PLANKS, ItemType.AETHER_OAK_PLANKS, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null, null),
    AETHER_WOODEN_HOE2(true, ItemType.AETHER_WOODEN_HOE, 1,
            null, ItemType.AETHER_OAK_PLANKS, ItemType.AETHER_OAK_PLANKS, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    AETHER_WOODEN_HOE3(true, ItemType.AETHER_WOODEN_HOE, 1,
            null, ItemType.AETHER_OAK_PLANKS, ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK),
    AETHER_WOODEN_PICKAXE(true, ItemType.AETHER_WOODEN_PICKAXE,
            1, ItemType.AETHER_OAK_PLANKS, ItemType.AETHER_OAK_PLANKS, ItemType.AETHER_OAK_PLANKS, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    AETHER_WOODEN_SHOVEL(true, ItemType.AETHER_WOODEN_SHOVEL,
            1, ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null, null),
    AETHER_WOODEN_SHOVEL1(true, ItemType.AETHER_WOODEN_SHOVEL,
            1, null, ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    AETHER_WOODEN_SHOVEL2(true, ItemType.AETHER_WOODEN_SHOVEL,
            1, null, null, ItemType.AETHER_OAK_PLANKS, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK),
    AETHER_WOODEN_AXE(true, ItemType.AETHER_WOODEN_AXE,
            1, ItemType.AETHER_OAK_PLANKS, ItemType.AETHER_OAK_PLANKS, null, ItemType.AETHER_OAK_PLANKS, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    AETHER_WOODEN_AXE1(true, ItemType.AETHER_WOODEN_AXE,
            1, null, ItemType.AETHER_OAK_PLANKS, ItemType.AETHER_OAK_PLANKS, null, ItemType.AETHER_STICK, ItemType.AETHER_OAK_PLANKS, null, ItemType.AETHER_STICK, null),
    AETHER_STONE_SWORD(true, ItemType.AETHER_STONE_SWORD,
            1, ItemType.AETHER_STONE, null, null, ItemType.AETHER_STONE, null, null, ItemType.AETHER_STICK, null, null),
    AETHER_STONE_SWORD1(true, ItemType.AETHER_STONE_SWORD,
            1, null, ItemType.AETHER_STONE, null, null, ItemType.AETHER_STONE, null, null, ItemType.AETHER_STICK, null),
    AETHER_STONE_SWORD2(true, ItemType.AETHER_STONE_SWORD,
            1, null, null, ItemType.AETHER_STONE, null, null, ItemType.AETHER_STONE, null, null, ItemType.AETHER_STICK),
    AETHER_STONE_HOE(true, ItemType.AETHER_STONE_HOE,
            1, ItemType.AETHER_STONE, ItemType.AETHER_STONE, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null, null),
    AETHER_STONE_HOE1(true, ItemType.AETHER_STONE_HOE,
            1, ItemType.AETHER_STONE, ItemType.AETHER_STONE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    AETHER_STONE_HOE2(true, ItemType.AETHER_STONE_HOE,
            1, null, ItemType.AETHER_STONE, ItemType.AETHER_STONE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK),
    AETHER_STONE_HOE3(true, ItemType.AETHER_STONE_HOE, 1,
            null, ItemType.AETHER_STONE, ItemType.AETHER_STONE, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    AETHER_STONE_PICKAXE(true, ItemType.AETHER_STONE_PICKAXE, 1,
            ItemType.AETHER_STONE, ItemType.AETHER_STONE, ItemType.AETHER_STONE, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    AETHER_STONE_SHOVEL(true, ItemType.AETHER_STONE_SHOVEL, 1,
            ItemType.AETHER_STONE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null, null),
    AETHER_STONE_SHOVEL1(true, ItemType.AETHER_STONE_SHOVEL, 1,
            null, ItemType.AETHER_STONE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    AETHER_STONE_SHOVEL2(true, ItemType.AETHER_STONE_SHOVEL, 1,
            null, null, ItemType.AETHER_STONE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK),
    AETHER_STONE_AXE(true, ItemType.AETHER_STONE_AXE, 1,
            ItemType.AETHER_STONE, ItemType.AETHER_STONE, null, ItemType.AETHER_STONE, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    AETHER_STONE_AXE1(true, ItemType.AETHER_STONE_AXE, 1,
            null, ItemType.AETHER_STONE, ItemType.AETHER_STONE, null, ItemType.AETHER_STICK, ItemType.AETHER_STONE, null, ItemType.AETHER_STICK, null),
    AETHER_STICK(false, ItemType.AETHER_STICK, 2,
            ItemType.AETHER_OAK_PLANKS),
    AETHER_STICK1(false, ItemType.AETHER_STICK, 2,
            ItemType.AETHER_BIRCH_PLANKS),
    ZANITE_SWORD(true, ItemType.ZANITE_SWORD, 1,
            ItemType.ZANITE_STONE, null, null, ItemType.ZANITE_STONE, null, null, ItemType.AETHER_STICK, null, null),
    ZANITE_SWORD1(true, ItemType.ZANITE_SWORD, 1,
            null, ItemType.ZANITE_STONE, null, null, ItemType.ZANITE_STONE, null, null, ItemType.AETHER_STICK, null),
    ZANITE_SWORD2(true, ItemType.ZANITE_SWORD, 1,
            null, null, ItemType.ZANITE_STONE, null, null, ItemType.ZANITE_STONE, null, null, ItemType.AETHER_STICK),
    ZANITE_HOE(true, ItemType.ZANITE_HOE, 1,
            ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null, null),
    ZANITE_HOE1(true, ItemType.ZANITE_HOE, 1,
            ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    ZANITE_HOE2(true, ItemType.ZANITE_HOE, 1,
            null, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    ZANITE_HOE3(true, ItemType.ZANITE_HOE, 1,
            null, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK),
    ZANITE_PICKAXE(true, ItemType.ZANITE_PICKAXE, 1,
            ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    ZANITE_SHOVEL(true, ItemType.ZANITE_SHOVEL, 1,
            ItemType.ZANITE_STONE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null, null),
    ZANITE_SHOVEL1(true, ItemType.ZANITE_SHOVEL, 1,
            null, ItemType.ZANITE_STONE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    ZANITE_SHOVEL2(true, ItemType.ZANITE_SHOVEL, 1,
            null, null, ItemType.ZANITE_STONE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK),
    ZANITE_AXE(true, ItemType.ZANITE_AXE, 1,
            ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    ZANITE_AXE1(true, ItemType.ZANITE_AXE, 1,
            null, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, ItemType.AETHER_STICK, ItemType.ZANITE_STONE, null, ItemType.AETHER_STICK, null),
    GRAVITITE_SWORD(true, ItemType.GRAVITITE_SWORD, 1,
            ItemType.GRAVITITE_PLATE, null, null, ItemType.GRAVITITE_PLATE, null, null, ItemType.AETHER_STICK, null, null),
    GRAVITITE_SWORD1(true, ItemType.GRAVITITE_SWORD, 1,
            null, ItemType.GRAVITITE_PLATE, null, null, ItemType.GRAVITITE_PLATE, null, null, ItemType.AETHER_STICK, null),
    GRAVITITE_SWORD2(true, ItemType.GRAVITITE_SWORD, 1,
            null, null, ItemType.GRAVITITE_PLATE, null, null, ItemType.GRAVITITE_PLATE, null, null, ItemType.AETHER_STICK),
    GRAVITITE_HOE(true, ItemType.GRAVITITE_HOE, 1,
            ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, null, ItemType.AETHER_STICK, null, ItemType.AETHER_STICK, null, null, null),
    GRAVITITE_HOE1(true, ItemType.GRAVITITE_HOE, 1,
            ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    GRAVITITE_HOE2(true, ItemType.GRAVITITE_HOE, 1,
            null, ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    GRAVITITE_HOE3(true, ItemType.GRAVITITE_HOE, 1,
            null, ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK),
    GRAVITITE_PICKAXE(true, ItemType.GRAVITITE_PICKAXE, 1,
            ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    GRAVITITE_SHOVEL(true, ItemType.GRAVITITE_SHOVEL, 1,
            ItemType.GRAVITITE_ORE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null, null),
    GRAVITITE_SHOVEL1(true, ItemType.GRAVITITE_SHOVEL, 1,
            null, ItemType.GRAVITITE_ORE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    GRAVITITE_SHOVEL2(true, ItemType.GRAVITITE_SHOVEL, 1,
            null, null, ItemType.GRAVITITE_ORE, null, null, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK),
    GRAVITITE_AXE(true, ItemType.GRAVITITE_AXE, 1,
            ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, null, ItemType.GRAVITITE_ORE, ItemType.AETHER_STICK, null, null, ItemType.AETHER_STICK, null),
    GRAVITITE_AXE1(true, ItemType.GRAVITITE_AXE, 1,
            null, ItemType.GRAVITITE_ORE, ItemType.GRAVITITE_ORE, null, ItemType.AETHER_STICK, ItemType.GRAVITITE_ORE, null, ItemType.AETHER_STICK, null),
    LIGHTNING_SWORD(false, ItemType.LIGHTNING_SWORD, 1,
            ItemType.ZANITE_SWORD, ItemType.LIGHTNING_ESSENCE),
    FLAME_SWORD(false, ItemType.FLAME_SWORD, 1,
            ItemType.ZANITE_SWORD, ItemType.FIRE_ESSENCE),
    ICE_SWORD(false, ItemType.ICE_SWORD, 1,
            ItemType.ZANITE_SWORD, ItemType.ICE_ESSENCE),
    SUN_SWORD(true, ItemType.SUN_SWORD, 1,
            ItemType.LIGHTNING_ESSENCE, ItemType.FIRE_ESSENCE, ItemType.ICE_ESSENCE, null, null, ItemType.SUN_STONE, null, ItemType.AETHER_STICK, null),
    ZANITE_HELMET(true, ItemType.ZANITE_HELMET, 1,
            ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE, null, null, null),
    ZANITE_CHESTPLATE(true, ItemType.ZANITE_CHESTPLATE, 1,
            ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE),
    ZANITE_LEGGINGS(true, ItemType.ZANITE_LEGGINGS, 1,
            ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE),
    ZANITE_BOOTS(true, ItemType.ZANITE_BOOTS, 1,
            null, null, null, ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE, ItemType.ZANITE_STONE, null, ItemType.ZANITE_STONE),
    GRAVITITE_HELMET(true, ItemType.GRAVITITE_HELMET, 1,
            ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE, null, null, null),
    GRAVITITE_CHESTPLATE(true, ItemType.GRAVITITE_CHESTPLATE, 1,
            ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE),
    GRAVITITE_LEGGINGS(true, ItemType.GRAVITITE_LEGGINGS, 1,
            ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE),
    GRAVITITE_BOOTS(true, ItemType.GRAVITITE_BOOTS, 1,
            null, null, null, ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_PLATE, null, ItemType.GRAVITITE_PLATE),
    AETHER_OAK_PLANKS(false, ItemType.AETHER_OAK_PLANKS, 1,
            ItemType.AETHER_OAK_LOG),
    AETHER_BIRCH_PLANKS(false, ItemType.AETHER_BIRCH_PLANKS, 1,
            ItemType.AETHER_BIRCH_LOG),
    AETHER_ACACIA_PLANKS(false, ItemType.AETHER_ACACIA_PLANKS, 1,
            ItemType.AETHER_ACACIA_LOG),
    AETHER_SPRUCE_PLANKS(false, ItemType.AETHER_SPRUCE_PLANKS, 1,
            ItemType.AETHER_SPRUCE_LOG),
    AETHER_DARK_JUNGLE_PLANKS(false, ItemType.AETHER_JUNGLE_PLANKS, 1,
            ItemType.AETHER_JUNGLE_LOG),
    AETHER_DARK_OAK_PLANKS(false, ItemType.AETHER_DARK_OAK_PLANKS, 1,
            ItemType.AETHER_DARK_OAK_LOG),
    GRAVITITE_SMELTING(ItemType.GRAVITITE_PLATE, ItemType.GRAVITITE_ORE, 50, 30),
    ZANITE_SMELTING(ItemType.ZANITE_STONE, ItemType.ZANITE_ORE, 50, 20);

    private final Recipe recipe;

    //Smelting
    Recipes(ItemType result, ItemType ingredient, float xp, int time)
    {
        NamespacedKey key = new NamespacedKey(Aether.getInstance(), name());
        this.recipe = new FurnaceRecipe(key, result.getItemStack()
                , new RecipeChoice.ExactChoice(ingredient.getItemStack()), xp, time);
    }

    //Crafting
    Recipes(boolean shaped, ItemType result, int amount, ItemType... ingredients)
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
            for (ItemType itemType : ingredients)
            {
                shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(itemType.getItemStack()));
            }
            recipe = shapelessRecipe;
        }
    }

    public Recipe getRecipe()
    {
        return recipe;
    }
}
