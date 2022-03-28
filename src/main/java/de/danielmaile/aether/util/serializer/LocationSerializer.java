package de.danielmaile.aether.util.serializer;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Type;
import java.util.UUID;

public class LocationSerializer implements JsonSerializer<Location>, JsonDeserializer<Location>
{
    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("world_uuid", location.getWorld().getUID().toString());
        jsonObject.addProperty("x", location.getX());
        jsonObject.addProperty("y", location.getY());
        jsonObject.addProperty("z", location.getZ());
        return jsonObject;
    }

    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        World world = Bukkit.getWorld(UUID.fromString(jsonObject.get("world_uuid").getAsString()));
        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        double z = jsonObject.get("z").getAsDouble();
        return new Location(world, x, y, z);
    }
}
