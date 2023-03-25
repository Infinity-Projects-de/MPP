import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

val javaVersion = 17
val minecraftVersion = "1.19.4"

group = "de.danielmaile.mpp"
version = "v0.2"

plugins {
    `java-library`
    `embedded-kotlin`
    id("io.papermc.paperweight.userdev") version "1.5.3"
    id("xyz.jpenilla.run-paper") version "2.0.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    id("de.undercouch.download") version "5.4.0"
}

bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
    main = "de.danielmaile.mpp.MPP"
    apiVersion = "1.19"
    authors = listOf("Daniel Maile and others")
    depend = listOf("ProtocolLib")
    libraries = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$embeddedKotlinVersion",
        "org.apache.commons:commons-compress:1.21",
        "com.konghq:unirest-java:3.13.13"
    )
    commands {
        register("mpp") {
            permission = "mpp.mpp"
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
}

repositories {
    mavenCentral()
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.mattstudios.me/artifactory/public/")
}

dependencies {
    paperweight.paperDevBundle("$minecraftVersion-R0.1-SNAPSHOT")
    implementation("dev.triumphteam:triumph-gui:3.1.2") {
        exclude(group = "net.kyori", module = "adventure-api")
        exclude(group = "net.kyori", module = "adventure-text-serializer-legacy")
        exclude(group = "net.kyori", module = "adventure-text-serializer-gson")
    }
    compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0-SNAPSHOT")
    compileOnly("org.apache.commons:commons-compress:1.21")
    compileOnly("com.konghq:unirest-java:3.13.13")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(javaVersion)
    }
    shadowJar {
        relocate("dev.triumphteam.gui", "de.danielmaile.libs.gui")
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    runServer {
        doFirst {
            download.run {
                src("https://ci.dmulloy2.net/job/ProtocolLib/lastSuccessfulBuild/artifact/target/ProtocolLib.jar")
                dest(buildDir)
                overwrite(false)
            }
        }
        jvmArgs("-Dcom.mojang.eula.agree=true")
        pluginJars(File(buildDir, "ProtocolLib.jar"))
        minecraftVersion(minecraftVersion)
    }
}
