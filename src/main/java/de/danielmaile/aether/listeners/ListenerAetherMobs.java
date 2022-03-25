package de.danielmaile.aether.listeners;

import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ListenerAetherMobs implements Listener
{
    @EventHandler
    public void onEntitiesLoad(EntitiesLoadEvent event)
    {
        if (!event.getWorld().equals(AetherWorld.getWorld())) return;
        for (Entity entity : event.getEntities())
        {
            if(!entity.isValid()) continue;
            if(!(entity instanceof LivingEntity livingEntity)) continue;
            if(livingEntity instanceof Player) continue;

            livingEntity.addPotionEffect(
                    new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 1, false, false));
            if(livingEntity instanceof Llama)
            {
                livingEntity.addPotionEffect(
                        new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2, false, false));
            }
        }
    }
}
