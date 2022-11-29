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

package de.danielmaile.mpp.aether.mob

import de.danielmaile.mpp.aetherWorld
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.EntitiesLoadEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class ListenerAetherMobs : Listener {

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
}