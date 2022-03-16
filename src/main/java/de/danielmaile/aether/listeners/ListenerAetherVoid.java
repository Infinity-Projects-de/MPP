/***
 * Daniel Maile - 2022
 */
package de.danielmaile.aether.listeners;

import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerAetherVoid implements Listener
{
    /***
     * Teleports players back to the overworld when they fall out of the aether
     */
    @EventHandler
    public void onVoidDamage(EntityDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if (player.getWorld().equals(Bukkit.getWorld(AetherWorld.WORLD_KEY)))
        {
            Location destination = player.getLocation().clone();
            destination.setWorld(Bukkit.getWorld("world"));
            destination.setY(400);
            player.teleport(destination);
            event.setCancelled(true);
        }
    }
}
