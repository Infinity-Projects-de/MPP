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

package de.danielmaile.mpp.mob

import org.bukkit.Location
import kotlin.random.Random

class MPPMobPack(
    private val size: Int,
    private val baseLevel: Long,
    private val levelVariance: Double
) {

    fun summon(location: Location) {
        for (i in 1..size) {
            getRandomMob(null).summon(location, getRandomMobLevel())
        }
    }

    /**
     * @return a random mob level based on baseLevel and levelVariance.
     * Returned value is always >= 1.
     */
    private fun getRandomMobLevel(): Long {
        val variance = (baseLevel * levelVariance).toLong()
        val minLevel = (baseLevel - variance).coerceAtLeast(1)
        val maxLevel = baseLevel + variance

        return Random.nextLong(minLevel, maxLevel + 1)
    }
}
