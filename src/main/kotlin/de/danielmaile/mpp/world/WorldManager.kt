/*
 * This file is part of MPP.
 * Copyright (c) 2022-2023 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.world

import de.danielmaile.mpp.MPP
import de.danielmaile.mpp.util.logError
import org.bukkit.Bukkit
import org.bukkit.World

class WorldManager(mpp: MPP) {

    val aetherWorld: World

    init {
        // try to get aether world
        val aetherWorld = Bukkit.getWorld("world_aether_aether")

        // if aether world is null send message and disable plugin
        if (aetherWorld == null) {
            mpp.getLanguageManager().getString("messages.errors.aether_world_not_generated")?.let { logError(it) }
            this.aetherWorld = Bukkit.getWorlds()[0]
            Bukkit.getPluginManager().disablePlugin(mpp)
        } else {
            this.aetherWorld = aetherWorld
        }
    }
}