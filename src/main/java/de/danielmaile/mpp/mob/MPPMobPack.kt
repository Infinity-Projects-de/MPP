package de.danielmaile.mpp.mob

import org.bukkit.Location
import kotlin.random.Random

class MPPMobPack(
    private val size: Int,
    private val baseLevel: Long,
    private val levelVariance: Double,
) {

    fun summon(location: Location) {
        for(i in 1 ..size) {
            getRandomMob(null, getRandomMobLevel()).summon(location)
        }
    }

    //Returns a random mob level based on baseLevel and levelVariance
    //Returned value is always >= 1
    private fun getRandomMobLevel(): Long {
        val variance = (baseLevel * levelVariance).toLong()
        val minLevel = (baseLevel - variance).coerceAtLeast(1)
        val maxLevel = baseLevel + variance

        return Random.nextLong(minLevel, maxLevel + 1)
    }
}