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

    //Return immutable list
    fun getDungeons(): List<Dungeon> {
        return dungeons.toList()
    }

    fun addNewDungeon(dungeon: Dungeon) {
        dungeons.add(dungeon)

        //Add dungeon to database
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