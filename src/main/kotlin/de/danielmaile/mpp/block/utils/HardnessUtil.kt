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

package de.danielmaile.mpp.block.utils

import de.danielmaile.mpp.block.BlockType
import de.danielmaile.mpp.item.items.Tools.ToolType
import net.minecraft.tags.TagKey
import net.minecraft.world.item.AxeItem
import net.minecraft.world.item.DiggerItem
import net.minecraft.world.item.HoeItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.PickaxeItem
import net.minecraft.world.item.ShovelItem
import net.minecraft.world.level.block.state.BlockState
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
val Block.nms: BlockState
    get() = (this as CraftBlock).nms

fun Block.isToolRequired(): Boolean {
    val nmsBlock = this.nms
    return !nmsBlock.canBeReplaced() && nmsBlock.requiresCorrectToolForDrops()
}

val Block.hardness: Float
    get() = nms.destroySpeed

val ItemStack.nmsItem: Item
    get() = (this as CraftItemStack).handle.copy().item

val ItemStack.blocks: TagKey<net.minecraft.world.level.block.Block>?
    get() {
        val nmsItem = this.nmsItem
        if (nmsItem !is DiggerItem) return null

        val itemClass = nmsItem::class
        val blocks = itemClass.declaredMemberProperties.find { it.name == "a" }
        blocks?.isAccessible = true

        return try {
            blocks?.call(nmsItem) as? TagKey<net.minecraft.world.level.block.Block>?
        } catch (e: ClassCastException) {
            null
        }
    }

fun ItemStack.isToolTypeCorrect(blockType: BlockType): Boolean {
    val item = nmsItem
    if (item !is DiggerItem) return blockType.tier == 0

    return when (blockType.toolType) {
        ToolType.SHOVEL -> item is ShovelItem
        ToolType.PICKAXE -> item is PickaxeItem
        ToolType.AXE -> item is AxeItem
        ToolType.HOE -> item is HoeItem
    } || (item.tier.level == 0)
}

fun ItemStack.isTierEnough(blockType: BlockType): Boolean {
    val item = nmsItem
    if (item !is DiggerItem) return blockType.tier == 0

    val i = item.tier.level
    return (i >= blockType.tier)
}

fun ItemStack.isToolCorrect(blockType: BlockType): Boolean {
    return isToolTypeCorrect(blockType) && isTierEnough(blockType)
}

fun ItemStack.tierDestroyDamage(): Float {
    val item = nmsItem
    if (item !is DiggerItem) return 1f
    return item.tier.speed
}
