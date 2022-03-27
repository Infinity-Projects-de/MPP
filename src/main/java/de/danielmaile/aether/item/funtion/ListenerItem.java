package de.danielmaile.aether.item.funtion;

import de.danielmaile.aether.item.ItemType;
import de.danielmaile.aether.util.NBTEditor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ListenerItem implements Listener
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
        ItemType itemType = ItemType.fromTag(itemInUse);
        if (itemType == null) return;

        switch (itemType)
        {
            case LIGHTNING_SWORD -> victim.getWorld().strikeLightning(victim.getLocation());
            case FLAME_SWORD, SUN_SWORD -> victim.setFireTicks(300);
            case ICE_SWORD -> {
                victim.setFreezeTicks(40);
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 4, false, false));
            }
        }
    }

    //Prevent use of aether items (iron horse armor material) on horses
    @EventHandler
    public void onSaddleEquip(InventoryClickEvent event)
    {
        if(!(event.getInventory() instanceof HorseInventory)) return;
        if(!NBTEditor.hasKey(event.getCurrentItem(), ItemType.AETHER_ITEM_TAG_KEY)) return;
        event.setCancelled(true);
    }
}
