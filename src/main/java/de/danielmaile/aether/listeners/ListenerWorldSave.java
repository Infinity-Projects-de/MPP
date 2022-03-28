package de.danielmaile.aether.listeners;

import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

public class ListenerWorldSave implements Listener
{
    @EventHandler
    public void onSave(WorldSaveEvent event)
    {
        if(!event.getWorld().equals(AetherWorld.getWorld())) return;
        AetherWorld.getObjectManager().save();
    }
}
