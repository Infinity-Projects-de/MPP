package de.danielmaile.aether.util.serializer;

import com.google.gson.*;
import org.bukkit.util.Vector;

import java.lang.reflect.Type;

public class VectorSerializer implements JsonSerializer<Vector>, JsonDeserializer<Vector>
{
    @Override
    public JsonElement serialize(Vector vector, Type type, JsonSerializationContext jsonSerializationContext)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", vector.getX());
        jsonObject.addProperty("y", vector.getY());
        jsonObject.addProperty("z", vector.getZ());
        return jsonObject;
    }

    @Override
    public Vector deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        double z = jsonObject.get("z").getAsDouble();
        return new Vector(x, y, z);
    }
}
