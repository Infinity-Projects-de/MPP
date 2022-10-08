package de.danielmaile.mpp.util.serializer

import com.google.gson.*
import kotlin.Throws
import org.bukkit.util.Vector
import java.lang.reflect.Type

class VectorSerializer : JsonSerializer<Vector>, JsonDeserializer<Vector> {

    override fun serialize(
        vector: Vector,
        type: Type,
        jsonSerializationContext: JsonSerializationContext
    ): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("x", vector.x)
        jsonObject.addProperty("y", vector.y)
        jsonObject.addProperty("z", vector.z)
        return jsonObject
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        jsonElement: JsonElement,
        type: Type,
        jsonDeserializationContext: JsonDeserializationContext
    ): Vector {
        val jsonObject = jsonElement.asJsonObject
        val x = jsonObject["x"].asDouble
        val y = jsonObject["y"].asDouble
        val z = jsonObject["z"].asDouble
        return Vector(x, y, z)
    }
}