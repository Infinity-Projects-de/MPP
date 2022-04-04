package de.danielmaile.aether.util.serializer;

import com.google.gson.*;
import de.danielmaile.aether.Aether;
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
        UUID uuid = UUID.fromString(jsonObject.get("world_uuid").getAsString());
        World world = Bukkit.getWorld(uuid);
        if(world == null)
        {
            Aether.logError("Error while reading location from file: No world found with UUID " + uuid + "!" +
                    " Please delete the aether data folder and the aether world and restart the server to avoid errors");
        }
        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        double z = jsonObject.get("z").getAsDouble();
        return new Location(world, x, y, z);
    }
}
