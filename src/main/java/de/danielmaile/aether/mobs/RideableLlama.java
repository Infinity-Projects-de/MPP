package de.danielmaile.aether.mobs;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import de.danielmaile.aether.Aether;
import de.danielmaile.aether.worldgen.AetherWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RideableLlama
{
    public void init(ProtocolManager protocolManager)
    {
        protocolManager.addPacketListener(new PacketAdapter(Aether.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE)
        {
            @Override
            public void onPacketReceiving(PacketEvent event)
            {
                PacketContainer packetContainer = event.getPacket();
                Player player = event.getPlayer();
                Entity vehicle = player.getVehicle();

                if(vehicle == null) return;
                if(!(vehicle instanceof Llama llama)) return;
                if(!llama.getLocation().getWorld().equals(AetherWorld.getWorld())) return;
                if(llama.getInventory().getDecor() == null) return;

                //Jumping
                boolean jump = packetContainer.getBooleans().read(0);
                if(jump && llama.isOnGround())
                {
                    llama.setVelocity(llama.getVelocity().add(new Vector(0.0, Aether.getInstance().getConfigManager().getLlamaJumpHeight(), 0.0)));
                }

                //Calculate velocity
                float leftRight = packetContainer.getFloat().read(0);
                float forwardBackward = packetContainer.getFloat().read(1);

                Vector horizontal = new Vector(-forwardBackward, 0, leftRight);
                if (horizontal.length() > 0)
                {
                    //Turn to face of vector in direction which the player is facing
                    horizontal.rotateAroundY(Math.toRadians(-player.getLocation().getYaw() + 90f));

                    //Scale vector
                    horizontal.normalize().multiply(Aether.getInstance().getConfigManager().getLlamaSpeed());
                }

                Vector vertical = llama.getVelocity();
                vertical.setX(0);
                vertical.setZ(0);
                llama.setVelocity(horizontal.add(vertical));

                //Rotation
                llama.setRotation(player.getEyeLocation().getYaw(), player.getEyeLocation().getPitch());
            }
        });
    }
}
