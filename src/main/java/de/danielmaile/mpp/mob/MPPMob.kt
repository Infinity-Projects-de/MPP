package de.danielmaile.mpp.mob

import de.danielmaile.mpp.inst
import de.danielmaile.mpp.util.abbreviateNumber
import de.danielmaile.mpp.util.setUnbreakable
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Ageable
import org.bukkit.entity.Breedable
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.math.ceil
import kotlin.random.Random

enum class MPPMob(
    val entityType: EntityType,
    private val baby: Boolean,
    private val baseHPMultiplier: Float,
    private val hpMultiplier: Float,
    private val speedMultiplier: Float,
    private val baseAttackDamageMultiplier: Float,
    private val attackDamageMultiplier: Float,
    private val knockbackResistance: Float,
    private val itemInMainHand: ItemStack?,
    private val itemInOffHand: ItemStack?,
    private val helmet: ItemStack?,
    private val chestplate: ItemStack?,
    private val leggings: ItemStack?,
    private val boots: ItemStack?,
    private val potionEffects: Array<PotionEffect>?
) {
    //Vanilla replacements
    ZOMBIE(EntityType.ZOMBIE),
    SKELETON(EntityType.SKELETON),
    SPIDER(EntityType.SPIDER),
    CREEPER(EntityType.CREEPER),
    ENDERMAN(EntityType.ENDERMAN),

    //Custom mobs
    TANK(
        EntityType.ZOMBIE, false,
        2.8f, 0.02f, 0.8f, 1.6f, 0.01f, 10f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    TANK_ELITE(
        EntityType.ZOMBIE, false,
        3.2f, 0.025f, 1.0f, 2f, 0.015f, 10f,
        null, null,
        null, getEliteChestPlate(),
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    SCOUT(
        EntityType.ZOMBIE, false,
        1.2f, 0.015f, 1.3f, 1.3f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    SCOUT_ELITE(
        EntityType.ZOMBIE, false,
        1.4f, 0.02f, 1.5f, 1.4f, 0.015f, 0f,
        null, null,
        null, getEliteChestPlate(),
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    PLAGUE(
        EntityType.ZOMBIE, false,
        0.6f, 0.015f, 1.0f, 1.0f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    PLAGUE_ELITE(
        EntityType.ZOMBIE, false,
        1.0f, 0.02f, 1.0f, 1.2f, 0.015f, 0f,
        null, null,
        null, getEliteChestPlate(),
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    MOTHER(
        EntityType.ZOMBIE, false,
        0.8f, 0.015f, 1.0f, 1.2f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    MOTHER_ELITE(
        EntityType.ZOMBIE, false,
        1.0f, 0.02f, 1.0f, 1.3f, 0.015f, 0f,
        null, null,
        null, getEliteChestPlate(),
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    HITMAN(
        EntityType.ZOMBIE, false,
        0.65f, 0.015f, 1.1f, 0.8f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    HITMAN_ELITE(
        EntityType.ZOMBIE, false,
        0.8f, 0.02f, 1.1f, 1.0f, 0.015f, 0f,
        null, null,
        null, getEliteChestPlate(),
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    CHILD(
        EntityType.ZOMBIE, true,
        0.5f, 0.015f, 1.3f, 1.0f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    CHILD_ELITE(
        EntityType.ZOMBIE, true,
        0.8f, 0.02f, 1.6f, 1.2f, 0.015f, 0f,
        null, null,
        null, getEliteChestPlate(),
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    SNIPER(
        EntityType.SKELETON, false,
        0.5f, 0.015f, 1.0f, 2.3f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    SNIPER_ELITE(
        EntityType.SKELETON, false,
        0.8f, 0.02f, 1.1f, 2.8f, 0.015f, 0f,
        null, null,
        null, getEliteChestPlate(),
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    RIFT(
        EntityType.SKELETON, false,
        1.0f, 0.015f, 1.2f, 1.5f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    RIFT_ELITE(
        EntityType.SKELETON, false,
        1.2f, 0.02f, 1.35f, 1.8f, 0.015f, 0f,
        null, null,
        null, getEliteChestPlate(),
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    KING(
        EntityType.SKELETON, false,
        2.2f, 0.015f, 0.8f, 1.2f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    KING_ELITE(
        EntityType.SKELETON, false,
        2.5f, 0.02f, 1.0f, 1.5f, 0.015f, 0f,
        null, null,
        null, getEliteChestPlate(),
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    SLAVE(
        EntityType.SKELETON, false,
        0.5f, 0.015f, 1.2f, 0.8f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    SLAVE_ELITE(
        EntityType.SKELETON, false,
        0.8f, 0.02f, 1.35f, 1.0f, 0.015f, 0f,
        null, null,
        null, getEliteChestPlate(),
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    HEALER(
        EntityType.WITCH, false,
        0.4f, 0.015f, 1.2f, 1.2f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    HEALER_ELITE(
        EntityType.WITCH, false,
        0.65f, 0.02f, 1.3f, 1.35f, 0.015f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    NECROMANCER(
        EntityType.WITCH, false,
        1.5f, 0.015f, 0.7f, 1.3f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    ),
    NECROMANCER_ELITE(
        EntityType.WITCH, false,
        1.8f, 0.02f, 0.7f, 1.5f, 0.015f, 0f,
        null, null,
        null, null,
        null, null,
        arrayOf(getFireResistanceEffect())
    );

    constructor(entityType: EntityType) : this(
        entityType, false,
        1f, 0.01f, 1f, 1f, 0.01f, 0f,
        null, null,
        null, null,
        null, null,
        null
    )

    fun summon(location: Location, level: Long) {
        val entity =
            location.world.spawnEntity(location, entityType, CreatureSpawnEvent.SpawnReason.CUSTOM) as LivingEntity

        //Set persistent data
        entity.persistentDataContainer.set(getMPPMobNameKey(), PersistentDataType.STRING, name)
        entity.persistentDataContainer.set(getMPPMobLevelKey(), PersistentDataType.LONG, level)

        //Set health based on level
        val baseHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue * baseHPMultiplier
        val maxHealth = baseHealth * (1 + (level * hpMultiplier))
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = maxHealth
        entity.health = maxHealth

        //Set movement speed
        val movementSpeed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)!!.baseValue * speedMultiplier
        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)!!.baseValue = movementSpeed

        //Set attack damage based on level
        val baseDamage = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)!!.baseValue * baseAttackDamageMultiplier
        val attackDamage = baseDamage * (1 + (level * attackDamageMultiplier))
        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)!!.baseValue = attackDamage

        //Knockback resistance
        entity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)!!.baseValue = knockbackResistance.toDouble()

        //Set equipment
        val inventory = entity.equipment!!
        inventory.setItemInMainHand(itemInMainHand)
        inventory.setItemInOffHand(itemInOffHand)
        inventory.helmet = helmet
        inventory.chestplate = chestplate
        inventory.leggings = leggings
        inventory.boots = boots

        //Disable equipment drops
        inventory.itemInMainHandDropChance = 0f
        inventory.itemInOffHandDropChance = 0f
        inventory.helmetDropChance = 0f
        inventory.chestplateDropChance = 0f
        inventory.leggingsDropChance = 0f
        inventory.bootsDropChance = 0f

        //Apply potion effects
        potionEffects?.let {
            for (potionEffect in potionEffects) {
                entity.addPotionEffect(potionEffect)
            }
        }

        //Set age of mob
        if (baby) {
            if (entity is Breedable) {
                entity.setBaby()
                entity.ageLock = true
            } else if (entity is Ageable) {
                entity.setBaby()
            }
        }

        //Update display name
        updateDisplayName(entity)
    }
}

fun getFromEntity(entity: LivingEntity): MPPMob? {
    val type = entity.persistentDataContainer.get(getMPPMobNameKey(), PersistentDataType.STRING) ?: return null
    return MPPMob.valueOf(type)
}

fun getLevelFromEntity(entity: LivingEntity): Long {
    return entity.persistentDataContainer.get(getMPPMobLevelKey(), PersistentDataType.LONG)!!
}

fun getRandomMob(entityType: EntityType?): MPPMob {
    val mobList = if (entityType != null) {
        MPPMob.values().filter { it.entityType == entityType }
    } else {
        MPPMob.values().toList()
    }

    return mobList[Random.nextInt(MPPMob.values().size)]
}

fun updateDisplayName(entity: LivingEntity) {
    if (!entity.persistentDataContainer.has(getMPPMobNameKey())) return

    try {
        val mppMob = getFromEntity(entity) ?: return
        val level = getLevelFromEntity(entity)
        val health = ceil(entity.health / 2)

        val tagResolver = TagResolver.resolver(
            Placeholder.parsed("level", level.abbreviateNumber()),
            Placeholder.parsed("health", health.toLong().abbreviateNumber())
        )

        val displayName = inst().getLanguageManager().getComponent(
            "mobs.${mppMob.name}.name",
            tagResolver
        )

        entity.customName(displayName)
    } catch (exception: IllegalArgumentException) {
        return
    }
}

fun getMPPMobNameKey(): NamespacedKey {
    return NamespacedKey(inst(), "MPPMobName")
}

fun getMPPMobLevelKey(): NamespacedKey {
    return NamespacedKey(inst(), "MPPMobLevel")
}

private fun getFireResistanceEffect(): PotionEffect {
    return PotionEffect(PotionEffectType.FIRE_RESISTANCE, Int.MAX_VALUE, 1)
}

private fun getEliteChestPlate(): ItemStack {
    return ItemStack(Material.GOLDEN_CHESTPLATE).apply {
        this.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
        this.setUnbreakable()
    }
}