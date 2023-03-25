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

package de.danielmaile.mpp.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import de.danielmaile.mpp.inst
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import java.io.*
import java.net.URL
import java.nio.file.Path
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


val gson: Gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()

/**
 * Creates a pseudo-file from a [jsonObject] and sets its relative path to [relativePath]
 * @param jsonObject JSON Object to be stored
 * @param relativePath Relative path to be set to
 * @return A [MemoryFile] containing the json Object pseudo-file
 */
fun memoryFileFromJSON(jsonObject: JsonObject, relativePath: Path): MemoryFile {
    ByteArrayOutputStream().use { baos ->
        OutputStreamWriter(baos).use {out ->
            gson.toJson(jsonObject, out)
            val fileInBytes = baos.toByteArray()
            val inputStream = ByteArrayInputStream(fileInBytes)

            return MemoryFile(relativePath.toString(), inputStream)
        }
    }

}

/**
 * Pseudo-file that consists of an [inputStream] and a (preferably) relative [path].
 * @param path Relative path of the file
 * @param inputStream Data of the file
 * @constructor Creates the pseudo-file with a path and an input stream
 * @see Closeable
 */
class MemoryFile(val path: String, val inputStream: InputStream) : Closeable {
    /**
     * Converts the inputStream into a temporary file. Path is NOT considered
     * @return Temporary file containing the input stream data
     */
    val file: File
        get() {
            val tmp = File.createTempFile("tmp",null)
            FileUtils.copyInputStreamToFile(inputStream, tmp)
            return tmp
        }
    override fun close() {
        inputStream.close()
    }
}

/**
 * Class that helps creating a zip archive easily in memory
 * @see Closeable
 */
class ZipArchive : Closeable {
    private val baos = ByteArrayOutputStream()
    private val zipArchiveOut = ZipOutputStream(baos)

    /**
     * Stores a file from [url] into the [ZipArchive]
     * @param url URL of the file given by FileUtils#getAssetsFromJar
     * @param relativePath Relative path of where to put the file
     */
    fun storeFile(url: URL, relativePath: String) {
        val entry = ZipEntry(relativePath)
        zipArchiveOut.putNextEntry(entry)
        url.openStream().use { it.copyTo(zipArchiveOut) }
        zipArchiveOut.closeEntry()
    }

    /**
     * Stores the [file] into the [ZipArchive]
     * @param file Pseudo-file to be stored
     */
    fun storeFile(file: MemoryFile) {
        val entry = ZipEntry(file.path)
        zipArchiveOut.putNextEntry(entry)

        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (file.inputStream.read(buffer).also { bytesRead = it } != -1) {
            zipArchiveOut.write(buffer, 0, bytesRead)
        }

        zipArchiveOut.closeEntry()
    }

    /**
     * Gets the InputStream representing the zip archive
     * @return InputStream containing the zip archive data
     */
    val inputStream: InputStream
        get() {
            val fileInBytes = baos.toByteArray()
            return ByteArrayInputStream(fileInBytes)
        }

    override fun close() {
        zipArchiveOut.close()
        baos.close()

    }
}

/**
 * Copies assets from the plugin jar file to the
 * given [outputFolder]. Only includes assets located
 * in the folder specified in [relativeFolder]. This method is
 * deprecated as well because no more files are going to
 * be saved from now on.
 */
@Deprecated("Changing to a method that doesn't require saving anything", ReplaceWith("assetsFromJar","relativeFolder"))
fun copyAssetsFromJar(relativeFolder: String, outputFolder: Path) {
    val jarFile = getPluginJar()

    if (jarFile.isFile) { // Might this be redundant?
        val jar = JarFile(jarFile)
        val entries: Enumeration<JarEntry> = jar.entries()

        while (entries.hasMoreElements()) {
            val jarEntry = entries.nextElement()
            val name: String = jarEntry.name

            if (name.startsWith("$relativeFolder/")) {

                // exclude folders
                if (name.endsWith('/')) continue

                saveResource(name, File(outputFolder.toFile().parentFile, name))
            }
        }
        jar.close()
    } else {
        logError("Failed to copy assets from $relativeFolder to $outputFolder")
    }
}

/**
 * Saves the elements in the specified [relativeFolder] into [MemoryFile]s
 * @param relativeFolder Relative folder that should contain the resources
 * @return [Array] of type [MemoryFile] of the resources
 */
fun getAssetsFromJar(relativeFolder: String, vararg exclude: String): Array<MemoryFile> {
    val files = arrayListOf<MemoryFile>()

    val jarFile = getPluginJar()

    if (jarFile.isFile) {  // Run with JAR file
        val jar = JarFile(jarFile)
        val entries: Enumeration<JarEntry> = jar.entries() //gives ALL entries in jar
        while (entries.hasMoreElements()) {
            val jarEntry = entries.nextElement()
            val name = jarEntry.name

            if (name.startsWith("$relativeFolder/")) { //filter according to the path

                val parentLess = name.split("/").drop(1).joinToString("/")
                for (excluded in exclude) {
                    if (parentLess.startsWith("/$excluded")) continue
                }

                if (name.endsWith('/')) continue
                files.add(resourceToMemoryFile(name))
            }
        }
        jar.close()
    } else {
        throw IOException("Couldn't find jar file")
    }

    return files.toTypedArray()
}

/**
 * Gets the specified resource and saves it into a [MemoryFile]
 * @param resourcePath Path containing the resource
 * @return [MemoryFile] containing the resource
 */
fun resourceToMemoryFile(resourcePath: String): MemoryFile {
    val inputStream = inst().javaClass.classLoader.getResourceAsStream(resourcePath) ?: throw java.lang.IllegalArgumentException()
    return MemoryFile(resourcePath, inputStream)
}

/**
 * This method saves the specified resource into the specified [outputFile]. No more files should be saved anymore so I deprecated it.
 */
@Deprecated("Deprecated in favor of non file-creating methods")
@Throws(IOException::class)
fun saveResource(resourcePath: String, outputFile: File) {
    val inputStream = inst().javaClass.classLoader.getResourceAsStream(resourcePath) ?: throw IllegalArgumentException()
    val directory = outputFile.toString().substringBeforeLast(File.separator)

    // create output directory
    val outputDirectory = File(directory)
    if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
        logError("Output directories for resource can't be created!")
        return
    }

    // write file
    val outputStream: OutputStream = FileOutputStream(outputFile)
    val buf = ByteArray(1024)
    var length: Int
    while (inputStream.read(buf).also { length = it } > 0) {
        outputStream.write(buf, 0, length)
    }

    outputStream.close()
    inputStream.close()
}

@Throws(IOException::class)
@Deprecated("Deprecated in favor of the Enumeration-returning method?",
    ReplaceWith("inst().javaClass.classLoader.getResourceAsStream(fileName)", "de.danielmaile.mpp.inst")
)
fun getResource(fileName: String): InputStream? {
    return inst().javaClass.classLoader.getResourceAsStream(fileName)
}

fun getPluginJar(): File {
    // unescape %20 to spaces to prevent FileNotFoundException if the path contains a space on linux
    return File(inst().javaClass.protectionDomain.codeSource.location.path.toString().replace("%20", " "))
}

/**
 * Calculates the sha1 hash of a given
 * [file]. If the file is a folder or
 * does not exist, the returned String
 * will be empty
 */

// TODO NO need of probably?
fun calculateSHA1Hash(file: File): String {
    if(file.isDirectory || !file.exists()) {
        return ""
    }

    FileInputStream(file).use {
        return DigestUtils.sha1Hex(it)
    }
}

fun calculateSHA1Hash(inputStream: InputStream): String {
    return DigestUtils.sha1Hex(inputStream)
}

/**
 * Returns true if the contents of
 * [directory1] and [directory2] are
 * equal.
 */
fun directoryEquals(directory1: File, directory2: File): Boolean {
    val fileList1 = ArrayList (FileUtils.listFiles(directory1, null, true))
    val fileList2 = ArrayList (FileUtils.listFiles(directory2, null, true))

    if(fileList1.size != fileList2.size) {
        return false
    }

    for(i in fileList1.indices) {
        if(!FileUtils.contentEquals(fileList1[i], fileList2[i])) {
            return false
        }
    }

    return true
}