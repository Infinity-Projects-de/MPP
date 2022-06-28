package de.danielmaile.lama.aether.world

import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import de.danielmaile.lama.aether.util.getResource
import de.danielmaile.lama.aether.util.logError

enum class PrefabType(private val fileName: String?) {

    NONE(null),
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

    lateinit var clipboard: Clipboard
        private set

    companion object {

        @JvmStatic
        fun loadPrefabs() {
            for (type in values()) {
                if(type.fileName == null) continue
                try {
                    val inputStream = getResource("prefabs/" + type.fileName) ?: throw java.lang.NullPointerException()
                    val format = ClipboardFormats.findByAlias("schem") ?: throw java.lang.NullPointerException()
                    val reader = format.getReader(inputStream)
                    type.clipboard = reader.read()
                    inputStream.close()
                } catch (e: java.lang.Exception) {
                    logError("Failed to load prefab " + type.fileName)
                }
            }
        }
    }
}