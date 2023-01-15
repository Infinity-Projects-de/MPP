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

import de.danielmaile.mpp.inst
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * Creates a zip file from the contents INSIDE the [inputFolder]
 * and saves it to the [outputFile]. If [deleteInputFolder] is
 * set to true, the input folder gets deleted afterwards.
 * Provide a [filter] method, that decides if a file should
 * be included or not.
 */
@Deprecated("Deprecated in favour of ZipArchive")
fun createZipFile(outputFile: File, inputFolder: Path, filter: (File) -> Boolean, deleteInputFolder: Boolean) {
    ZipArchiveOutputStream(FileOutputStream(outputFile)).use { archive ->
        Files.walk(inputFolder).forEach { p ->
            val file = p.toFile()

            if (!file.isDirectory) {
                if(!filter(file)) {
                    return@forEach
                }

                val entry = ZipArchiveEntry(
                    file,
                    file.toString().substringAfter(inputFolder.toString() + File.separator)
                )

                FileInputStream(file).use { fis ->
                    archive.putArchiveEntry(entry)
                    IOUtils.copy(fis, archive)
                    archive.closeArchiveEntry()
                }
            }
        }
        archive.finish()
    }

    if(deleteInputFolder) {
        FileUtils.deleteDirectory(inputFolder.toFile())
    }
}

class ZipArchive : Closeable {
    private val baos = ByteArrayOutputStream()
    private val zipArchiveOut = ZipArchiveOutputStream(baos)

    /**
     * Stores a file into the ZipArchive
     * @param url URL of the file given by FileUtils#getAssetsFromJar
     */
    fun storeFile(url: URL) {
        val entry = ZipArchiveEntry(File(url.toURI()), url.path)
        zipArchiveOut.putArchiveEntry(entry)
        url.openStream().use { it.copyTo(zipArchiveOut) }
        zipArchiveOut.closeArchiveEntry()
    }

    /**
     * Gets the InputStream representing the Zip Archive
     */
    fun getInputStream(): InputStream {
        val fileInBytes = baos.toByteArray()
        return ByteArrayInputStream(fileInBytes)
    }
    override fun close() {
        baos.close()
        zipArchiveOut.close()
    }
}

/**
 * Copies assets from the plugin jar file to the
 * given [outputFolder]. Only includes assets located
 * in the folder specified in [relativeFolder].
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
 * Gets the elements in the specified folder into an Enumeration
 */
fun getAssetsFromJar(relativeFolder: Path): Enumeration<URL> {
    return inst().javaClass.classLoader.getResources(relativeFolder.toString())
}

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
@Deprecated("Deprecated in favor of the Enumeration-returning method")
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
fun calculateSHA1Hash(file: File): String {
    if(file.isDirectory || !file.exists()) {
        return ""
    }

    FileInputStream(file).use {
        return DigestUtils.sha1Hex(it)
    }
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