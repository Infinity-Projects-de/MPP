package de.danielmaile.aether.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.io.InputStream;

public class Utils
{
    public static InputStream getResource(String filename) throws IOException
    {
        return Utils.class.getClassLoader().getResourceAsStream(filename);
    }

    public static void addPermEffect(Player player, PotionEffectType type)
    {
        player.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, 0, false, false));
    }

    public static void addMaxHealth(Player player, double value)
    {
        AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null)
        {
            maxHealth.setBaseValue(maxHealth.getBaseValue() + value);
        }
    }

}
