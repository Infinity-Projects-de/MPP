package de.danielmaile.aether.util;

import javax.annotation.Nonnull;

public record Vector2I(int x, int y)
{
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Vector2I add(@Nonnull Vector2I vector)
    {
        return new Vector2I(this.x + vector.x, this.y + vector.y);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Vector2I vector2I)) return false;
        return x == vector2I.x && y == vector2I.y;
    }
}
