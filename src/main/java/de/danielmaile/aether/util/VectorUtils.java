package de.danielmaile.aether.util;

import org.bukkit.util.Vector;

public class VectorUtils
{
    public static Vector rotateAroundAxisY(Vector vector, double angle)
    {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double x = vector.getX() * cos + vector.getZ() * sin;
        double z = vector.getX() * -sin + vector.getZ() * cos;
        return vector.setX(x).setZ(z);
    }
}
