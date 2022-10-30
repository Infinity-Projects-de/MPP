package de.danielmaile.mpp.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import de.danielmaile.mpp.block.BlockType
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.logError
import de.danielmaile.mpp.util.logInfo
import de.danielmaile.mpp.util.saveResource
import de.danielmaile.mpp.util.toMinecraftName
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile


class ResourcePackBuilder {

    private val resourcePackFolder = Paths.get(inst().dataFolder.absolutePath, "mpp_resourcepack")
    private val resourcePackZipPath = Paths.get(inst().dataFolder.absolutePath, "MPP Resource Pack.zip")

    init {
        logInfo("Generation resource pack...")
        copyAssets()
        generateBlockStatesJson()
        createZipFile()
        deleteWorkingDirectory()
        val hash = calculateSHA1Hash()
        logInfo("Saved resource pack to: " + resourcePackZipPath.toAbsolutePath())
        logInfo("Hash: $hash")
    }

    private fun copyAssets() {
        // Copy updated data pack from jar
        val jarFile = File(javaClass.protectionDomain.codeSource.location.path)
        if (jarFile.isFile) {
            val path = "mpp_resourcepack"
            val jar = JarFile(jarFile)
            val entries: Enumeration<JarEntry> = jar.entries()
            while (entries.hasMoreElements()) {
                val jarEntry = entries.nextElement()
                val name: String = jarEntry.name
                if (name.startsWith("$path/")) {
                    if (name.endsWith('/')) continue
                    saveResource(name, Paths.get(inst().dataFolder.absolutePath, name))
                }
            }
            jar.close()
        } else {
            logError("[THIS SHOULD NOT BE REACHED] Failed to generate resource pack.")
        }
    }

    private fun generateBlockStatesJson() {
        val jsonObject = JsonObject()

        val variants = JsonObject()
        for (blockType in BlockType.values()) {
            val model = JsonObject()
            model.addProperty("model", "minecraft:block/" + blockType.name.lowercase(Locale.getDefault()))
            variants.add(blockType.toBlockDataString(), model)
        }

        jsonObject.add("variants", variants)
        val gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()
        val outputFile = File(
            Paths.get(
                inst().dataFolder.absolutePath,
                "mpp_resourcepack",
                "assets",
                "minecraft",
                "blockstates",
                "note_block.json"
            ).toString()
        )
        outputFile.parentFile.mkdirs()
        val fileWriter = FileWriter(outputFile)
        gson.toJson(jsonObject, fileWriter)
        fileWriter.flush()
        fileWriter.close()
    }

    private fun createZipFile() {
        ZipArchiveOutputStream(FileOutputStream(resourcePackZipPath.toFile())).use { archive ->
            Files.walk(resourcePackFolder.toFile().toPath()).forEach { p ->
                val file = p.toFile()

                if(!file.isDirectory) {
                    val entry = ZipArchiveEntry(file, file.toString().substringAfter(resourcePackFolder.toString() + File.separator))
                    FileInputStream(file).use { fis ->
                        archive.putArchiveEntry(entry)
                        IOUtils.copy(fis, archive)
                        archive.closeArchiveEntry()
                    }
                }
            }
            archive.finish()
        }
    }

    private fun deleteWorkingDirectory() {
        FileUtils.deleteDirectory(resourcePackFolder.toFile())
    }

    private fun calculateSHA1Hash(): String {
        return DigestUtils.sha1Hex(FileInputStream(resourcePackZipPath.toFile()))
    }

    /*
    * Returns a string containing the block data of the given block type
    * Example: "instrument=harp,note=1,powered=false"
    */
    @Suppress("deprecation")
    private fun BlockType.toBlockDataString(): String {
        return "instrument=%s,note=%s,powered=%s".format(
            this.instrument.toMinecraftName(),
            this.note.id,
            this.isPowered.toString()
        )
    }
}