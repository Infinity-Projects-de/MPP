package de.danielmaile.mpp.demo

import de.danielmaile.mpp.inst
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class ListenerJoinDemo : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (!inst().validLicense) {
            Bukkit.getScheduler().runTaskLater(inst(), Runnable {
                Bukkit.broadcast(
                    MiniMessage.miniMessage().deserialize(
                        "<gradient:#FFC733:#FFEC33>This Server uses the</gradient> <gradient:#AC33FF:#FF33E0>Project MPP Plugin</gradient> " +
                                "<gradient:#FF3333:#FF9333>(no Licence).</gradient> <gradient:#FFC733:#FFEC33>Visit </gradient><gradient:#AC33FF:#FF33E0> " +
                                "<click:OPEN_URL:https://patreon.com/lamaprojects><gradient:#AC33FF:#FF33E0>https://patreon.com/lamaprojects</gradient></click> " +
                                "<gradient:#FFC733:#FFEC33>to get a licence and</gradient> <gradient:#7AFF33:#33FF93>support </gradient><gradient:#FFC733:#FFEC33>us.</gradient>"
                    )
                )
            }, 5L)

            Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
                event.player.sendActionBar(
                    MiniMessage.miniMessage().deserialize("<gradient:#FFC733:#FFEC33>Visit us at </gradient><gradient:#AC33FF:#FF33E0>https://patreon.com/lamaprojects</gradient>")
                )
            }, 0L, 20L)
        }
    }
}