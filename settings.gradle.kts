pluginManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")

        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")

        gradlePluginPortal()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            library("worldguard", "com.sk89q.worldguard", "worldguard-bukkit").version("7.1.0-SNAPSHOT")

            library("triumphcmds", "dev.triumphteam", "triumph-cmd-bukkit").version("2.0.0-ALPHA-9")

            library("metrics", "org.bstats", "bstats-bukkit").version("3.0.2")

            library("configme", "ch.jalu", "configme").version("1.4.1")
        }
    }
}

rootProject.name = "SimpleFlags"