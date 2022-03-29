package de.danielmaile.aether.listeners;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static de.danielmaile.aether.worldgen.AetherWorld.getWorld;

public class ListenerAetherVoid implements Listener
{
    /***
     * Teleports players back to the overworld when they fall out of the aether
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        Location playerLoc = player.getLocation();
        if (!player.getWorld().equals(getWorld())) return;
        if (playerLoc.getY() > 64 || playerLoc.getY() < -256) return;

        Location destination = new Location(Bukkit.getWorlds().get(0), playerLoc.getX(), 400, playerLoc.getZ());
        player.teleport(destination);
        event.setCancelled(true);
    }

    /***
     * Kill all entities that fall out of the aether
     */
    @EventHandler
    public void onEntityMove(EntityMoveEvent event)
    {
        Entity entity = event.getEntity();
        if (!entity.getWorld().equals(getWorld())) return;
        if (entity.getLocation().getY() > 64 || entity.getLocation().getY() < -256) return;

        entity.remove();
        event.setCancelled(true);
    }

    /***
     * Stop water and lava from flowing out of aether
     */
    @EventHandler
    public void onBlockFlow(BlockFromToEvent event)
    {
        if (!event.getBlock().getWorld().equals(getWorld())) return;
        if (event.getToBlock().getY() > 64 || event.getToBlock().getY() < -256) return;
        event.setCancelled(true);
    }
}
