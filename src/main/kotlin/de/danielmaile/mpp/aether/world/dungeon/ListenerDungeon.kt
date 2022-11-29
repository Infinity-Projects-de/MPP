/*
 * This file is part of MPP.
 * Copyright (c) 2022 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.aether.world.dungeon

import de.danielmaile.mpp.aetherWorld
import de.danielmaile.mpp.inst
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.title.Title
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class ListenerDungeon : Listener {

    private val monumentClickMap = HashMap<Player, ArrayList<Location>>()

    @EventHandler
    fun onMonumentClick(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.hand != EquipmentSlot.HAND) return
        val clickedBlock = event.clickedBlock ?: return
        if (clickedBlock.world != aetherWorld()) return

        val clickLocation = clickedBlock.location
        val targetDungeon =
            inst().worldManager.objectManager.getDungeons().firstOrNull { it.monumentLocation == clickLocation }
                ?: return
        val targetLocation = targetDungeon.monumentTargetLocation
        event.isCancelled = true

        var clickedLocations = monumentClickMap[event.player]
        if (clickedLocations != null && clickedLocations.contains(clickLocation)) {
            //Teleport player
            clickedLocations.remove(clickLocation)
            event.player.teleport(targetLocation!!)

            //Send dungeon message
            val mainTitle = inst().getLanguageManager().getComponent("dungeons.entry.title")
            val tagResolver = TagResolver.resolver(Placeholder.parsed("size", targetDungeon.getSize().toString()))
            val subTitle = inst().getLanguageManager().getComponent("dungeons.entry.subtitle", tagResolver)
            val title = Title.title(mainTitle, subTitle)
            event.player.showTitle(title)
            return
        }

        if (clickedLocations == null) {
            clickedLocations = ArrayList()
        }

        clickedLocations.add(clickLocation)
        monumentClickMap[event.player] = clickedLocations
        event.player.sendMessage(
            inst().getLanguageManager().getComponent("messages.prefix")
                .append(inst().getLanguageManager().getComponent("messages.chat.click_to_dungeon_teleport"))
        )
    }

    @EventHandler
    fun onDungeonBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        val player = event.player

        // check for monument break
        val targetDungeon =
            inst().worldManager.objectManager.getDungeons().firstOrNull { it.monumentLocation == event.block.location }
        if (targetDungeon != null) {
            event.isCancelled = true
            return
        }

        // check for dungeon break
        if (player.world != aetherWorld()) return
        if (player.location.y > -256) return
        event.isCancelled = true
    }

    @EventHandler
    fun onDungeonBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        if (player.world != aetherWorld()) return
        if (player.location.y > -256) return
        event.isCancelled = true
    }

    @EventHandler
    fun onDungeonExplode(event: EntityExplodeEvent) {
        if (event.location.world != aetherWorld()) return
        if (event.location.y > -256) return
        event.entity.remove()
        event.isCancelled = true
    }
}