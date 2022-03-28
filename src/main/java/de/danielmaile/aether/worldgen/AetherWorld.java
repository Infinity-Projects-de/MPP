package de.danielmaile.aether.worldgen;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URISyntaxException;

public class AetherWorld
{
    private static final String worldName = "world_aether_aether";

    public static ObjectManager getObjectManager()
    {
        return objectManager;
    }

    private static ObjectManager objectManager;

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
            Aether.logError("Error while copying datapack. Please restart your server and try again!");
        }

        objectManager = new ObjectManager();
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
        saveResourceToWorldFolder("aether_datapack/data/aether/dimension/aether.json");
        saveResourceToWorldFolder("aether_datapack/data/aether/worldgen/biome/plains.json");
        saveResourceToWorldFolder("aether_datapack/pack.mcmeta");

        Bukkit.getConsoleSender().sendMessage(
                Aether.getLanguageManager().getComponent("messages.prefix")
                        .append(Aether.getLanguageManager().getComponent("messages.console.datapack_copied")));
    }

    private static void saveResourceToWorldFolder(String resourcePath) throws IOException
    {
        String outputPath = Bukkit.getWorldContainer() + File.separator + "world" + File.separator + "datapacks" + File.separator + resourcePath;
        InputStream inputStream = Utils.getResource(resourcePath);
        if (inputStream == null)
        {
            throw new IllegalArgumentException();
        }

        //Create output directory
        File outputDirectory = new File(outputPath.substring(0, outputPath.lastIndexOf('/')));
        if (!outputDirectory.exists())
        {
            if (!outputDirectory.mkdirs())
            {
                Aether.logError("Output directories for resource can't be created!");
                return;
            }
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
}
