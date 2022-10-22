package de.danielmaile.mpp.mob.listeners

import de.danielmaile.mpp.mob.MPPMob
import org.bukkit.event.Listener

abstract class MobListener(private vararg val mobs: MPPMob) : Listener {

    fun shouldListen(mob: MPPMob?): Boolean {
        if (mob == null) return false
        return mobs.contains(mob)
    }
}