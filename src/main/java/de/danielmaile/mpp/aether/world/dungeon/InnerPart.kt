package de.danielmaile.mpp.aether.world.dungeon

import de.danielmaile.mpp.util.converter.LocationConverter
import de.danielmaile.mpp.util.converter.VectorConverter
import jakarta.persistence.*
import org.bukkit.Location
import org.bukkit.util.Vector

@Entity
class InnerPart(
    @Enumerated(EnumType.STRING)
    val innerType: InnerPartType,

    @Column(nullable = false)
    @Convert(converter = VectorConverter::class)
    val relativePosition: Vector,

    @Transient
    private val outerPart: OuterPart,

    @Column(nullable = false)
    @Convert(converter = LocationConverter::class)
    val worldLocation: Location = outerPart.worldLocation.clone().add(relativePosition.x * 16, 0.0, relativePosition.z * 16),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null
)