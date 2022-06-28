package de.danielmaile.lama.aether.item.funtion

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import de.danielmaile.lama.aether.Aether.Companion.instance
import de.danielmaile.lama.aether.inst
import de.danielmaile.lama.aether.item.ArmorSet
import de.danielmaile.lama.aether.item.ItemType
import de.danielmaile.lama.aether.util.addPermEffect
import de.danielmaile.lama.aether.util.getEquippedArmorSet
import de.danielmaile.lama.aether.util.isGrounded
import de.danielmaile.lama.aether.util.setMaximumHealth
import org.bukkit.Bukkit
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityToggleGlideEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.potion.PotionEffectType

class ListenerArmor : Listener {

    private val boosted = ArrayList<Player>()

    init {
        //Remove player from boosted list if he is on ground
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
            for (player: Player in Bukkit.getOnlinePlayers()) {
                if (boosted.contains(player) && !player.isFlying && player.isGrounded()) {
                    boosted.remove(player)
                }
            }
        }, 0L, 5L)
    }

    //When wearing zanite or gravitite or armor arrows bounce off
    @EventHandler
    fun onArrowHit(event: ProjectileHitEvent) {
        if (event.entity !is Arrow) return
        val arrow = event.entity as Arrow

        if (event.hitEntity !is Player) return
        val player = event.hitEntity as Player

        val equippedSet = player.getEquippedArmorSet()
        if (equippedSet != ArmorSet.ZANITE && equippedSet != ArmorSet.GRAVITITE) return

        arrow.velocity = arrow.velocity.normalize().multiply(-0.1)
        event.isCancelled = true
    }

    //When wearing gravitite armor player is immune to fall damage
    @EventHandler
    fun onFallDamage(event: EntityDamageEvent) {
        if (event.entity !is Player) return
        if (event.cause != EntityDamageEvent.DamageCause.FALL) return
        if ((event.entity as Player).getEquippedArmorSet() != ArmorSet.GRAVITITE) return
        event.isCancelled = true
    }

    @EventHandler
    fun onChangeArmor(event: PlayerArmorChangeEvent) {
        val player = event.player
        val oldItemType = ItemType.fromTag(event.oldItem)
        val newItemType = ItemType.fromTag(event.newItem)

        //Player gets permanent slow fall when wearing valkyrie boots
        if (oldItemType == ItemType.VALKYRE_BOOTS) {
            player.removePotionEffect(PotionEffectType.SLOW_FALLING)
        }
        if (newItemType == ItemType.VALKYRE_BOOTS) {
            player.addPermEffect(PotionEffectType.SLOW_FALLING)
        }

        //Player gets +2 health when wearing valkyrie ring
        if (oldItemType == ItemType.VALKYRE_RING) {
            player.setMaximumHealth(20.0)
        }
        if (newItemType == ItemType.VALKYRE_RING) {
            player.setMaximumHealth(24.0)
        }

        //Player gets permanent regeneration fall when wearing full valkyrie armor set
        if (player.getEquippedArmorSet() == ArmorSet.VALKYRIE) {
            player.addPermEffect(PotionEffectType.REGENERATION)
        } else if (ArmorSet.VALKYRIE.contains(oldItemType)) {
            player.removePotionEffect(PotionEffectType.REGENERATION)
        }
    }

    //If player has full valkyre armor he can use elytra boost once per flight
    @EventHandler
    fun onSwapItem(event: PlayerSwapHandItemsEvent) {
        val player = event.player
        if (player.isGrounded()) return
        if (boosted.contains(player)) return
        if (player.getEquippedArmorSet() != ArmorSet.VALKYRIE) return
        event.isCancelled = true

        boosted.add(player)
        player.velocity = player.location.direction.multiply(5)
    }

    @EventHandler
    fun onFlightToggle(event: EntityToggleGlideEvent) {
        if (event.entity !is Player) return
        val player = event.entity as Player

        if (!event.isGliding) return
        if (player.getEquippedArmorSet() != ArmorSet.VALKYRIE) return

        player.sendActionBar(instance.getLanguageManager().getComponent("items.VALKYRE_WINGS.boost_info"))
    }
}