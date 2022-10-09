package de.danielmaile.mpp.config

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.configuration.file.FileConfiguration

class LanguageManager(private val languageFile: FileConfiguration) {

    fun getComponent(path: String): Component {
        return getComponent(path, null)
    }

    fun getComponent(path: String, tagResolver: TagResolver?): Component {
        val string = languageFile.getString(path) ?: return Component.empty()
        return if (tagResolver != null) MiniMessage.miniMessage()
            .deserialize(string, tagResolver) else MiniMessage.miniMessage().deserialize(string)
    }

    fun getComponentList(path: String): List<Component> {
        val stringList = languageFile.getStringList(path)
        if (stringList.isEmpty()) {
            return listOf(Component.empty())
        }

        val componentList = ArrayList<Component>()
        for (string in stringList) {
            componentList.add(MiniMessage.miniMessage().deserialize(string))
        }
        return componentList
    }

    fun getStringList(path: String): List<String> {
        return languageFile.getStringList(path)
    }
}