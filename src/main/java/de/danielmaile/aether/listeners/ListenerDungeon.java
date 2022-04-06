package de.danielmaile.aether.listeners;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.worldgen.AetherWorld;
import de.danielmaile.aether.worldgen.dungeon.Dungeon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.HashMap;

public class ListenerDungeon implements Listener
{
    private final HashMap<Player, ArrayList<Location>> monumentClickMap = new HashMap<>();

    @EventHandler
    public void onMonumentClick(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getWorld().equals(AetherWorld.getWorld())) return;

        Location clickLocation = event.getClickedBlock().getLocation();
        Dungeon targetDungeon = AetherWorld.getObjectManager().getDungeons()
                .stream()
                .filter(dungeon -> dungeon.getMonumentLocation().equals(clickLocation))
                .findFirst()
                .orElse(null);
        if (targetDungeon == null) return;
        Location targetLocation = targetDungeon.getMonumentTargetLocation();
        event.setCancelled(true);

        ArrayList<Location> clickedLocations = monumentClickMap.get(event.getPlayer());
        if (clickedLocations != null && clickedLocations.contains(clickLocation))
        {
            //Teleport player
            clickedLocations.remove(clickLocation);
            event.getPlayer().teleport(new Location(AetherWorld.getWorld(), targetLocation.getX(), targetLocation.getY(), targetLocation.getZ()));

            //Send dungeon message
            Component mainTitle = Aether.getInstance().getLanguageManager().getComponent("dungeons.entry.title");
            TagResolver tagResolver = TagResolver.resolver(Placeholder.parsed("size", Integer.toString(targetDungeon.getSize())));
            Component subTitle = Aether.getInstance().getLanguageManager().getComponent("dungeons.entry.subtitle", tagResolver);
            Title title = Title.title(mainTitle, subTitle);
            event.getPlayer().showTitle(title);
            return;
        }

        if (clickedLocations == null)
        {
            clickedLocations = new ArrayList<>();
        }

        clickedLocations.add(clickLocation);
        monumentClickMap.put(event.getPlayer(), clickedLocations);
        event.getPlayer().sendMessage(
                Aether.getInstance().getLanguageManager().getComponent("messages.prefix")
                        .append(Aether.getInstance().getLanguageManager().getComponent("messages.chat.click_to_dungeon_teleport")));
    }

    @EventHandler
    public void onDungeonBreak(BlockBreakEvent event)
    {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();

        //Check for monument break
        Dungeon targetDungeon = AetherWorld.getObjectManager().getDungeons()
                .stream()
                .filter(dungeon -> dungeon.getMonumentLocation().equals(event.getBlock().getLocation()))
                .findFirst()
                .orElse(null);
        if (targetDungeon != null)
        {
            event.setCancelled(true);
            return;
        }

        //Check for dungeon break
        if (!player.getWorld().equals(AetherWorld.getWorld())) return;
        if (player.getLocation().getY() > -256) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDungeonBlockPlace(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        if (!player.getWorld().equals(AetherWorld.getWorld())) return;
        if (player.getLocation().getY() > -256) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDungeonExplode(EntityExplodeEvent event)
    {
        if (!event.getLocation().getWorld().equals(AetherWorld.getWorld())) return;
        if (event.getLocation().getY() > -256) return;
        event.getEntity().remove();
        event.setCancelled(true);
    }
}
