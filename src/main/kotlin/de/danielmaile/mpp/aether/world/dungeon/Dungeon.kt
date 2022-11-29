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

import de.danielmaile.mpp.util.converter.LocationConverter
import jakarta.persistence.*
import org.bukkit.Location
import java.util.*

@Entity
class Dungeon(
    @Column(nullable = false)
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val outerParts: List<OuterPart>,

    @Transient
    private val random: Random,

    // monumentLocation is only null during generation and not when saving
    @Column(nullable = false)
    @Convert(converter = LocationConverter::class)
    var monumentLocation: Location? = null,

    // monumentTargetLocation is only null during generation and not when saving
    @Column(nullable = false)
    @Convert(converter = LocationConverter::class)
    var monumentTargetLocation: Location? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null
) {

    init {
        findMonumentLoc()
    }

    private fun findMonumentLoc() {
        // try to find valid monument location around outer end parts
        val radius = 20
        for (outerEndPart in getOuterEndParts()) {
            for (x in outerEndPart.worldLocation.blockX - radius..outerEndPart.worldLocation.blockX + radius) {
                for (z in outerEndPart.worldLocation.blockZ - radius..outerEndPart.worldLocation.blockZ + radius) {
                    val y = outerEndPart.worldLocation.world.getHighestBlockYAt(outerEndPart.worldLocation) + 8

                    // monument has to be at or above y=0
                    if (y > 0) {
                        // try to find target location in inner end
                        val possibleTargets = getInnerEndParts()
                        if (possibleTargets.isNotEmpty()) {
                            monumentLocation = Location(
                                outerEndPart.worldLocation.world,
                                x.toDouble(), y.toDouble(), z.toDouble()
                            )
                            monumentTargetLocation =
                                possibleTargets[random.nextInt(possibleTargets.size)].worldLocation.clone()
                                    .add(-8.0, 1.0, 8.0)
                            return
                        }
                    }
                }
            }
        }
    }

    private fun getOuterEndParts(): List<OuterPart> {
        return outerParts.filter { END_PART_TYPES.contains(it.outerType.type) }
    }

    private fun getInnerEndParts(): List<InnerPart> {
        val possibleEndParts = ArrayList<InnerPart>()
        for (outerPart in outerParts) {
            outerPart.innerParts?.let { possibleEndParts.addAll(it) }
        }

        return possibleEndParts.filter { END_PART_TYPES.contains(it.innerType.type) }
    }

    fun getSize(): Int {
        var size = 0
        for (outerPart in outerParts) {
            if (outerPart.hasInnerParts()) {
                size += outerPart.innerParts!!.size
            } else {
                size++
            }
        }
        return size
    }
}