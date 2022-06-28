package de.danielmaile.lama.aether.world

import de.danielmaile.lama.aether.Aether.Companion.instance
import de.danielmaile.lama.aether.util.getResource
import de.danielmaile.lama.aether.util.logError
import org.bukkit.Bukkit
import org.bukkit.World
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.URISyntaxException

private const val worldName = "world_aether_aether"

class AetherWorld {

    val objectManager: ObjectManager = ObjectManager()

    init {
        try {
            copyDatapack()
        } catch (e: java.lang.Exception) {
            logError("Error while copying datapack. Please restart your server and try again!")
        }

        getWorld()?.populators?.add(BedrockPopulator())
    }

    fun getWorld(): World? {
        return Bukkit.getWorld(worldName)
    }

    @Throws(IOException::class, URISyntaxException::class)
    private fun copyDatapack() {
        //Check if Datapack is already there
        val datapackPath = Bukkit.getWorldContainer()
            .toString() + File.separator + "world" + File.separator + "datapacks" + File.separator + "aether_datapack" + File.separator
        if (File(datapackPath).exists()) {
            return
        }

        //Copy Datapack
        saveResourceToWorldFolder("aether_datapack/data/aether/dimension/aether.json")
        saveResourceToWorldFolder("aether_datapack/data/aether/worldgen/biome/plains.json")
        saveResourceToWorldFolder("aether_datapack/pack.mcmeta")

        Bukkit.getConsoleSender().sendMessage(
            instance.getLanguageManager().getComponent("messages.prefix")
                .append(instance.getLanguageManager().getComponent("messages.console.datapack_copied"))
        )
    }

    @Throws(IOException::class)
    private fun saveResourceToWorldFolder(resourcePath: String) {
        val outputPath = Bukkit.getWorldContainer()
            .toString() + File.separator + "world" + File.separator + "datapacks" + File.separator + resourcePath
        val inputStream = getResource(resourcePath) ?: throw IllegalArgumentException()

        //Create output directory
        val outputDirectory = File(outputPath.substring(0, outputPath.lastIndexOf('/')))
        if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
            logError("Output directories for resource can't be created!")
            return
        }

        //Write file
        val outFile = File(outputPath)
        val outputStream: OutputStream = FileOutputStream(outFile)
        val buf = ByteArray(1024)
        var length: Int
        while (inputStream.read(buf).also { length = it } > 0) {
            outputStream.write(buf, 0, length)
        }
        outputStream.close()
        inputStream.close()
    }
}