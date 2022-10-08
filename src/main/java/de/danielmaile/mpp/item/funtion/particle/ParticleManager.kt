package de.danielmaile.mpp.item.funtion.particle

import de.danielmaile.mpp.inst
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class ParticleManager {
    enum class ParticleType {
        VALKYRE_RING, VALKYRE_WINGS
    }

    private val playerParticleList: HashMap<Player, ArrayList<ParticleType>> = HashMap()

    init {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst(), {
            for ((player, value) in playerParticleList) {
                for (type in value) {
                    when (type) {
                        ParticleType.VALKYRE_RING -> {
                            val location = player.location.clone().add(0.0, 2.2, 0.0)
                            spawnCircle(location, 0.5, 30, Color.YELLOW)
                        }
                        ParticleType.VALKYRE_WINGS -> {
                            val location = player.location.clone().add(0.0, 1.0, 0.0)
                            spawnWings(location, Color.YELLOW)
                        }
                    }
                }
            }
        }, 5L, 5L)
    }

    private fun spawnWings(location: Location, color: Color) {
        var i = 0.0
        while (i < Math.PI * 2) {
            val offset = (Math.E.pow(cos(i)) - 2 * cos(i * 4) - sin(i / 12).pow(5.0)) / 2
            val x = sin(i) * offset
            val y = cos(i) * offset
            val vector = Vector(x, y, -0.3).rotateAroundY(-Math.toRadians(location.yaw.toDouble()))
            spawnParticle(location.clone().add(vector.x, vector.y, vector.z), color)
            i += Math.PI / 48
        }
    }

    private fun spawnCircle(center: Location, radius: Double, amount: Int, color: Color) {
        var i = 0
        while (i < 360) {
            val angle = i * Math.PI / 180
            val x = radius * cos(angle)
            val z = radius * sin(angle)
            val location = Location(center.world, center.x + x, center.y, center.z + z)
            spawnParticle(location, color)
            i += 360 / amount
        }
    }

    private fun spawnParticle(location: Location, color: Color) {
        val dustOptions = Particle.DustOptions(color, 1f)
        location.world.spawnParticle(Particle.REDSTONE, location, 0, dustOptions)
    }

    fun removeParticleType(player: Player, type: ParticleType) {
        val particles = playerParticleList[player]
        particles?.remove(type)
    }

    fun addParticleType(player: Player, type: ParticleType) {
        val particles = playerParticleList.computeIfAbsent(player) { ArrayList() }
        if (!particles.contains(type)) {
            particles.add(type)
        }
    }

    fun removePlayer(player: Player) {
        playerParticleList.remove(player)
    }
}