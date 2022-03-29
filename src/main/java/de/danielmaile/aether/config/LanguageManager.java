package de.danielmaile.aether.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public record LanguageManager(FileConfiguration languageFile)
{
    @Nonnull
    public Component getComponent(String path)
    {
        return getComponent(path, null);
    }

    @Nonnull
    public Component getComponent(String path, @Nullable TagResolver tagResolver)
    {
        String string = languageFile.getString(path);
        if (string == null)
        {
            return Component.empty();
        }

        return tagResolver != null ? MiniMessage.miniMessage().deserialize(string, tagResolver) : MiniMessage.miniMessage().deserialize(string);
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
