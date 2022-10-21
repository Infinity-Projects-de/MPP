package de.danielmaile.mpp.aether.world

import de.danielmaile.mpp.MPP
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.logError
import org.bukkit.Bukkit
import org.bukkit.World
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile

class WorldManager(mpp: MPP) {

    val world: World
    val objectManager: ObjectManager

    init {
        //Copy datapack
        saveOrUpdateDataPack()

        //Try to get aether world
        val aetherWorld = Bukkit.getWorld("world_aether_aether")

        //If aether world is null send message and disable plugin
        if (aetherWorld == null) {
            mpp.getLanguageManager().getString("messages.errors.aether_world_not_generated")?.let { logError(it) }
            world = Bukkit.getWorlds()[0]
            Bukkit.getPluginManager().disablePlugin(mpp)
        } else {
            world = aetherWorld
        }

        objectManager = ObjectManager()
    }

    private fun saveOrUpdateDataPack() {
        // Determine if newer version is available from jar
        val versionJar = getVersion(inst().javaClass.classLoader.getResourceAsStream("mpp_datapack/version.txt")!!)
        if (versionJar == -1) {
            logError("Could not detect data pack version from jar! Aborted data pack update.")
            return
        }
        val dataPackPath = Bukkit.getWorldContainer().toString() + File.separator + Bukkit.getWorlds()[0].name + File.separator + "datapacks" + File.separator + "mpp_datapack"
        val versionFilePath = dataPackPath + File.separator + "version.txt"
        if (File(versionFilePath).exists() && versionJar <= getVersion(FileInputStream(versionFilePath)))
            return

        // Delete old data pack files
        Files.walk(Paths.get(dataPackPath)).use { dirStream ->
            dirStream
                .map(Path::toFile)
                .sorted(Comparator.reverseOrder())
                .forEach(File::delete)
        }

        // Copy updated data pack from jar
        val jarFile = File(javaClass.protectionDomain.codeSource.location.path)
        if (jarFile.isFile) {
            val path = "mpp_datapack"
            val jar = JarFile(jarFile)
            val entries: Enumeration<JarEntry> = jar.entries()
            while (entries.hasMoreElements()) {
                val jarEntry = entries.nextElement()
                val name: String = jarEntry.name
                if (name.startsWith("$path/")) {
                    if (name.endsWith('/')) continue
                    saveResourceToWorldFolder(name)
                }
            }
            jar.close()
        } else {
            logError("[THIS SHOULD NOT BE REACHED] Failed to copy data pack.")
        }
    }

    private fun getVersion(versionFileStream: InputStream): Int {
        val versionFileContents = String(versionFileStream.readAllBytes())
        var version = -1
        for (line in versionFileContents.split('\n'))
            if (line.startsWith("version: "))
                version = Integer.parseInt(line.replace("version: ", "").trim())
        versionFileStream.close()
        return version
    }

    @Throws(IOException::class)
    private fun saveResourceToWorldFolder(resourcePath: String) {
        val outputPath = Bukkit.getWorldContainer()
            .toString() + File.separator + Bukkit.getWorlds()[0].name + File.separator + "datapacks" + File.separator + resourcePath
        val inputStream =
            inst().javaClass.classLoader.getResourceAsStream(resourcePath) ?: throw IllegalArgumentException()

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