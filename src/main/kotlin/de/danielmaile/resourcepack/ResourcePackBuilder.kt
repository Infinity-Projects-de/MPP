/*
 * This file is part of MPP.
 * Copyright (c) 2023 by it's authors. All rights reserved.
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
package de.danielmaile.resourcepack

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import de.danielmaile.mpp.block.BlockType
import de.danielmaile.mpp.item.ArmorMaterial
import de.danielmaile.mpp.item.ItemRegistry
import de.danielmaile.mpp.registerItems
import de.danielmaile.mpp.util.toMinecraftName
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.imageio.ImageIO

// Entry point for the gradle task. Do not change!
fun main(args: Array<String>) {
    val projectDir = File(args[1])
    PackBuilder(projectDir, args[0]).buildPack()
}

class PackBuilder(
    private val projectDir: File,
    private val pluginVersion: String
) {
    private val gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()
    private val resourcesDir = projectDir
        .toPath()
        .resolve(
            Paths.get(
                "src",
                "main",
                "kotlin",
                "de",
                "danielmaile",
                "resourcepack",
                "resources"
            )
        ).toFile()

    fun buildPack() {
        val targetDir = projectDir.toPath().resolve(Paths.get("build", "resourcepack")).toFile()
        targetDir.deleteOnExit()
        targetDir.mkdirs()

        val outputStream = ByteArrayOutputStream()
        val zipOutputStream = ZipOutputStream(outputStream)
        registerItems()
        copyBasePack(zipOutputStream)
        generateBlockStatesJson(zipOutputStream)
        generateItemModels(zipOutputStream)
        generateArmorLayers(zipOutputStream)

        zipOutputStream.close()
        val zipFile = File(targetDir, "MPP-$pluginVersion.zip")
        Files.write(zipFile.toPath(), outputStream.toByteArray())
    }

    private fun copyBasePack(zipOutputStream: ZipOutputStream) {
        val basePackDir = File(resourcesDir, "basepack")
        basePackDir.walkTopDown()
            .filter { it.isFile }
            .forEach {
                val relativePath = basePackDir.toPath().relativize(it.toPath())
                val zipEntry = ZipEntry(relativePath.toString())
                zipOutputStream.putNextEntry(zipEntry)
                Files.copy(it.toPath(), zipOutputStream)
                zipOutputStream.closeEntry()
            }
    }

    private fun generateBlockStatesJson(zipOutputStream: ZipOutputStream) {
        val jsonObject = JsonObject()

        val variants = JsonObject()
        for (blockType in BlockType.values()) {
            val model = JsonObject()
            model.addProperty("model", "minecraft:block/" + blockType.name.lowercase(Locale.getDefault()))
            variants.add(blockType.toBlockDataString(), model)
        }

        jsonObject.add("variants", variants)
        val gsonString = gson.toJson(jsonObject)

        val relativePath = Paths.get(
            "assets",
            "minecraft",
            "blockstates",
            "note_block.json"
        )
        addZipEntry(zipOutputStream, gsonString, relativePath)
    }

    private fun generateItemModels(zipOutputStream: ZipOutputStream) {
        val groupedTypes = ItemRegistry.getAllItems().groupBy {
            it.material
        }

        for ((material, items) in groupedTypes) {
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
            for (item in items.sortedBy { it.modelID }) {
                val entry = JsonObject()

                val predicate = JsonObject()
                predicate.addProperty("custom_model_data", item.modelID)
                entry.add("predicate", predicate)

                entry.addProperty("model", "item/" + item.name.lowercase())

                overrides.add(entry)
            }

            jsonObject.add("overrides", overrides)
            val gsonString = gson.toJson(jsonObject)

            val relativePath = Paths.get(
                "assets",
                "minecraft",
                "models",
                "item",
                material.name.lowercase() + ".json"
            )
            addZipEntry(zipOutputStream, gsonString, relativePath)
        }
    }

    private fun generateArmorLayers(zipOutputStream: ZipOutputStream) {
        val customArmorTextureDir = File(resourcesDir, "armor")
        val vanillaArmorDir = resourcesDir
            .toPath()
            .resolve(
                Paths.get(
                    "basepack",
                    "assets",
                    "minecraft",
                    "textures",
                    "models",
                    "armor"
                )
            ).toFile()

        val armorAmount = (customArmorTextureDir.listFiles()?.size ?: 38) / 2

        val layer1File = File(vanillaArmorDir, "leather_layer_1.png")
        val layer2File = File(vanillaArmorDir, "leather_layer_2.png")

        val layer1Buffer = BufferedImage(armorAmount * 64, 32, BufferedImage.TYPE_INT_ARGB)
        val layer2Buffer = BufferedImage(armorAmount * 64, 32, BufferedImage.TYPE_INT_ARGB)

        val layer1Graphics = layer1Buffer.graphics
        val layer2Graphics = layer2Buffer.graphics

        var i = 1
        var j = 1

        customArmorTextureDir
            .walkTopDown()
            .filter { it.isFile }
            .forEach {
                val image = ImageIO.read(it)
                val armorName = it.name.split("_")[0]

                if (armorName == "vanilla") {
                    if (it.name.endsWith("1.png")) {
                        layer1Graphics.drawImage(image, 0, 0, null)
                    } else {
                        layer2Graphics.drawImage(image, 0, 0, null)
                    }
                } else {
                    try {
                        val color = ArmorMaterial.valueOf(armorName.uppercase()).color.asRGB()
                        if (it.name.endsWith("1.png")) {
                            layer1Graphics.drawImage(image, i * 64, 0, null)
                            layer1Graphics.color = java.awt.Color(color)
                            layer1Graphics.drawLine(i * 64, 0, i * 64, 0)
                            i++
                        } else {
                            layer2Graphics.drawImage(image, j * 64, 0, null)
                            layer2Graphics.color = java.awt.Color(color)
                            layer2Graphics.drawLine(j * 64, 0, j * 64, 0)
                            j++
                        }
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace() // This would mean a texture is in the RP but is not in ArmorMaterial.kt
                    }
                }
            }

        val layer1RelativePath = File(resourcesDir, "basepack").toPath().relativize(layer1File.toPath())
        val layer2RelativePath = File(resourcesDir, "basepack").toPath().relativize(layer2File.toPath())

        addZipEntry(zipOutputStream, layer1Buffer, layer1RelativePath)
        addZipEntry(zipOutputStream, layer2Buffer, layer2RelativePath)
    }

    private fun addZipEntry(zipOutputStream: ZipOutputStream, imageBuffer: BufferedImage, relativePath: Path) {
        val zipEntry = ZipEntry(relativePath.toString())
        zipOutputStream.putNextEntry(zipEntry)
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(imageBuffer, "png", outputStream)
        outputStream.writeTo(zipOutputStream)
        zipOutputStream.closeEntry()
    }

    private fun addZipEntry(zipOutputStream: ZipOutputStream, string: String, relativePath: Path) {
        val stringBytes = string.toByteArray(StandardCharsets.UTF_8)

        val zipEntry = ZipEntry(relativePath.toString())
        zipOutputStream.putNextEntry(zipEntry)
        ByteArrayInputStream(stringBytes).use {
            it.copyTo(zipOutputStream)
        }
        zipOutputStream.closeEntry()
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
