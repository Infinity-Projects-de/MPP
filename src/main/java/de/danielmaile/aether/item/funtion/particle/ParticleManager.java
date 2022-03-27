package de.danielmaile.aether.item.funtion.particle;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.util.VectorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticleManager
{
    public enum ParticleType
    {
        VALKYRE_RING, VALKYRE_WINGS
    }

    private final HashMap<Player, List<ParticleType>> playerParticleList;

    public ParticleManager()
    {
        playerParticleList = new HashMap<>();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Aether.getInstance(), () ->
        {
            for (Map.Entry<Player, List<ParticleType>> entry : playerParticleList.entrySet())
            {
                Player player = entry.getKey();
                for (ParticleType type : entry.getValue())
                {
                    switch (type)
                    {
                        case VALKYRE_RING -> {
                            Location location = player.getLocation().clone().add(0, 2.2, 0);
                            spawnCircle(location, 0.5, 30, Color.YELLOW);
                        }
                        case VALKYRE_WINGS -> {
                            Location location = player.getLocation().clone().add(0, 1, 0);
                            spawnWings(location, Color.YELLOW);
                        }
                    }
                }
            }
        }, 5L, 5L);
    }

    @SuppressWarnings("SameParameterValue")
    private void spawnWings(Location location, Color color)
    {
        for (double i = 0; i < Math.PI * 2; i += Math.PI / 48)
        {
            double offset = (Math.pow(Math.E, Math.cos(i)) - 2 * Math.cos(i * 4) - Math.pow(Math.sin(i / 12), 5)) / 2;
            double x = Math.sin(i) * offset;
            double y = Math.cos(i) * offset;
            Vector vector = VectorUtils.rotateAroundAxisY(new Vector(x, y, -0.3), -Math.toRadians(location.getYaw()));
            spawnParticle(location.clone().add(vector.getX(), vector.getY(), vector.getZ()), color);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void spawnCircle(Location center, double radius, int amount, Color color)
    {
        for (int i = 0; i < 360; i += 360 / amount)
        {
            double angle = (i * Math.PI / 180);
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            Location location = new Location(center.getWorld(), center.getX() + x, center.getY(), center.getZ() + z);
            spawnParticle(location, color);
        }
    }

    private void spawnParticle(Location location, Color color)
    {
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1);
        location.getWorld().spawnParticle(Particle.REDSTONE, location, 0, dustOptions);
    }

    public void removeParticleType(Player player, ParticleType type)
    {
        List<ParticleType> particles = playerParticleList.get(player);
        if (particles != null)
        {
            particles.remove(type);
        }
    }

    public void addParticleType(Player player, ParticleType type)
    {
        List<ParticleType> particles = playerParticleList.computeIfAbsent(player, k -> new ArrayList<>());
        if (!particles.contains(type))
        {
            particles.add(type);
        }
    }

    public void removePlayer(Player player)
    {
        playerParticleList.remove(player);
    }
}
