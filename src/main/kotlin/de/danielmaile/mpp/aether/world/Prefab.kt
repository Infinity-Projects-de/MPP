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

package de.danielmaile.mpp.aether.world

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.math.transform.AffineTransform
import com.sk89q.worldedit.session.ClipboardHolder
import de.danielmaile.mpp.inst
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.*

class Prefab(val type: PrefabType, val location: Location, val yRotation: Double, val ignoreAirBlocks: Boolean) {
    constructor(type: PrefabType, location: Location, ignoreAirBlocks: Boolean) : this(
        type,
        location,
        0.0,
        ignoreAirBlocks
    )

    fun instantiate() {
        instantiate(Collections.singletonList(this))
    }

    companion object {

        @JvmStatic
        fun instantiate(prefabs: MutableList<Prefab>) {
            Bukkit.getScheduler().runTaskAsynchronously(inst(), Runnable {
                for (prefab in prefabs) {
                    WorldEdit.getInstance().newEditSessionBuilder().world(BukkitAdapter.adapt(prefab.location.world))
                        .build().use {
                        val clipboardHolder = ClipboardHolder(prefab.type.clipboard)
                        clipboardHolder.transform = AffineTransform().rotateY(prefab.yRotation)
                        val operation = clipboardHolder
                            .createPaste(it)
                            .to(BlockVector3.at(prefab.location.x, prefab.location.y, prefab.location.z))
                            .copyEntities(false)
                            .ignoreAirBlocks(prefab.ignoreAirBlocks)
                            .build()
                        Operations.complete(operation)
                    }
                }
            })
        }
    }
}