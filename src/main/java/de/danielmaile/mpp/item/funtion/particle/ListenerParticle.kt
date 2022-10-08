package de.danielmaile.mpp.item.funtion.particle

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ItemType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class ListenerParticle : Listener {

    @EventHandler
    fun onChangeArmor(event: PlayerArmorChangeEvent) {
        val player = event.player
        val oldItemType = ItemType.fromTag(event.oldItem)
        val newItemType = ItemType.fromTag(event.newItem)

        //Player gets ring above head when wearing valkyrie ring
        if (oldItemType == ItemType.VALKYRE_RING) {
            inst().particleManager.removeParticleType(player, ParticleManager.ParticleType.VALKYRE_RING)
        }
        if (newItemType == ItemType.VALKYRE_RING) {
            inst().particleManager.addParticleType(player, ParticleManager.ParticleType.VALKYRE_RING)
        }

        //Player gets wings when wearing valkyrie wings
        if (oldItemType == ItemType.VALKYRE_WINGS) {
            inst().particleManager.removeParticleType(player, ParticleManager.ParticleType.VALKYRE_WINGS)
        }
        if (newItemType == ItemType.VALKYRE_WINGS) {
            inst().particleManager.addParticleType(player, ParticleManager.ParticleType.VALKYRE_WINGS)
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        inst().particleManager.removePlayer(event.player)
    }
}