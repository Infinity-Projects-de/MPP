package de.danielmaile.aether.item;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ListenerCustomItems implements Listener
{
    //When hit by lightning sword -> lightning strikes victim
    //When hit by flame or sun sword -> set victim on fire
    //When hit by ice sword -> give victim slowness and free effect
    @EventHandler
    public void onSwordDamage(EntityDamageByEntityEvent event)
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
}
