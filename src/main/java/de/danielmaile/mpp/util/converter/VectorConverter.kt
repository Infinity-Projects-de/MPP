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