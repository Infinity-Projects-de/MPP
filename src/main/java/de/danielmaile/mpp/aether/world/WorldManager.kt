package de.danielmaile.mpp.aether.world

import de.danielmaile.mpp.MPP
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.logError
import org.bukkit.Bukkit
import org.bukkit.World
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class WorldManager(mpp: MPP) {

    val world: World
    val objectManager: ObjectManager

    init {
        //Copy datapack
        saveResourceToWorldFolder("mpp_datapack/data/aether/dimension/aether.json")
        saveResourceToWorldFolder("mpp_datapack/data/aether/dimension_type/aether.json")
        saveResourceToWorldFolder("mpp_datapack/data/aether/worldgen/biome/plains.json")
        saveResourceToWorldFolder("mpp_datapack/data/minecraft/worldgen/density_function/overworld/base_3d_noise.json")
        saveResourceToWorldFolder("mpp_datapack/data/minecraft/worldgen/density_function/overworld/depth.json")
        saveResourceToWorldFolder("mpp_datapack/data/minecraft/worldgen/density_function/overworld/factor.json")
        saveResourceToWorldFolder("mpp_datapack/data/minecraft/worldgen/density_function/overworld/sloped_cheese.json")
        saveResourceToWorldFolder("mpp_datapack/pack.mcmeta")

        //Try to get aether world
        val aetherWorld = Bukkit.getWorld("world_aether_aether")

        //If aether world is null send message and disable plugin
        if(aetherWorld == null) {
            mpp.getLanguageManager().getString("messages.errors.aether_world_not_generated")?.let { logError(it) }
            world = Bukkit.getWorlds()[0]
            Bukkit.getPluginManager().disablePlugin(mpp)
        } else {
            world = aetherWorld
        }

        objectManager = ObjectManager()
    }

    @Throws(IOException::class)
    private fun saveResourceToWorldFolder(resourcePath: String) {
        val outputPath = Bukkit.getWorldContainer()
            .toString() + File.separator + "world" + File.separator + "datapacks" + File.separator + resourcePath
        val inputStream = inst().javaClass.classLoader.getResourceAsStream(resourcePath) ?: throw IllegalArgumentException()

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