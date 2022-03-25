package de.danielmaile.aether.item;

import org.bukkit.entity.Player;

import java.util.Arrays;

public enum ArmorSet
{
    VALKYRE(CustomItemType.VALKYRE_RING, CustomItemType.VALKYRE_WINGS, CustomItemType.VALKYRE_LEGGINGS, CustomItemType.VALKYRE_BOOTS),
    ZANITE(CustomItemType.ZANITE_HELMET, CustomItemType.ZANITE_CHESTPLATE, CustomItemType.ZANITE_LEGGINGS, CustomItemType.ZANITE_BOOTS),
    GRAVITITE(CustomItemType.GRAVITIT_HELMET, CustomItemType.GRAVITIT_CHESTPLATE, CustomItemType.GRAVITIT_LEGGINGS, CustomItemType.GRAVITIT_BOOTS);

    private final CustomItemType head;
    private final CustomItemType chest;
    private final CustomItemType legs;
    private final CustomItemType feet;

    ArmorSet(CustomItemType head, CustomItemType chest, CustomItemType legs, CustomItemType feet)
    {
        this.head = head;
        this.chest = chest;
        this.legs = legs;
        this.feet = feet;
    }

    public static ArmorSet getEquippedSet(Player player)
    {
        CustomItemType headType = CustomItemType.getFromTag(player.getEquipment().getHelmet());
        CustomItemType chestType = CustomItemType.getFromTag(player.getEquipment().getChestplate());
        CustomItemType legsType = CustomItemType.getFromTag(player.getEquipment().getLeggings());
        CustomItemType feetType = CustomItemType.getFromTag(player.getEquipment().getBoots());
        return Arrays.stream(ArmorSet.values())
                .filter(set -> set.head == headType && set.chest == chestType && set.legs == legsType && set.feet == feetType)
                .findFirst().orElse(null);
    }
}
