package de.danielmaile.aether.world.dungeon

import org.bukkit.Location
import org.bukkit.util.Vector

class InnerPart(val innerType: InnerPartType, val relativePosition: Vector, outerPart: OuterPart) {

    val worldLocation: Location =
        outerPart.worldLocation.clone().add(relativePosition.x * 16, 0.0, relativePosition.z * 16)
}