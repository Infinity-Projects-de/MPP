package de.danielmaile.aether.util;

import org.bukkit.Location;

import java.io.Serializable;

public record SimpleLocation(int x, int y, int z) implements Serializable
{
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }

    public static SimpleLocation fromLocation(Location location)
    {
        return new SimpleLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
