plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.4.0")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("kotlin-stdlib", "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$embeddedKotlinVersion")
            library("apache-commons-compress", "org.apache.commons:commons-compress:1.21")
            library("okhttp", "com.squareup.okhttp3:okhttp:4.9.1")
            library("kotlin-reflect", "org.jetbrains.kotlin:kotlin-reflect:1.8.20")
            library("triumph-gui", "dev.triumphteam:triumph-gui:3.1.2")
            library("bstats", "org.bstats:bstats-bukkit:3.0.0")
            library("unirest-java", "com.konghq:unirest-java:3.13.13")
            library("junit", "org.junit.jupiter:junit-jupiter:5.9.3")
            library("mockk", "io.mockk:mockk:1.13.5")
            library("kotlin-test", "org.jetbrains.kotlin:kotlin-test:$embeddedKotlinVersion")

            bundle("plugin", listOf("kotlin-stdlib", "apache-commons-compress", "okhttp", "kotlin-reflect"))
            bundle("implementation", listOf("triumph-gui", "bstats"))
            bundle("compile", listOf("kotlin-reflect", "apache-commons-compress", "unirest-java", "okhttp"))
            bundle("test", listOf("junit", "mockk", "kotlin-test"))
        }
    }
}

rootProject.name = "MPP"
