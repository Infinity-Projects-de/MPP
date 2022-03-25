package de.danielmaile.aether.item;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ListenerCustomItems implements Listener
{
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event)
    {
        if (!(event.getEntity() instanceof LivingEntity victim)) return;
        if (!(event.getDamager() instanceof LivingEntity)) return;
        if (!(event.getDamager() instanceof Player player)) return;

        ItemStack itemInUse = player.getInventory().getItemInMainHand();
        CustomItemType customItemType = CustomItemType.getFromTag(itemInUse);
        if (customItemType == null) return;

        switch (customItemType)
        {
            case LIGHTNING_SWORD -> victim.getWorld().strikeLightning(victim.getLocation());
            case FLAME_SWORD, SUN_SWORD -> victim.setFireTicks(300);
            case ICE_SWORD -> {
                victim.setFreezeTicks(30);
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 4, false, false));
            }
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event)
    {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!(event.getHitEntity() instanceof Player player)) return;

        ArmorSet equippedSet = ArmorSet.getEquippedSet(player);
        if (equippedSet != ArmorSet.GRAVITITE && equippedSet != ArmorSet.ZANITE) return;

        arrow.setVelocity(arrow.getVelocity().normalize().multiply(-0.1));
        event.setCancelled(true);
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (ArmorSet.getEquippedSet(player) != ArmorSet.GRAVITITE) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onChangeArmor(PlayerArmorChangeEvent event)
    {
        Player player = event.getPlayer();
        if (ArmorSet.getEquippedSet(player) == ArmorSet.VALKYRE)
        {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, false, false));
        }
        else
        {
            player.removePotionEffect(PotionEffectType.REGENERATION);
        }

        if (CustomItemType.getFromTag(player.getEquipment().getBoots()) == CustomItemType.VALKYRE_BOOTS)
        {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 0, false, false));
        }
        else
        {
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        }

        if (CustomItemType.getFromTag(player.getEquipment().getHelmet()) == CustomItemType.VALKYRE_RING)
        {
            AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (maxHealth != null)
            {
                maxHealth.setBaseValue(24);
            }
        }
        else
        {
            AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (maxHealth != null)
            {
                maxHealth.setBaseValue(20);
            }
        }
    }
}
