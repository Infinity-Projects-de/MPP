package de.danielmaile.lama.aether.world

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import de.danielmaile.lama.aether.inst
import de.danielmaile.lama.aether.util.serializer.LocationSerializer
import de.danielmaile.lama.aether.util.serializer.VectorSerializer
import de.danielmaile.lama.aether.world.dungeon.Dungeon
import org.bukkit.Location
import org.bukkit.util.Vector
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.util.*
import kotlin.collections.ArrayList


class ObjectManager {

    var dungeons = ArrayList<Dungeon>()

    private val dungeonSavePath = (inst().dataFolder.absolutePath
            + File.separator + "data" + File.separator + "dungeons.aether")
    private var gson: Gson = GsonBuilder()
        .registerTypeAdapter(Location::class.java, LocationSerializer())
        .registerTypeAdapter(Vector::class.java, VectorSerializer())
        .setPrettyPrinting()
        .create()

    init {
        loadDungeons()
    }

    fun save() {
        saveDungeons()
    }

    private fun saveDungeons() {
        if (dungeons.isEmpty()) return

        FileWriter(dungeonSavePath, false).use {
            gson.toJson(dungeons, it)
            it.flush()
        }
    }

    private fun loadDungeons() {
        try {
            val reader = JsonReader(FileReader(dungeonSavePath))
            dungeons = gson.fromJson<Array<Dungeon>>(reader, Array<Dungeon>::class.java).toCollection(ArrayList())
        } catch (ignored: FileNotFoundException) {
        }
    }
}