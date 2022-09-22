package de.danielmaile.lama.aether.item.funtion.magicwand

import de.danielmaile.lama.aether.inst
import de.danielmaile.lama.aether.util.getDataString
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.inventory.ItemStack

enum class Spell(val range: Int, val color: Color) {
    LLAMA_SPELL(20, Color.PURPLE),
    YEET_SPELL(20, Color.BLUE),
    SOUND_BEAM(20, Color.RED);

    val displayName: Component = inst().getLanguageManager().getComponent("items.MAGIC_WAND.spells.$name")

    fun next(): Spell {
        return values()[(this.ordinal + 1) % values().size]
    }

    companion object {

        @JvmStatic
        fun fromTag(itemStack: ItemStack): Spell? {
            return try {
                val itemTag = itemStack.getDataString(SELECTED_SPELL_TAG) ?: return null
                valueOf(itemTag)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}