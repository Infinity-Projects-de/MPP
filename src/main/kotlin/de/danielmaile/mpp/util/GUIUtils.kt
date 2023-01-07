/*
 * This file is part of MPP.
 * Copyright (c) 2023 by it's authors. All rights reserved.
 * MPP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MPP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MPP.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.danielmaile.mpp.util

import de.danielmaile.mpp.inst
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

fun getFillerItem(): ItemStack {
    return createGUIItem(ItemStack(Material.GRAY_STAINED_GLASS_PANE), Component.empty(), listOf())
}

fun getArrowLeftItem(): ItemStack {
    val base64 =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="
    val skull = SkullFactory.fromBase64(base64)
    return createGUIItem(
        skull, inst().getLanguageManager().getComponent("gui.item.page_left.name"),
        inst().getLanguageManager().getComponentList("gui.item.page_left.description")
    )
}

fun getArrowRightItem(): ItemStack {
    val base64 =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19"
    val skull = SkullFactory.fromBase64(base64)
    return createGUIItem(
        skull, inst().getLanguageManager().getComponent("gui.item.page_right.name"),
        inst().getLanguageManager().getComponentList("gui.item.page_right.description")
    )
}

private fun createGUIItem(itemStack: ItemStack, displayName: Component, lore: List<Component>): ItemStack {
    val meta = itemStack.itemMeta ?: error("GUIUtils item meta is null")
    meta.displayName(displayName)
    meta.lore(lore)
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
    itemStack.setItemMeta(meta)
    return itemStack
}