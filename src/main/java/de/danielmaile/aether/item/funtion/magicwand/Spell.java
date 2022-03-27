package de.danielmaile.aether.item.funtion.magicwand;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.util.NBTEditor;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public enum Spell
{
    LLAMA_SPELL(20, Color.PURPLE),
    YEET_SPELL(20, Color.BLUE),
    SOUND_BEAM(20, Color.RED);

    private final Component name;
    private final int range;
    private final Color color;

    public Component getName()
    {
        return name;
    }

    public int getRange()
    {
        return range;
    }

    public Color getColor()
    {
        return color;
    }

    Spell(int range, Color color)
    {
        this.name = Aether.getLanguageManager().getComponent("items.MAGIC_WAND.spells." + name());
        this.range = range;
        this.color = color;
    }

    public Spell next()
    {
        return values()[(this.ordinal() + 1) % values().length];
    }

    @Nullable
    public static Spell fromTag(ItemStack itemStack)
    {
        try
        {
            return Spell.valueOf(NBTEditor.getString(itemStack, MagicWand.SELECTED_SPELL_TAG));
        }
        catch (NullPointerException | IllegalArgumentException exception)
        {
            return null;
        }
    }

}
