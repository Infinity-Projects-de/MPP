package de.danielmaile.aether.portal;

import de.danielmaile.aether.worldgen.AetherWorld;
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
        event.getPlayer().teleport(AetherWorld.getWorld().getSpawnLocation());
    }
}
