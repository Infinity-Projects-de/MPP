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

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.bukkit.util.Vector

@Converter
class VectorConverter : AttributeConverter<Vector, String> {

    private val separator = ";"

    /**
     * Convert Vector object to a String
     * with format x;y;z
     */
    override fun convertToDatabaseColumn(vector: Vector): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(vector.x)
        stringBuilder.append(separator)
        stringBuilder.append(vector.y)
        stringBuilder.append(separator)
        stringBuilder.append(vector.z)
        return stringBuilder.toString()
    }

    /**
     * Convert a String with format x;y;z
     * to a Vector object
     */
    override fun convertToEntityAttribute(vectorString: String): Vector {
        val values = vectorString.split(separator)
        return Vector(
            values[0].toDouble(),
            values[1].toDouble(),
            values[2].toDouble()
        )
    }
}