package de.danielmaile.aether.worldgen;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import de.danielmaile.aether.Aether;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Collections;
import java.util.List;

public class Prefab
{
    public PrefabType getType()
    {
        return type;
    }

    public Location getLocation()
    {
        return location;
    }

    public double getYRotation()
    {
        return yRotation;
    }

    public boolean isIgnoreAirBlocks()
    {
        return ignoreAirBlocks;
    }

    private final PrefabType type;
    private final Location location;
    private final double yRotation;
    private final boolean ignoreAirBlocks;

    public Prefab(PrefabType type, Location location, double yRotation, boolean ignoreAirBlocks)
    {
        this.type = type;
        this.location = location;
        this.yRotation = yRotation;
        this.ignoreAirBlocks = ignoreAirBlocks;
    }

    public Prefab(PrefabType type, Location location, boolean ignoreAirBlocks)
    {
        this(type, location, 0, ignoreAirBlocks);
    }

    public void instantiate()
    {
        instantiate(Collections.singletonList(this));
    }

    public static void instantiate(List<Prefab> prefabs)
    {
        Bukkit.getScheduler().runTaskAsynchronously(Aether.getInstance(), () ->
        {
            for (Prefab prefab : prefabs)
            {
                try (EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(BukkitAdapter.adapt(prefab.getLocation().getWorld())).build())
                {
                    ClipboardHolder clipboardHolder = new ClipboardHolder(prefab.getType().getClipboard());
                    clipboardHolder.setTransform(new AffineTransform().rotateY(prefab.getYRotation()));
                    Operation operation = clipboardHolder
                            .createPaste(editSession)
                            .to(BlockVector3.at(prefab.getLocation().getX(), prefab.getLocation().getY(), prefab.getLocation().getZ()))
                            .copyEntities(false)
                            .ignoreAirBlocks(prefab.isIgnoreAirBlocks())
                            .build();
                    Operations.complete(operation);
                }
            }
        });
    }
}