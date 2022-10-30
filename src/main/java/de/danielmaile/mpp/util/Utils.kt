package de.danielmaile.mpp.util

import de.danielmaile.mpp.inst
import org.bukkit.Instrument
import java.io.*
import java.nio.file.Path
import java.text.CompactNumberFormat
import java.text.NumberFormat
import java.util.*


fun logInfo(message: String) {
    inst().logger.info(message)
}

fun logWarning(message: String) {
    inst().logger.warning(message)
}

fun logError(message: String) {
    inst().logger.severe(message)
}

@Throws(IOException::class)
fun getResource(fileName: String): InputStream? {
    return inst().javaClass.classLoader.getResourceAsStream(fileName)
}

fun String.isLong(): Boolean {
    return try {
        java.lang.Long.parseLong(this)
        true
    } catch (exception: java.lang.NumberFormatException) {
        false
    }
}

fun Long.abbreviateNumber(): String {
    val locale = Locale.forLanguageTag(inst().config.getString("language_file"))
    return CompactNumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.SHORT).format(this)
        .replace('\u00a0', ' ')
}

@Throws(IOException::class)
fun saveResource(resourcePath: String, outputPath: Path) {
    val inputStream = inst().javaClass.classLoader.getResourceAsStream(resourcePath) ?: throw IllegalArgumentException()
    val directory = outputPath.toString().substringBeforeLast(File.separator)

    //Create output directory
    val outputDirectory = File(directory)
    if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
        logError("Output directories for resource can't be created!")
        return
    }

    //Write file
    val outFile = outputPath.toFile()
    val outputStream: OutputStream = FileOutputStream(outFile)
    val buf = ByteArray(1024)
    var length: Int
    while (inputStream.read(buf).also { length = it } > 0) {
        outputStream.write(buf, 0, length)
    }
    outputStream.close()
    inputStream.close()
}

fun Instrument.toMinecraftName(): String {
    return when(this) {
        Instrument.PIANO -> "harp"
        Instrument.BASS_DRUM -> "basedrum"
        Instrument.SNARE_DRUM -> "snare"
        Instrument.STICKS -> "hat"
        Instrument.BASS_GUITAR -> "bass"
        Instrument.FLUTE -> "flute"
        Instrument.BELL -> "bell"
        Instrument.GUITAR -> "guitar"
        Instrument.CHIME -> "chime"
        Instrument.XYLOPHONE -> "xylophone"
        Instrument.IRON_XYLOPHONE -> "iron_xylophone"
        Instrument.COW_BELL -> "cow_bell"
        Instrument.DIDGERIDOO -> "didgeridoo"
        Instrument.BIT -> "bit"
        Instrument.BANJO -> "banjo"
        Instrument.PLING -> "pling"
    }
}