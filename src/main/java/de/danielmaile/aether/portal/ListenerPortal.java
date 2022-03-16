/**
 * Daniel Maile - 2022
 */
package de.danielmaile.aether.portal;

import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class ListenerPortal implements Listener
{
    @EventHandler
    public void onWaterBucketClick(PlayerBucketEmptyEvent event)
    {
        if (event.getBucket() != Material.WATER_BUCKET) return;
        if (!AetherPortal.checkPortal(event.getBlock().getLocation(), true)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onAetherTeleport(PlayerPortalEvent event)
    {
        if(AetherWorld.isLoaded())
        {
            World aether = AetherWorld.getWorld();
            if(aether != null)
            {
                event.setTo(aether.getSpawnLocation());
            }
        }

        /*
        Location portalLocation = event.getPlayer().getLocation().clone();
        System.out.println("Portal at? " + event.getPlayer().getLocation().getBlock().getLocation());
        System.out.println(AetherPortal.checkPortal(event.getPlayer().getLocation(), false));
        event.setCancelled(true);
        System.out.println("Cancelled"); */
    }
}
