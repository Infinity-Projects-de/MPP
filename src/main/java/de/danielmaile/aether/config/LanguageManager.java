package de.danielmaile.aether.config;

import de.danielmaile.aether.Aether;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LanguageManager
{
    private final FileConfiguration languageFile;

    public LanguageManager(FileConfiguration languageFile)
    {
        this.languageFile = languageFile;
    }

    @Nonnull
    public TextComponent getComponent(String path)
    {
        String string = languageFile.getString(path);
        if (string == null)
        {
            return Component.empty();
        }
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
    }

    @Nonnull
    public List<TextComponent> getComponentList(String path)
    {
        List<String> stringList = languageFile.getStringList(path);
        if (stringList.isEmpty())
        {
            return List.of(Component.empty());
        }

        List<TextComponent> componentList = new ArrayList<>();
        for (String string : stringList)
        {
            componentList.add(LegacyComponentSerializer.legacyAmpersand().deserialize(string));
        }
        return componentList;
    }
}
