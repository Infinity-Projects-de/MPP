package de.danielmaile.aether.listeners

import de.danielmaile.aether.inst
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldSaveEvent

class ListenerWorldSave : Listener {

    @EventHandler
    fun onSave(event: WorldSaveEvent) {
        if (event.world !=  inst().aetherWorld.getWorld()) return
        inst().getObjectManager().save()
    }
}