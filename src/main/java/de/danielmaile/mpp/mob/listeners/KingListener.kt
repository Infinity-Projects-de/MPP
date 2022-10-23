package de.danielmaile.mpp.mob.listeners

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.mob.MPPMob
import de.danielmaile.mpp.mob.getFromEntity
import de.danielmaile.mpp.mob.getLevelFromEntity
import org.bukkit.NamespacedKey
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.collections.ArrayList

class KingListener : MobListener(MPPMob.KING, MPPMob.KING_ELITE) {

    val kingSlavesListKey = NamespacedKey(inst(), "MPPKingSlaves")
    val slaveOwnerKey = NamespacedKey(inst(), "MPPSlaveOwner")

    init {
        //Spawn new slaves every 8s up to 5 per king
        object : BukkitRunnable() {
            override fun run() {
                for (world in inst().server.worlds)
                    for (king in world.entities) {
                        if (!shouldListen(king)) continue
                        val slaves = king.persistentDataContainer.get(kingSlavesListKey, PersistentDataType.STRING)
                        if (slaves == null || slaves.split('\n').size < 5) {
                            //Spawn slave
                            val slave =
                                if (getFromEntity(king as LivingEntity) == MPPMob.KING_ELITE)
                                    MPPMob.SLAVE_ELITE.summon(
                                        king.location,
                                        getLevelFromEntity(king)
                                    )
                                else
                                    MPPMob.SLAVE.summon(king.location, getLevelFromEntity(king))
                            //Add slave to kings slaves list
                            king.persistentDataContainer.set(
                                kingSlavesListKey,
                                PersistentDataType.STRING,
                                if (slaves != null) slaves + "\n" + slave.uniqueId.toString() else slave.uniqueId.toString()
                            )
                            //Link king to slave
                            slave.persistentDataContainer.set(
                                slaveOwnerKey,
                                PersistentDataType.STRING,
                                king.uniqueId.toString()
                            )
                        }
                    }
            }
        }.runTaskTimer(inst(), 20 * 8, 20 * 8)
    }

    @EventHandler
    fun onDeath(event: EntityDeathEvent) {
        if (shouldListen(event.entity)) {
            //It's a king
            val king = event.entity
            val slavesOfKing = king.persistentDataContainer.get(kingSlavesListKey, PersistentDataType.STRING) ?: return
            slavesOfKing.split('\n').forEach { slaveUUID ->
                king.world.getEntity(UUID.fromString(slaveUUID))?.let { slave ->
                    (slave as LivingEntity).health = 0.0
                }
            }
        } else {
            val slave = event.entity
            val slaveMPP = getFromEntity(slave)
            if (slaveMPP != MPPMob.SLAVE && slaveMPP != MPPMob.SLAVE_ELITE) return
            //It's a slave
            val kingUUID = slave.persistentDataContainer.get(slaveOwnerKey, PersistentDataType.STRING)
            val king = slave.world.getEntity(UUID.fromString(kingUUID)) ?: return
            val slavesOfKing = king.persistentDataContainer.get(kingSlavesListKey, PersistentDataType.STRING)
            if (slavesOfKing != null) {
                val slaveUUIDS = slavesOfKing.split('\n')
                val slaveUUIDSNew = ArrayList<String>()
                for (uuid in slaveUUIDS)
                    if (!uuid.equals(slave.uniqueId)) slaveUUIDSNew.add(uuid)
                king.persistentDataContainer.set(
                    kingSlavesListKey,
                    PersistentDataType.STRING,
                    slaveUUIDSNew.joinToString("\n")
                )
            }
        }
    }

}