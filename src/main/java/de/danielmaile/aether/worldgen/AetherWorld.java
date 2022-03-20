package de.danielmaile.aether.worldgen;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import de.danielmaile.aether.Aether;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URISyntaxException;

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

        //Add populators to Aether world
        if (getWorld() != null)
        {
            getWorld().getPopulators().add(new TreePopulator());
            getWorld().getPopulators().add(new DungeonPopulator());
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
            if (!outputDirectory.mkdirs())
            {
                Aether.logError("Output directories can't be created!");
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

    private static InputStream getResource(String filename) throws IOException
    {
        return AetherWorld.class.getClassLoader().getResourceAsStream(filename);
    }

    public static Clipboard loadPrefabToClipboard(String prefabName)
    {
        Clipboard clipboard = null;
        try
        {
            InputStream inputStream = getResource("prefabs/" + prefabName + ".schem");
            ClipboardFormat format = ClipboardFormats.findByAlias("schem");
            if (format == null) throw new NullPointerException();
            ClipboardReader reader = format.getReader(inputStream);
            clipboard = reader.read();
            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return clipboard;
    }

    public static void instantiatePrefab(Location location, String prefabName)
    {
        Clipboard clipboard = loadPrefabToClipboard(prefabName);
        instantiatePrefab(location, clipboard);
    }

    public static void instantiatePrefab(Location location, Clipboard clipboard)
    {
        com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(location.getWorld());
        EditSession editSession = WorldEdit.getInstance().newEditSession(adaptedWorld);
        Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                .to(BlockVector3.at(location.getX(), location.getY(), location.getZ())).ignoreAirBlocks(true).build();

        try
        {
            Operations.complete(operation);
            editSession.close();
        }
        catch (WorldEditException exception)
        {
            exception.printStackTrace();
        }
    }
}
