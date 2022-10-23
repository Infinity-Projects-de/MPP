package de.danielmaile.mpp.mob.listeners

import de.danielmaile.mpp.event.MPPMobSpawnEvent
import de.danielmaile.mpp.inst
import de.danielmaile.mpp.mob.MPPMob
import de.danielmaile.mpp.mob.getFromEntity
import de.danielmaile.mpp.mob.getLevelFromEntity
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class KingListener : MobListener(MPPMob.KING, MPPMob.KING_ELITE) {

    val kingSlaves = HashMap<LivingEntity, ArrayList<LivingEntity>>()

    val ownerNameSpace = NamespacedKey(inst(), "MPPKingSlaves")

    init {
        //Map existing slaves to their king onLoad
        for (world in inst().server.worlds)
            resolveKings(world.entities)

        //Spawn new slaves every 8s up to 5 per king
        object : BukkitRunnable() {
            override fun run() {
                val iterator = kingSlaves.iterator()
                while (iterator.hasNext()) {
                    val iteration = iterator.next()
                    val king = iteration.key
                    if (king.isDead) {
                        iterator.remove()
                        continue
                    }
                    val slaves = iteration.value
                    val slavesIterator = slaves.iterator()
                    while (slavesIterator.hasNext())
                        if (slavesIterator.next().isDead) slavesIterator.remove()

                    if (slaves.size < 5) {
                        val slave =
                            if (getFromEntity(king) == MPPMob.KING_ELITE)
                                MPPMob.SLAVE_ELITE.summon(
                                    king.location,
                                    getLevelFromEntity(king)
                                )
                            else
                                MPPMob.SLAVE.summon(king.location, getLevelFromEntity(king))
                        slaves.add(slave)
                        slave.persistentDataContainer.set(
                            ownerNameSpace,
                            PersistentDataType.STRING,
                            king.uniqueId.toString()
                        )
                    }
                }
            }
        }.runTaskTimer(inst(), 20 * 8, 20 * 8)
    }


    @EventHandler(priority = EventPriority.MONITOR)
    fun onMobSpawn(event: MPPMobSpawnEvent) {
        if (!shouldListen(event.mob)) return
        kingSlaves[event.entity] = ArrayList()
    }

    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        //world.entities only supplies loaded entities so this is also required
        resolveKings(event.chunk.entities.asList())
    }

    @EventHandler
    fun onDeath(event: EntityDeathEvent) {
        if (!shouldListen(event.entity)) return
        removeSlaves(event.entity)
    }

    //There is no despawn event to remove if this happens o.0
    private fun removeSlaves(king: LivingEntity) {
        kingSlaves[king]?.let { slaves ->
            slaves.forEach { slave ->
                slave.health = 0.0
            }
        }
        kingSlaves.remove(king)
    }

    private fun resolveKings(entities: List<Entity>) {
        for (entity in entities) {
            if (shouldListen(entity)) {
                if (!kingSlaves.contains(entity))
                    kingSlaves[entity as LivingEntity] = ArrayList() //safe 'as' check after shouldListen
            } else if (entity.persistentDataContainer.has(ownerNameSpace)) {
                val king = entity.world.getEntity(
                    UUID.fromString(
                        entity.persistentDataContainer.get(
                            ownerNameSpace,
                            PersistentDataType.STRING
                        )
                    )
                )
                if (king == null || king.isDead) {
                    entity.remove()
                    continue
                }
                if (!kingSlaves.contains(king))
                    kingSlaves[king as LivingEntity] = ArrayList()
                kingSlaves[king]?.add(entity as LivingEntity)
            }
        }
    }

}