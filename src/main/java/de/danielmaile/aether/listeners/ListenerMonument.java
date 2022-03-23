package de.danielmaile.aether.listeners;

import de.danielmaile.aether.util.SimpleLocation;
import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerMonument implements Listener
{
    @EventHandler
    public void onMonumentClick(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getWorld().equals(AetherWorld.getWorld())) return;

        Location clickLocation = event.getClickedBlock().getLocation();
        SimpleLocation targetLocation = AetherWorld.getMonumentTargetList().get(SimpleLocation.fromLocation(clickLocation));
        if (targetLocation == null) return;
        event.getPlayer().teleport(new Location(AetherWorld.getWorld(), targetLocation.getX(), targetLocation.getY(), targetLocation.getZ()));
    }
}
