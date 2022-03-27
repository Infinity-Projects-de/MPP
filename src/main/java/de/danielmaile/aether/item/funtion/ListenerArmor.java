package de.danielmaile.aether.item.funtion;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import de.danielmaile.aether.Aether;
import de.danielmaile.aether.item.ArmorSet;
import de.danielmaile.aether.item.ItemType;
import de.danielmaile.aether.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ListenerArmor implements Listener
{
    private final List<Player> boosted = new ArrayList<>();

    public ListenerArmor()
    {
        //Remove player from boosted list if he is on ground
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Aether.getInstance(), () ->
        {
            for (Player player : Bukkit.getOnlinePlayers())
            {
                if (boosted.contains(player) && !player.isFlying() &&
                        player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)
                {
                    boosted.remove(player);
                }
            }
        }, 0L, 5L);
    }

    //When wearing zanite or gravitite or armor arrows bounce off
    @EventHandler
    public void onArrowHit(ProjectileHitEvent event)
    {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!(event.getHitEntity() instanceof Player player)) return;

        ArmorSet equippedSet = ArmorSet.getEquippedSet(player);
        if (equippedSet != ArmorSet.ZANITE && equippedSet != ArmorSet.GRAVITITE) return;

        arrow.setVelocity(arrow.getVelocity().normalize().multiply(-0.1));
        event.setCancelled(true);
    }

    //When wearing gravitite armor player is immune to fall damage
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
        ItemType oldItemType = ItemType.fromTag(event.getOldItem());
        ItemType newItemType = ItemType.fromTag(event.getNewItem());

        //Player gets permanent slow fall when wearing valkyrie boots
        if (oldItemType == ItemType.VALKYRE_BOOTS) { player.removePotionEffect(PotionEffectType.SLOW_FALLING); }
        if (newItemType == ItemType.VALKYRE_BOOTS) { Utils.addPermEffect(player, PotionEffectType.SLOW_FALLING); }

        //Player gets +2 health when wearing valkyrie ring
        if (oldItemType == ItemType.VALKYRE_RING) { Utils.addMaxHealth(player, -4d); }
        if (newItemType == ItemType.VALKYRE_RING) { Utils.addMaxHealth(player, 4d); }

        //Player gets permanent regeneration fall when wearing full valkyrie armor set
        if (ArmorSet.getEquippedSet(player) == ArmorSet.VALKYRIE)
        {
            Utils.addPermEffect(player, PotionEffectType.REGENERATION);
        }
        else if (ArmorSet.VALKYRIE.contains(oldItemType))
        {
            player.removePotionEffect(PotionEffectType.REGENERATION);
        }
    }

    //If player has full valkyre armor he can use elytra boost once per flight
    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        if (boosted.contains(player)) return;
        if (ArmorSet.getEquippedSet(player) != ArmorSet.VALKYRIE) return;
        event.setCancelled(true);

        boosted.add(player);
        player.setVelocity(player.getLocation().getDirection().multiply(5));
    }
}
