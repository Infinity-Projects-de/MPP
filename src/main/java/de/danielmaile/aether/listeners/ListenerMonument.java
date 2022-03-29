package de.danielmaile.aether.listeners;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.util.Utils;
import de.danielmaile.aether.worldgen.AetherWorld;
import de.danielmaile.aether.worldgen.dungeon.Dungeon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.HashMap;

public class ListenerMonument implements Listener
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
        Dungeon targetDungeon = AetherWorld.getObjectManager().getDungeonList()
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
            Component mainTitle = Aether.getLanguageManager().getComponent("dungeons.entry.title");
            Component subTitle = Utils.replace(Aether.getLanguageManager().getComponent("dungeons.entry.subtitle"),
                    "%size%", Component.text(targetDungeon.getSize()));
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
                Aether.getLanguageManager().getComponent("messages.prefix")
                        .append(Aether.getLanguageManager().getComponent("messages.chat.click_to_dungeon_teleport")));
    }

    @EventHandler
    public void onMonumentBreak(BlockBreakEvent event)
    {
        Dungeon targetDungeon = AetherWorld.getObjectManager().getDungeonList()
                .stream()
                .filter(dungeon -> dungeon.getMonumentLocation().equals(event.getBlock().getLocation()))
                .findFirst()
                .orElse(null);
        event.setCancelled(targetDungeon != null);
    }
}
