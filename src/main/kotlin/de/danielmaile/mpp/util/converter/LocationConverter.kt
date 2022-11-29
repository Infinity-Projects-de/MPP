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

package de.danielmaile.mpp.util.converter

import de.danielmaile.mpp.util.logError
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.*

@Converter
class LocationConverter : AttributeConverter <Location, String> {

    private val separator = ";"

    /**
     * Convert Location object to a String
     * with format world_uuid;x;y;z
     */
    override fun convertToDatabaseColumn(location: Location): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(location.world.uid.toString())
        stringBuilder.append(separator)
        stringBuilder.append(location.x)
        stringBuilder.append(separator)
        stringBuilder.append(location.y)
        stringBuilder.append(separator)
        stringBuilder.append(location.z)
        return stringBuilder.toString()
    }

    /**
     * Convert a String with format world_uuid;x;y;z
     * to a Location object
     */
    override fun convertToEntityAttribute(locationString: String): Location {
        val values = locationString.split(separator)

        val uuid = UUID.fromString(values[0])
        val world = Bukkit.getWorld(uuid)
        if (world == null) {
            logError(
                "Error while reading location from database: No world found with UUID " + uuid + "!" +
                        " Please delete the database to avoid errors!"
            )
        }

        return Location(
            world,
            values[1].toDouble(),
            values[2].toDouble(),
            values[3].toDouble()
        )
    }
}