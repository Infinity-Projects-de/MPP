package de.danielmaile.aether.worldgen;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import de.danielmaile.aether.Aether;
import de.danielmaile.aether.util.Utils;

import java.io.IOException;
import java.io.InputStream;

public enum PrefabType
{
    TREE1("tree_var1.schem"),
    TREE2("tree_var2.schem"),
    TREE3("tree_var3.schem"),
    PORTAL("portal.schem"),
    DUNGEON_MONUMENT("dungeon/parts/monument.schem"),
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

    public Clipboard getClipboard()
    {
        return clipboard;
    }

    private Clipboard clipboard = null;

    PrefabType(String fileName)
    {
        this.fileName = fileName;
    }

    public static void loadPrefabs()
    {
        for (PrefabType prefabType : PrefabType.values())
        {
            try
            {
                InputStream inputStream = Utils.getResource("prefabs/" + prefabType.fileName);
                ClipboardFormat format = ClipboardFormats.findByAlias("schem");
                if (format == null) throw new NullPointerException();
                ClipboardReader reader = format.getReader(inputStream);
                prefabType.clipboard = reader.read();
                inputStream.close();
            }
            catch (NullPointerException | ArrayIndexOutOfBoundsException | IOException e)
            {
                Aether.logError("Failed to load prefab " + prefabType.fileName);
            }
        }
    }
}
