package de.danielmaile.aether.item;

import de.danielmaile.aether.Aether;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public enum CustomRecipe
{
    AETHER_STICK(false, CustomItemType.AETHER_STICK, 2, CustomItemType.AETHER_OAK_PLANKS),
    HOLYSTONE_SWORD(true, CustomItemType.HOLYSTONE_SWORD, 1, null, CustomItemType.HOLYSTONE, null, null, CustomItemType.HOLYSTONE, null, null, CustomItemType.AETHER_STICK, null);

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
            shapedRecipe.shape(shapeString.substring(0, 2), shapeString.substring(3, 5), shapeString.substring(6, 8));

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
