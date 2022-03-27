package de.danielmaile.aether.item;

import org.bukkit.entity.Player;

import java.util.Arrays;

public enum ArmorSet
{
    VALKYRIE(ItemType.VALKYRE_RING, ItemType.VALKYRE_WINGS, ItemType.VALKYRE_LEGGINGS, ItemType.VALKYRE_BOOTS),
    ZANITE(ItemType.ZANITE_HELMET, ItemType.ZANITE_CHESTPLATE, ItemType.ZANITE_LEGGINGS, ItemType.ZANITE_BOOTS),
    GRAVITITE(ItemType.GRAVITITE_HELMET, ItemType.GRAVITITE_CHESTPLATE, ItemType.GRAVITITE_LEGGINGS, ItemType.GRAVITITE_BOOTS);

    private final ItemType head;
    private final ItemType chest;
    private final ItemType legs;
    private final ItemType feet;

    ArmorSet(ItemType head, ItemType chest, ItemType legs, ItemType feet)
    {
        this.head = head;
        this.chest = chest;
        this.legs = legs;
        this.feet = feet;
    }

    public boolean contains(ItemType itemType)
    {
        return head == itemType || chest == itemType
                || legs == itemType || feet == itemType;
    }

    public static ArmorSet getEquippedSet(Player player)
    {
        ItemType headType = ItemType.fromTag(player.getEquipment().getHelmet());
        ItemType chestType = ItemType.fromTag(player.getEquipment().getChestplate());
        ItemType legsType = ItemType.fromTag(player.getEquipment().getLeggings());
        ItemType feetType = ItemType.fromTag(player.getEquipment().getBoots());
        return Arrays.stream(ArmorSet.values())
                .filter(set -> set.head == headType && set.chest == chestType && set.legs == legsType && set.feet == feetType)
                .findFirst().orElse(null);
    }
}
