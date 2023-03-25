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
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.imageio.ImageIO


object ResourcePackBuilder {

    private val packName = "${RandomStringUtils.randomAlphanumeric(32)}.zip"

    private val url =
        "http://${inst().configManager.resourcePackHostIP}:${inst().configManager.resourcePackHostPort}/files/$packName"

    fun generateResourcePack() {
        logInfo("Generating resource pack...")

        val resourcePack = getAssetsFromJar("mpp_resourcepack", "parseable")
        val zipArchive = ZipArchive()

            val layer1 = getArmorAssets(true)
            val layer2 = getArmorAssets(false)

            zipArchive.storeFile(layer1)
            zipArchive.storeFile(layer2)
           generateBlockStatesJson().use {
            zipArchive.storeFile(it)
           }

            generateItemModels().forEach {file ->
                file.use {
                    zipArchive.storeFile(it)
                }
            }

            for (memoryFile in resourcePack) {
                zipArchive.storeFile(memoryFile)
                memoryFile.close()
            }

            logInfo("Successfully generated resource pack! Uploading...")

           val inputStream = zipArchive.inputStream
            uploadPack(inputStream)

           val hash = calculateSHA1Hash(inputStream) //TODO Maybe we can implement a method ZipArchive#hash
           // register pack listener
           inst().server.pluginManager.registerEvents(ResourcePackListener(url, hash), inst())
        println(url) // TODO delete


        zipArchive.close()
    }

    private fun getArmorAssets(layer1: Boolean): MemoryFile {
        val armorAssets = getAssetsFromJar("mpp_resourcepack/parseable/armor")
        val relArmorPath = Paths.get( "assets","minecraft","textures","models","armor")
        var layerNum = 1
        if(!layer1) layerNum = 2
        val armorFile = File(relArmorPath.toFile(), "leather_layer_$layerNum.png")

         val armorCount = armorAssets.size / 2
        val layer = ArmorManager(armorCount)

        for (memoryFile in armorAssets) {
            memoryFile.use {
                if ((layer1 && memoryFile.path.endsWith("1.png")) || (!layer1 && memoryFile.path.endsWith("2.png"))) {
                    layer.drawTexture(memoryFile)
                }
            }
        }

        return MemoryFile(armorFile.path, layer.write())
    }

    private fun isDirectory(url: URL): Boolean {
        if(File(url.toURI()).isDirectory) {
            return true
        }
        return false
    }

    /**
     * A simple class that helps drawing the armor textures into layers
     * @param armorAmount Amount of custom armors
     */
    private class ArmorManager(private val armorAmount: Int) {
        private val buff: BufferedImage = BufferedImage(armorAmount * 64, 32, BufferedImage.TYPE_INT_ARGB)
        private val graphics = buff.graphics

        private var latest = 1

        /**
         * Draws the given [texture]. Also reads the [url] name to know which color to use
         * @param texture Texture to draw
         * @param url Url to read the name from
         */
        fun drawTexture(memoryFile: MemoryFile) {
            val path = memoryFile.path.split("/")
            val file = path.last()
            val name = file.split("_")[0].uppercase()
            if(name == "VANILLA") {
                drawDefaultTexture(memoryFile.inputStream)
            } else {
                drawNormalTexture(memoryFile.inputStream, ArmorSet.valueOf(name))
            }
        }


        /**
         * Draws the default white [texture] into the layer
         * @param texture Texture of the vanilla white armor
         */
        private fun drawDefaultTexture(texture: InputStream) {
            val image = ImageIO.read(texture)
            graphics.drawImage(image,0,0, null)
        }

        /**
         * Draws the [texture] with its respective color from [armorSet]
         * @param texture Texture of the armor
         * @param armorSet Armor Set that corresponds to the armor
         */
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

        /**
         * Writes the layer into an input stream
         * @return Input stream containing the layer data
         */
        fun write(): InputStream {
            val baos = ByteArrayOutputStream()
            ImageIO.write(buff, "png", baos)
            val imageInByte = baos.toByteArray()
            return ByteArrayInputStream(imageInByte)
        }

    }

    /**
     * Generates block states JSON into a pseudo-file in memory
     *
     * @return A pseudo-file containing the block states json
     */
    private fun generateBlockStatesJson(): MemoryFile {
        val jsonObject = JsonObject()

        val variants = JsonObject()
        for (blockType in BlockType.values()) {
            val model = JsonObject()
            model.addProperty("model", "minecraft:block/" + blockType.name.lowercase(Locale.getDefault()))
            variants.add(blockType.toBlockDataString(), model)
        }

        jsonObject.add("variants", variants)

       return memoryFileFromJSON(jsonObject, Paths.get(
           "mpp_resourcepack",
           "assets",
           "minecraft",
           "blockstates",
           "note_block.json"
       ))

    }

    /**
     * Generates the item models and stores them into pseudo-files
     */
    // todo Note: Not going to be a problem but this will open many input streams (for a very tiny bit of time though)
    private fun generateItemModels(): Array<MemoryFile> {
        val memoryFiles = ArrayList<MemoryFile>()

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
            memoryFiles.add(memoryFileFromJSON(jsonObject, Paths.get(
                "mpp_resourcepack",
                "assets",
                "minecraft",
                "models",
                "item",
                material.name.lowercase() + ".json"
            )))
        }

        return memoryFiles.toTypedArray()
    }

    /**
     * Uploads a zip input stream (essentially the resource pack's zip archive) into the cloud storage
     * @param inputStream zip archive containing the resource pack
     */
    private fun uploadPack(inputStream: InputStream) {
        Unirest
            .post("http://${inst().configManager.resourcePackHostIP}:${inst().configManager.resourcePackHostPort}/upload")
            .field("file", inputStream, packName)
            .asStringAsync {
                logInfo("Successfully uploaded resource pack!")
            }

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