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