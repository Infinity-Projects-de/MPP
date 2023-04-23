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

package de.danielmaile.mpp.item

import de.danielmaile.mpp.item.items.Armors
import de.danielmaile.mpp.item.items.Blocks
import de.danielmaile.mpp.item.items.Ingredients
import de.danielmaile.mpp.item.items.Tools
import de.danielmaile.mpp.registerItems
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ItemRegistryTest {
    @BeforeTest
    fun clearItemsRegistry() {
        ItemRegistry.clear()
    }

    @Test
    fun `Registering blocks should register all blocks`() {
        ItemRegistry.registerItems(Blocks.values())
        var containsAll = true
        for (item in Blocks.values()) {
            if (!ItemRegistry.getAllItems().contains(item)) containsAll = false
        }
        assertTrue(containsAll)
    }

    @Test
    fun `Registering armors should register all armors`() {
        ItemRegistry.registerItems(Armors.values())
        var containsAll = true
        for (item in Armors.values()) {
            if (!ItemRegistry.getAllItems().contains(item)) containsAll = false
        }
        assertTrue(containsAll)
    }

    @Test
    fun `Registering ingredients should register all ingredients`() {
        ItemRegistry.registerItems(Ingredients.values())
        var containsAll = true
        for (item in Ingredients.values()) {
            if (!ItemRegistry.getAllItems().contains(item)) containsAll = false
        }
        assertTrue(containsAll)
    }

    @Test
    fun `Registering tools should register all tools`() {
        ItemRegistry.registerItems(Tools.values())
        var containsAll = true
        for (item in Tools.values()) {
            if (!ItemRegistry.getAllItems().contains(item)) containsAll = false
        }
        assertTrue(containsAll)
    }

    @Test
    fun `There should not be any repeated model ID`() {
        registerItems()
        val modelIDs = mutableListOf<Int>()
        var repeats = false
        for (item in ItemRegistry.getAllItems()) {
            if (modelIDs.contains(item.modelID)) {
                repeats = true
                break
            }
            modelIDs.add(item.modelID)
        }
        assertFalse(repeats)
    }

    @Test
    fun `Clearing should clear the whole registry`() {
        ItemRegistry.registerItems(Blocks.values())
        ItemRegistry.clear()
        assertEquals(0, ItemRegistry.getAllItems().size)
    }

    @Test
    fun `Registering all items should register all items`() {
        registerItems()
        val items = Blocks.values().size + Tools.values().size + Armors.values().size + Ingredients.values().size
        assertEquals(items, ItemRegistry.getAllItems().size)
    }

    @Test
    fun `Getting an unregistered item by id should throw a NoSuchElementException`() {
        assertThrows<NoSuchElementException> {
            ItemRegistry.getItemByID("abc:idontexist")
        }
    }

    @Test
    fun `Getting a registered item by id should not throw a NoSuchElementException`() {
        ItemRegistry.registerItem(Blocks.AETHER_STONE)
        assertDoesNotThrow {
            ItemRegistry.getItemByID("mpp:aether_stone")
        }
    }

    @Test
    fun `Getting an unregistered item ID should throw a NoSuchElementException`() {
        assertThrows<NoSuchElementException> {
            ItemRegistry.getItemID(Blocks.AETHER_STONE)
        }
    }

    @Test
    fun `Registering a single Item should register the Item as is`() {
        ItemRegistry.registerItem(Blocks.AETHER_STONE)
        assertEquals("mpp:aether_stone", ItemRegistry.getItemID(Blocks.AETHER_STONE))
    }

    @Test
    fun `Registering a single Item with another ID should register the Item with that ID`() {
        ItemRegistry.registerItem("mpp:not_an_aether_stone", Blocks.AETHER_STONE)
        assertEquals("mpp:not_an_aether_stone", ItemRegistry.getItemID(Blocks.AETHER_STONE))
    }
}
