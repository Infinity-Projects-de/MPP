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

import com.destroystokyo.paper.profile.CraftPlayerProfile
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.net.URL
import java.util.UUID

object SkullFactory {

    fun fromTexture(texture: String): ItemStack {
        val skull = ItemStack(Material.PLAYER_HEAD)

        val skullMeta = skull.itemMeta as SkullMeta

        val profile = CraftPlayerProfile(UUID.randomUUID(), null)
        val textures = profile.textures
        textures.skin = URL("https://textures.minecraft.net/texture/$texture")
        profile.setTextures(textures)

        skullMeta.playerProfile = profile
        skull.itemMeta = skullMeta
        return skull
    }

    fun fromPlayer(player: OfflinePlayer): ItemStack {
        val skull = ItemStack(Material.PLAYER_HEAD)
        val skullMeta = skull.itemMeta as SkullMeta

        skullMeta.owningPlayer = player
        skull.itemMeta = skullMeta
        return skull
    }
}
