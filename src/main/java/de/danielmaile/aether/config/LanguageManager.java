package de.danielmaile.aether.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public record LanguageManager(FileConfiguration languageFile)
{

    @Nonnull
    public Component getComponent(String path)
    {
        String string = languageFile.getString(path);
        if (string == null)
        {
            return Component.empty();
        }
        return MiniMessage.miniMessage().deserialize(string);
    }

    @Nonnull
    public List<Component> getComponentList(String path)
    {
        List<String> stringList = languageFile.getStringList(path);
        if (stringList.isEmpty())
        {
            return List.of(Component.empty());
        }

        List<Component> componentList = new ArrayList<>();
        for (String string : stringList)
        {
            componentList.add(MiniMessage.miniMessage().deserialize(string));
        }
        return componentList;
    }
}
