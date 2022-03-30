package de.danielmaile.aether.worldgen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import de.danielmaile.aether.Aether;
import de.danielmaile.aether.util.serializer.LocationSerializer;
import de.danielmaile.aether.util.serializer.VectorSerializer;
import de.danielmaile.aether.worldgen.dungeon.Dungeon;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectManager
{
    private static final String dungeonSavePath = Aether.getInstance().getDataFolder().getAbsolutePath()
            + File.separator + "data" + File.separator + "dungeons.aether";

    private List<Dungeon> dungeons = new ArrayList<>();

    public List<Dungeon> getDungeonList()
    {
        return dungeons;
    }

    private final Gson gson;

    public ObjectManager()
    {
        gson = new GsonBuilder()
                .registerTypeAdapter(Location.class, new LocationSerializer())
                .registerTypeAdapter(Vector.class, new VectorSerializer())
                .setPrettyPrinting()
                .create();
        loadDungeons();
    }

    public void save()
    {
        saveDungeons();
    }

    private void saveDungeons()
    {
        try (FileWriter fileWriter = new FileWriter(dungeonSavePath, false))
        {
            gson.toJson(dungeons, fileWriter);
            fileWriter.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadDungeons()
    {
        try
        {
            JsonReader reader = new JsonReader(new FileReader(dungeonSavePath));
            dungeons = new ArrayList<>(Arrays.asList(gson.fromJson(reader, Dungeon[].class)));
        }
        catch (FileNotFoundException ignored) { }

        if (dungeons == null)
        {
            dungeons = new ArrayList<>();
        }
    }
}
