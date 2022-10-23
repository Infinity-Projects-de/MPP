package de.danielmaile.mpp.config

import de.danielmaile.mpp.util.logWarning
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.configuration.file.FileConfiguration

class LanguageManager(private val languageFile: FileConfiguration) {

    private val hasWarnedPaths = ArrayList<String>()

    fun getComponent(path: String): Component {
        return getComponent(path, null)
    }

    fun getComponent(path: String, tagResolver: TagResolver?): Component {
        val string = getString(path)
        if(string == null || string == "") {
            return Component.empty()
        }
        return if (tagResolver != null) MiniMessage.miniMessage()
            .deserialize(string, tagResolver) else MiniMessage.miniMessage().deserialize(string)
    }

    fun getComponentList(path: String): List<Component> {
        val stringList = getStringList(path)
        if (stringList.isEmpty()) {
            return listOf(Component.empty())
        }

        val componentList = ArrayList<Component>()
        for (string in stringList) {
            if(string == "")
            {
                componentList.add(Component.empty())
            } else {
                componentList.add(MiniMessage.miniMessage().deserialize(string))
            }
        }
        return componentList
    }

    fun getStringList(path: String): List<String> {
        checkIfLanguageFileContainsPath(path)
        return languageFile.getStringList(path)
    }

    fun getString(path: String): String? {
        checkIfLanguageFileContainsPath(path)
        return languageFile.getString(path)
    }

    /**
     * Checks if the user provided language file contains a key at the given path.
     * If key does not exist and a warning about this key wasn't printed already, print a warning to the console
     */
    private fun checkIfLanguageFileContainsPath(path: String) {
        if(!languageFile.contains(path, true) && !hasWarnedPaths.contains(path)) {
            hasWarnedPaths.add(path)
            logWarning("$path was not found in the language file set in the config! Using fallback file.")
        }
    }
}