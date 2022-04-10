package de.danielmaile.aether.item

enum class ArmorSet(val head: ItemType, val chest: ItemType, val legs: ItemType, val feet: ItemType) {
    VALKYRIE(ItemType.VALKYRE_RING, ItemType.VALKYRE_WINGS, ItemType.VALKYRE_LEGGINGS, ItemType.VALKYRE_BOOTS),
    ZANITE(ItemType.ZANITE_HELMET, ItemType.ZANITE_CHESTPLATE, ItemType.ZANITE_LEGGINGS, ItemType.ZANITE_BOOTS),
    GRAVITITE(ItemType.GRAVITITE_HELMET, ItemType.GRAVITITE_CHESTPLATE, ItemType.GRAVITITE_LEGGINGS, ItemType.GRAVITITE_BOOTS);

    fun contains(itemType: ItemType?): Boolean {
        return head == itemType || chest == itemType || legs == itemType || feet == itemType
    }
}