package de.danielmaile.aether.worldgen

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.math.transform.AffineTransform
import com.sk89q.worldedit.session.ClipboardHolder
import de.danielmaile.aether.inst
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
        Companion.instantiate(Collections.singletonList(this))
    }

    companion object {

        @JvmStatic
        fun instantiate(prefabs: MutableList<Prefab>) {
            Bukkit.getScheduler().runTaskAsynchronously(inst(), Runnable {
                for(prefab in prefabs) {
                    WorldEdit.getInstance().newEditSessionBuilder().world(BukkitAdapter.adapt(prefab.location.world)).build().use {
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