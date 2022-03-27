package de.danielmaile.aether.item.funtion.particle;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import de.danielmaile.aether.Aether;
import de.danielmaile.aether.item.ItemType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerParticle implements Listener
{
    @EventHandler
    public void onChangeArmor(PlayerArmorChangeEvent event)
    {
        Player player = event.getPlayer();
        ItemType oldItemType = ItemType.fromTag(event.getOldItem());
        ItemType newItemType = ItemType.fromTag(event.getNewItem());

        //Player gets ring above head when wearing valkyrie ring
        if (oldItemType == ItemType.VALKYRE_RING)
        {
            Aether.getParticleManager().removeParticleType(player, ParticleManager.ParticleType.VALKYRE_RING);
        }

        if (newItemType == ItemType.VALKYRE_RING)
        {
            Aether.getParticleManager().addParticleType(player, ParticleManager.ParticleType.VALKYRE_RING);
        }

        //Player gets wings when wearing valkyrie wings
        if (oldItemType == ItemType.VALKYRE_WINGS)
        {
            Aether.getParticleManager().removeParticleType(player, ParticleManager.ParticleType.VALKYRE_WINGS);
        }

        if (newItemType == ItemType.VALKYRE_WINGS)
        {
            Aether.getParticleManager().addParticleType(player, ParticleManager.ParticleType.VALKYRE_WINGS);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Aether.getParticleManager().removePlayer(event.getPlayer());
    }
}
