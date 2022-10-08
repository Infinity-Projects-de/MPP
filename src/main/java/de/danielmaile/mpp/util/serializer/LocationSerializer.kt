package de.danielmaile.mpp.util.serializer

import com.google.gson.*
import de.danielmaile.mpp.util.logError
import org.bukkit.Bukkit
import org.bukkit.Location
import java.lang.reflect.Type
import java.util.*

class LocationSerializer : JsonSerializer<Location>, JsonDeserializer<Location> {

    override fun serialize(
        location: Location,
        type: Type,
        jsonSerializationContext: JsonSerializationContext
    ): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("world_uuid", location.world.uid.toString())
        jsonObject.addProperty("x", location.x)
        jsonObject.addProperty("y", location.y)
        jsonObject.addProperty("z", location.z)
        return jsonObject
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        jsonElement: JsonElement,
        type: Type,
        jsonDeserializationContext: JsonDeserializationContext
    ): Location {
        val jsonObject = jsonElement.asJsonObject
        val uuid = UUID.fromString(jsonObject["world_uuid"].asString)
        val world = Bukkit.getWorld(uuid)
        if (world == null) {
            logError(
                "Error while reading location from file: No world found with UUID " + uuid + "!" +
                        " Please delete the aether data folder and the aether world and restart the server to avoid errors"
            )
        }

        val x = jsonObject["x"].asDouble
        val y = jsonObject["y"].asDouble
        val z = jsonObject["z"].asDouble
        return Location(world, x, y, z)
    }
}