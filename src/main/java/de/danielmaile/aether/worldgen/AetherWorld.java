package de.danielmaile.aether.worldgen;

import de.danielmaile.aether.Aether;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class AetherWorld
{
    private static final String worldName = "world_aether_aether";

    @Nullable
    public static World getWorld()
    {
        return Bukkit.getWorld(worldName);
    }

    public static void init()
    {
        try
        {
            copyDatapack();
        }
        catch (IOException | URISyntaxException exception)
        {
            Aether.logError("Fehler beim kopieren des Datapacks. Starte deinen Server neu und versuche es erneut!");
        }

    }

    private static void copyDatapack() throws IOException, URISyntaxException
    {
        //Check if Datapack is already there
        String datapackPath = Bukkit.getWorldContainer() + File.separator + "world" + File.separator + "datapacks" + File.separator + "aether_datapack" + File.separator;
        if (new File(datapackPath).exists())
        {
            return;
        }

        //Copy Datapack
        //TODO auto copy all files in resources directory
        saveResourceToWorldFolder("aether_datapack/data/aether/dimension/aether.json");
        saveResourceToWorldFolder("aether_datapack/data/aether/worldgen/biome/plains.json");
        saveResourceToWorldFolder("aether_datapack/pack.mcmeta");

        Bukkit.getConsoleSender().sendMessage(Aether.PREFIX + ChatColor.RED + "Alle Dateien wurden erfolgreich kopiert. Starte deinen Server neu um die Aether-Welt zu generieren.");
    }

    private static void saveResourceToWorldFolder(String resourcePath) throws IOException
    {
        String outputPath = Bukkit.getWorldContainer() + File.separator + "world" + File.separator + "datapacks" + File.separator + resourcePath;
        InputStream inputStream = getResource(resourcePath);
        if (inputStream == null)
        {
            throw new IllegalArgumentException();
        }

        //Create output directory
        File outputDirectory = new File(outputPath.substring(0, outputPath.lastIndexOf('/')));
        if (!outputDirectory.exists())
        {
            outputDirectory.mkdirs();
        }

        //Write file
        File outFile = new File(outputPath);
        OutputStream outputStream = new FileOutputStream(outFile);
        byte[] buf = new byte[1024];
        int length;
        while ((length = inputStream.read(buf)) > 0)
        {
            outputStream.write(buf, 0, length);
        }
        outputStream.close();
        inputStream.close();
    }

    private static InputStream getResource(String filename) throws IOException
    {
        URL url = AetherWorld.class.getClassLoader().getResource(filename);

        if (url == null)
        {
            return null;
        }

        URLConnection connection = url.openConnection();
        connection.setUseCaches(false);
        return connection.getInputStream();
    }
}
