/*
 * This file is part of MPP.
 * Copyright (c) 2022 by it's authors. All rights reserved.
 * MPP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MPP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MPP.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.danielmaile.mpp.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import de.danielmaile.mpp.block.BlockType
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.item.ArmorSet
import de.danielmaile.mpp.item.ItemType
import de.danielmaile.mpp.util.*
import kong.unirest.Unirest
import org.apache.commons.lang3.RandomStringUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.awt.image.BufferedImage
import java.io.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.imageio.ImageIO
import kotlin.io.path.isDirectory
import kotlin.io.path.name


object ResourcePackBuilder {

    private val packName = "${RandomStringUtils.randomAlphanumeric(32)}.zip"

    @Deprecated("Saving won't be necessary")
    private val resourcePackWorkingDirectory = Paths.get(inst().dataFolder.absolutePath, "mpp_resourcepack")

    private val resourcePackZipPath = Paths.get(inst().dataFolder.absolutePath, packName)

    private val url =
        "http://${inst().configManager.resourcePackHostIP}:${inst().configManager.resourcePackHostPort}/files/$packName"

    @Deprecated("Deprecated in favor of non file-saving methods")
    fun generateResourcePackOld() {
        logInfo("Generating resource pack...")
        generateBlockStatesJson()
        generateItemModels()
        generateArmorLayers()
        createZipFile(resourcePackZipPath.toFile(), resourcePackWorkingDirectory, { it.parentFile.name != "armor_assets" },true)
        logInfo("Successfully generated resource pack! Uploading...")

        // upload pack to hosting server
        uploadPack()

        // calculate hash
        val hash = calculateSHA1Hash(resourcePackZipPath.toFile())

        // register pack listener
        inst().server.pluginManager.registerEvents(ResourcePackListener(url, hash), inst())
    }

    fun generateResourcePack() {
        val resourcePack = getAssetsFromJar(Path.of("mpp_resourcepack","assets"))

        val layer1 = getArmorAssets(true)
        val layer2 = getArmorAssets(false)

        for (url in resourcePack) {
            if(isDirectory(url)) {
                continue
            }
            url.openStream().use {

            }
        }

    }

    private fun getArmorAssets(layer1: Boolean): InputStream {
        val armorAssets = getAssetsFromJar(Path.of("mpp_resourcepack", "parseable","armor"))
        val armorCount = armorAssets.asSequence().count() / 2
        val layer = ArmorManager(armorCount)

        for (url in armorAssets) {
            if(isDirectory(url)) { // I'm quite sure I don't need this foolproof check
                continue
            }
            url.openStream().use {
                if (layer1 && url.file.endsWith("1.png") || !layer1 && url.file.endsWith("2.png")) {
                    layer.drawTexture(url, it)
                }
            }
        }
        return layer.write()
    }

    private fun isDirectory(url: URL): Boolean {
        if(File(url.toURI()).isDirectory) {
            return true
        }
        return false
    }

    private class ArmorManager(private val armorAmount: Int) {
        private val buff: BufferedImage = BufferedImage(armorAmount * 64, 32, BufferedImage.TYPE_INT_ARGB)
        private val graphics = buff.graphics

        private var latest = 1

        fun drawTexture(url: URL, inputStream: InputStream) {
            val name = url.file.split("_")[0].uppercase()
            if(name == "VANILLA") {
                drawDefaultTexture(inputStream)
            } else {
                drawNormalTexture(inputStream, ArmorSet.valueOf(name))
            }
        }
        private fun drawDefaultTexture(texture: InputStream) {
            val image = ImageIO.read(texture)
            graphics.drawImage(image,0,0, null)
        }

        private fun drawNormalTexture(texture: InputStream, armorSet: ArmorSet) {
            if(latest >= armorAmount) {
                throw ArrayIndexOutOfBoundsException()
            }

            val image = ImageIO.read(texture)
            val color = armorSet.color.asRGB()

            graphics.drawImage(image, latest * 64, 0, null)
            graphics.color = java.awt.Color(color)
            graphics.drawLine(latest*64,0,latest*64,0)
            latest++
        }

        fun write(): InputStream {
            val baos = ByteArrayOutputStream()
            ImageIO.write(buff, "png", baos)
            val imageInByte = baos.toByteArray()
            return ByteArrayInputStream(imageInByte)
        }

    }

    @Deprecated("Deprecated")
    private fun generateArmorLayers() {
        val armor_assets: Path = Paths.get(resourcePackWorkingDirectory.toString(), "assets","minecraft","textures","armor_assets")
        val armor: Path = Paths.get(resourcePackWorkingDirectory.toString(), "assets","minecraft","textures","models","armor")

        val armorAmount = (armor_assets.toFile().listFiles()?.size ?: 38) / 2

        val layer1File = File(armor.toFile(), "leather_layer_1.png")
        val layer2File = File(armor.toFile(), "leather_layer_2.png")

        val layer1Buffer = BufferedImage(armorAmount * 64, 32, BufferedImage.TYPE_INT_ARGB)
        val layer2Buffer = BufferedImage(armorAmount * 64, 32, BufferedImage.TYPE_INT_ARGB)

        val layer1Graphics = layer1Buffer.graphics
        val layer2Graphics = layer2Buffer.graphics

        var i = 1
        var j = 1

        Files.walk(armor_assets).forEach { p ->
            if(p.isDirectory()) {
                return@forEach
            }

            val image = ImageIO.read(p.toFile())
            val armorName = p.fileName.name.split("_")[0]
            if(armorName == "vanilla") {
                if(p.fileName.toString().endsWith("1.png")) {
                    layer1Graphics.drawImage(image, 0, 0, null)
                } else {
                    layer2Graphics.drawImage(image, 0, 0, null)
                }
            } else {

                val color = ArmorSet.valueOf(armorName.uppercase()).color.asRGB()

                if(p.fileName.toString().endsWith("1.png")) {
                    layer1Graphics.drawImage(image, i * 64, 0, null)
                    layer1Graphics.color = java.awt.Color(color)
                    layer1Graphics.drawLine(i*64,0,i*64,0)
                    i++
                } else {
                    layer2Graphics.drawImage(image, j * 64, 0, null)
                    layer2Graphics.color = java.awt.Color(color)
                    layer2Graphics.drawLine(j*64,0,j*64,0)
                    j++
                }
            }
        }

        ImageIO.write(layer1Buffer, "PNG", layer1File)
        ImageIO.write(layer2Buffer, "PNG", layer2File)
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

    private fun generateItemModels() {
        val groupedTypes = ItemType.values().groupBy {
            it.getMaterial()
        }

        for ((material, types) in groupedTypes) {
            val jsonObject = JsonObject()

            val textures = JsonObject()
            if (material.isBlock) {
                jsonObject.addProperty("parent", "minecraft:block/cube_all")
                textures.addProperty("all", "minecraft:block/" + material.name.lowercase())

            } else {
                jsonObject.addProperty("parent", "minecraft:item/handheld")
                textures.addProperty("layer0", "minecraft:item/" + material.name.lowercase())
            }
            jsonObject.add("textures", textures)

            val overrides = JsonArray()
            for (type in types) {
                val entry = JsonObject()

                val predicate = JsonObject()
                predicate.addProperty("custom_model_data", type.modelID)
                entry.add("predicate", predicate)

                entry.addProperty("model", "item/" + type.name.lowercase())

                overrides.add(entry)
            }

            jsonObject.add("overrides", overrides)

            val gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()
            val outputFile = File(
                Paths.get(
                    inst().dataFolder.absolutePath,
                    "mpp_resourcepack",
                    "assets",
                    "minecraft",
                    "models",
                    "item",
                    material.name.lowercase() + ".json"
                ).toString()
            )
            outputFile.parentFile.mkdirs()
            val fileWriter = FileWriter(outputFile)
            gson.toJson(jsonObject, fileWriter)
            fileWriter.flush()
            fileWriter.close()
        }
    }

    private fun uploadPack() {
        Unirest
            .post("http://${inst().configManager.resourcePackHostIP}:${inst().configManager.resourcePackHostPort}/upload")
            .field("file", resourcePackZipPath.toFile())
            .asStringAsync {
                logInfo("Successfully uploaded resource pack!")
                deleteZip()
            }

    }

    private fun deleteZip() {
        Files.delete(resourcePackZipPath)
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

    private class ResourcePackListener(
        private val url: String,
        private val hash: String
    ) : Listener {

        @EventHandler
        fun onJoin(event: PlayerJoinEvent) {
            event.player.setResourcePack(url, hash, true)
        }
    }
}