/*
 * This file is part of MPP.
 * Copyright (c) 2022 by it's authors. All rights reserved.
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

package de.danielmaile.mpp.data

import de.danielmaile.mpp.aether.world.dungeon.Dungeon
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.logInfo
import org.bukkit.Bukkit


class ObjectManager {

    private var dungeons = ArrayList<Dungeon>()

    init {
        loadDungeons()
    }

    /**
     * @return immutable list containing the dungeons
     */
    fun getDungeons(): List<Dungeon> {
        return dungeons.toList()
    }

    fun addNewDungeon(dungeon: Dungeon) {
        dungeons.add(dungeon)

        // add dungeon to database
        Bukkit.getScheduler().runTaskAsynchronously(inst(), Runnable {
            val transaction = inst().entityManager.transaction
            transaction.begin()
            inst().entityManager.persist(dungeon)
            transaction.commit()
        })
    }

    private fun loadDungeons() {
        dungeons.clear()

        Bukkit.getScheduler().runTaskAsynchronously(inst(), Runnable {
            val startTime = System.currentTimeMillis()

            val criteriaBuilder = inst().entityManager.criteriaBuilder
            val criteriaQuery = criteriaBuilder.createQuery(Dungeon::class.java)
            val rootEntry = criteriaQuery.from(Dungeon::class.java)
            val all = criteriaQuery.select(rootEntry)
            val allQuery = inst().entityManager.createQuery(all)

            dungeons = allQuery.resultList as ArrayList<Dungeon>
            logInfo("Loading dungeons from database took %dms".format(System.currentTimeMillis() - startTime))
        })
    }
}