package de.danielmaile.aether.portal;

import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerPortal implements Listener
{
    @EventHandler
    public void onWaterBucketClick(PlayerBucketEmptyEvent event)
    {
        if (event.getBucket() != Material.WATER_BUCKET) return;
        if (!AetherPortal.checkPortal(event.getBlock().getLocation(), false)) return;

        //Explode Portal in Nether or End
        if (event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER || event.getPlayer().getWorld().getEnvironment() == World.Environment.THE_END)
        {
            event.getPlayer().getWorld().createExplosion(event.getBlockClicked().getLocation(), 20, true);
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onAetherTeleport(PlayerMoveEvent event)
    {
        if (AetherWorld.getWorld() == null) return;
        if (!AetherPortal.checkPortal(event.getTo(), true)) return;

        Location toLocation = event.getPlayer().getLocation().clone();
        toLocation.setWorld(event.getPlayer().getWorld().equals(AetherWorld.getWorld()) ? Bukkit.getWorlds().get(0) : AetherWorld.getWorld());

        Location portalLocation = AetherPortal.findPortalInRadius(toLocation, 32);
        if (portalLocation != null)
        {
            event.getPlayer().teleport(portalLocation.add(0, 4, 0));
        }
        else
        {
            toLocation.setY(toLocation.getWorld().getHighestBlockYAt(toLocation) + 2);
            AetherPortal.createPortal(toLocation);
            event.getPlayer().teleport(toLocation.add(0, 4, 0));
        }

    }
}
