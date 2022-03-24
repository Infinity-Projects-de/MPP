package de.danielmaile.aether.listeners;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.util.SimpleLocation;
import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.HashMap;

public class ListenerMonument implements Listener
{
    private final HashMap<Player, ArrayList<Location>> monumentClickMap = new HashMap<>();

    @EventHandler
    public void onMonumentClick(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getWorld().equals(AetherWorld.getWorld())) return;

        Location clickLocation = event.getClickedBlock().getLocation();
        SimpleLocation targetLocation = AetherWorld.getMonumentTargetList().get(SimpleLocation.fromLocation(clickLocation));
        if (targetLocation == null) return;
        event.setCancelled(true);

        ArrayList<Location> clickedLocations = monumentClickMap.get(event.getPlayer());
        if(clickedLocations != null && clickedLocations.contains(clickLocation))
        {
            clickedLocations.remove(clickLocation);
            event.getPlayer().teleport(new Location(AetherWorld.getWorld(), targetLocation.getX(), targetLocation.getY(), targetLocation.getZ()));
            return;
        }

        if(clickedLocations == null)
        {
            clickedLocations = new ArrayList<>();
        }

        clickedLocations.add(clickLocation);
        monumentClickMap.put(event.getPlayer(), clickedLocations);
        event.getPlayer().sendMessage(Aether.PREFIX + "Klicke nochmals auf den Block um dich in den Dungeon zu teleportieren.");
    }

    @EventHandler
    public void onMonumentBreak(BlockBreakEvent event)
    {
        SimpleLocation targetLocation = AetherWorld.getMonumentTargetList().get(SimpleLocation.fromLocation(event.getBlock().getLocation()));
        event.setCancelled(targetLocation != null);
    }
}
