package de.danielmaile.mpp.util

import de.danielmaile.mpp.inst
import java.io.IOException
import java.io.InputStream
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