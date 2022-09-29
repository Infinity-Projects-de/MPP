package de.danielmaile.lama.aether.world

import de.danielmaile.lama.aether.inst
import de.danielmaile.lama.aether.world.generation.Builder.EnvironmentBuilder
import de.danielmaile.lama.aether.world.generation.Builder.GeneratorConfiguration
import de.danielmaile.lama.aether.world.generation.CustomWorldCreator
import org.bukkit.Material
import org.bukkit.World
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

const val aetherWorldName = "world_aether"

class WorldManager {

    val world: World
    val objectManager: ObjectManager

    init {

        //TODO create Aether world

        /*
        inst().saveResource("aether.json", false)

        val environmentBuilder = EnvironmentBuilder()
        environmentBuilder.setNatural(true)
        environmentBuilder.setPiglinSafe(false)
        environmentBuilder.setRespawnAnchorWorks(false)
        environmentBuilder.setBedWorks(true)
        environmentBuilder.setHasRaids(true)
        environmentBuilder.setHasSkylight(true)
        environmentBuilder.setHasCeiling(false)
        environmentBuilder.setCoordinateScale(1.0)
        environmentBuilder.setAmbientLight(0f)
        environmentBuilder.setLogicalHeight(256)
        environmentBuilder.setMinY(-512)
        environmentBuilder.setHeight(1024)

        val generatorConfiguration = GeneratorConfiguration()
        generatorConfiguration.setSeaLevel(-512);
        generatorConfiguration.setDisableMobGeneration(false);
        generatorConfiguration.setAquifersEnabled(false);
        generatorConfiguration.setOreVeinsEnabled(false);
        generatorConfiguration.setLegacyRandomSource(true);
        generatorConfiguration.setDefaultBlock(Material.STONE);
        generatorConfiguration.setDefaultFluid(Material.WATER);


        val customWorldCreator = CustomWorldCreator("test")
        customWorldCreator.setEnvironmentBuilder(environmentBuilder);
        customWorldCreator.setGeneratorConfiguration(generatorConfiguration);

        try {
            customWorldCreator.generatorSettings(
                String(
                    Files.readAllBytes(
                        Paths.get(
                            File(
                                inst().dataFolder,
                                "aether.json"
                            ).toURI()
                        )
                    )
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        world = customWorldCreator.createWorld()!!

        objectManager = ObjectManager()*/
    }
}