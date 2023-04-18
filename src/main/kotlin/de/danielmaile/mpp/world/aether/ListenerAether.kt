/*
 * This file is part of MPP.
 * Copyright (c) 2023 by it's authors. All rights reserved.
 * MPP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MPP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MPP.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.danielmaile.mpp.world.aether

import de.danielmaile.mpp.aetherWorld
import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.world.EntitiesLoadEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class ListenerAether : Listener {

    /**
     * Adds slow falling effect to all mobs that are loaded in the aether.
     */
    @EventHandler
    fun onEntitiesLoad(event: EntitiesLoadEvent) {
        if (event.world != aetherWorld()) return
        for (entity in event.entities) {
            if (!entity.isValid) continue
            if (entity !is LivingEntity) continue
            if (entity is Player) continue

            entity.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 0, false, false))
        }
    }

    /**
     * Teleports players back to the overworld when they fall out of the aether
     */
    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val playerLoc = player.location
        if (player.world != aetherWorld()) return
        if (playerLoc.y > 64 || playerLoc.y < -256) return

        val destinationWorld = Bukkit.getWorlds()[0]
        val destination = Location(
            destinationWorld,
            playerLoc.x,
            destinationWorld.getHighestBlockYAt(playerLoc.x.toInt(), playerLoc.z.toInt()).toDouble(),
            playerLoc.z
        )
        player.teleport(destination)
        event.isCancelled = true
    }

    /**
     * Kills all entities that fall out of the aether
     */
    @EventHandler
    fun onEntityMove(event: EntityMoveEvent) {
        val entity: Entity = event.entity
        if (entity.world != aetherWorld()) return
        if (entity.location.y > 64 || entity.location.y < -256) return

        entity.remove()
        event.isCancelled = true
    }

    /**
     * Stops water and lava from flowing out of aether
     */
    @EventHandler
    fun onBlockFlow(event: BlockFromToEvent) {
        if (event.block.world != aetherWorld()) return
        if (event.toBlock.y > 64 || event.toBlock.y < -256) return
        event.isCancelled = true
    }
}
