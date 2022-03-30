package de.danielmaile.aether.worldgen;

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
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import de.danielmaile.aether.Aether;
import de.danielmaile.aether.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.primesoft.asyncworldedit.api.IAsyncWorldEdit;
import org.primesoft.asyncworldedit.api.blockPlacer.IBlockPlacer;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerEntry;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerManager;
import org.primesoft.asyncworldedit.api.worldedit.IAsyncEditSessionFactory;
import org.primesoft.asyncworldedit.api.worldedit.IThreadSafeEditSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public enum Prefab
{
    TREE1("tree_var1.schem"),
    TREE2("tree_var2.schem"),
    TREE3("tree_var3.schem"),
    PORTAL("portal.schem"),
    DUNGEON_MONUMENT("dungeon/monument.schem"),
    DUNGEON_PART_INNER_S("dungeon/parts/inner/S_var1.schem"),
    DUNGEON_PART_INNER_N("dungeon/parts/inner/N_var1.schem"),
    DUNGEON_PART_INNER_W("dungeon/parts/inner/W_var1.schem"),
    DUNGEON_PART_INNER_E("dungeon/parts/inner/E_var1.schem"),
    DUNGEON_PART_INNER_SN("dungeon/parts/inner/SN_var1.schem"),
    DUNGEON_PART_INNER_WS("dungeon/parts/inner/WS_var1.schem"),
    DUNGEON_PART_INNER_ES("dungeon/parts/inner/ES_var1.schem"),
    DUNGEON_PART_INNER_WN("dungeon/parts/inner/WN_var1.schem"),
    DUNGEON_PART_INNER_EN("dungeon/parts/inner/EN_var1.schem"),
    DUNGEON_PART_INNER_EW("dungeon/parts/inner/EW_var1.schem"),
    DUNGEON_PART_INNER_EWS("dungeon/parts/inner/EWS_var1.schem"),
    DUNGEON_PART_INNER_WSN("dungeon/parts/inner/WSN_var1.schem"),
    DUNGEON_PART_INNER_ESN("dungeon/parts/inner/ESN_var1.schem"),
    DUNGEON_PART_INNER_EWN("dungeon/parts/inner/EWN_var1.schem"),
    DUNGEON_PART_INNER_EWSN("dungeon/parts/inner/EWSN_var1.schem");

    private final String fileName;
    private Clipboard clipboard = null;

    Prefab(String fileName)
    {
        this.fileName = fileName;
    }

    public void instantiate(Location location, boolean ignoreAirBlocks)
    {
        instantiate(location, 0, ignoreAirBlocks);
    }

    public void instantiate(Location location, double yRotation, boolean ignoreAirBlocks)
    {
        World adaptedWorld = BukkitAdapter.adapt(location.getWorld());
        IThreadSafeEditSession editSession = sessionFactory.getThreadSafeEditSession(adaptedWorld, Integer.MAX_VALUE);
        blockPlacer.performAsAsyncJob(editSession, awePlayer, "aether place prefab", iCancelabeEditSession ->
        {
            ClipboardHolder clipboardHolder = new ClipboardHolder(clipboard);
            clipboardHolder.setTransform(new AffineTransform().rotateY(yRotation));
            Operation operation = clipboardHolder.createPaste(editSession)
                    .to(BlockVector3.at(location.getX(), location.getY(), location.getZ())).ignoreAirBlocks(ignoreAirBlocks).build();
            try
            {
                Operations.complete(operation);
            }
            catch (WorldEditException e)
            {
                e.printStackTrace();
            }
            editSession.close();
            return 0;
        });
    }

    private static IAsyncEditSessionFactory sessionFactory;
    private static IBlockPlacer blockPlacer;
    private static IPlayerEntry awePlayer;

    public static void init()
    {
        IAsyncWorldEdit aweAPI = (IAsyncWorldEdit) Bukkit.getServer().getPluginManager().getPlugin("AsyncWorldEdit");
        if (aweAPI == null)
        {
            Aether.logError("AsyncWorldEdit plugin was not found. Please check your plugin folder!");
            return;
        }

        sessionFactory = (IAsyncEditSessionFactory) WorldEdit.getInstance().getEditSessionFactory();
        blockPlacer = aweAPI.getBlockPlacer();
        IPlayerManager playerManager = aweAPI.getPlayerManager();
        awePlayer = playerManager.createFakePlayer("aether", UUID.randomUUID());
        loadPrefabs();
    }

    private static void loadPrefabs()
    {
        for (Prefab prefab : Prefab.values())
        {
            try
            {
                InputStream inputStream = Utils.getResource("prefabs/" + prefab.fileName);
                ClipboardFormat format = ClipboardFormats.findByAlias("schem");
                if (format == null) throw new NullPointerException();
                ClipboardReader reader = format.getReader(inputStream);
                prefab.clipboard = reader.read();
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (NullPointerException e)
            {
                Aether.logError("Failed to load prefab " + prefab.fileName);
            }
        }
    }
}