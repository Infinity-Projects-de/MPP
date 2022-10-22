package de.danielmaile.mpp.mob.listeners

import de.danielmaile.mpp.mob.MPPMob
import org.bukkit.event.Listener

abstract class MobListener : Listener {
    abstract var mob: MPPMob
}