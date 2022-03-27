package de.danielmaile.aether.item.funtion.magicwand;

import de.danielmaile.aether.Aether;
import de.danielmaile.aether.item.ItemType;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ListenerMagicWand implements Listener
{
    @EventHandler
    public void onItemUse(PlayerInteractEvent event)
    {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (ItemType.fromTag(event.getItem()) != ItemType.MAGIC_WAND) return;
        event.setCancelled(true);

        MagicWand wand = new MagicWand(event.getItem());
        if (event.getAction().isLeftClick())
        {
            //Change spell
            wand.nextSpell();
            Component component = Aether.getLanguageManager().getComponent("items.MAGIC_WAND.current_spell")
                    .append(wand.getCurrentSpell().getName());
            event.getPlayer().sendActionBar(component);
            event.getPlayer().getInventory().setItemInMainHand(wand.getItemStack());
        }
        else if (event.getAction().isRightClick())
        {
            //Fire magic wand
            wand.fire(event.getPlayer());
        }
    }
}
