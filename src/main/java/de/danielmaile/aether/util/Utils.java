package de.danielmaile.aether.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.intellij.lang.annotations.RegExp;

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

    public static void setMaxHealth(Player player, double value)
    {
        AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null)
        {
            maxHealth.setBaseValue(value);
        }
    }

    public static boolean isGrounded(Player player)
    {
        return player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR;
    }

    public static Component replace(Component component, @RegExp String regex, Component replacement)
    {
        return component.replaceText(TextReplacementConfig.builder().match(regex).replacement(replacement).build());
    }
}
