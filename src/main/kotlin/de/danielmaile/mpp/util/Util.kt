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
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.InputStream
import java.util.logging.Level

fun getFillerItem(): ItemStack {
    return ItemStack(Material.GRAY_STAINED_GLASS_PANE)
}

fun getArrowLeftItem(): ItemStack {
    return ItemStack(Material.ARROW)
}

fun getArrowRightItem(): ItemStack {
    return ItemStack(Material.ARROW)
}

fun getResource(resource: String): InputStream? {
    return inst().javaClass.classLoader.getResourceAsStream(resource)
}

fun logError(message: String) {
    inst().logger.log(Level.SEVERE, message)
}
