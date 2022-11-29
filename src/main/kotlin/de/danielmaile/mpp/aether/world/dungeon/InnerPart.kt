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
import de.danielmaile.mpp.util.converter.VectorConverter
import jakarta.persistence.*
import org.bukkit.Location
import org.bukkit.util.Vector

@Entity
class InnerPart(
    @Enumerated(EnumType.STRING)
    val innerType: InnerPartType,

    @Column(nullable = false)
    @Convert(converter = VectorConverter::class)
    val relativePosition: Vector,

    @Transient
    private val outerPart: OuterPart,

    @Column(nullable = false)
    @Convert(converter = LocationConverter::class)
    val worldLocation: Location = outerPart.worldLocation.clone()
        .add(relativePosition.x * 16, 0.0, relativePosition.z * 16),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null
)