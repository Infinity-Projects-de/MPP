package de.danielmaile.aether.util;

import java.io.IOException;
import java.io.InputStream;

public class Utils
{
    public static InputStream getResource(String filename) throws IOException
    {
        return Utils.class.getClassLoader().getResourceAsStream(filename);
    }
}
