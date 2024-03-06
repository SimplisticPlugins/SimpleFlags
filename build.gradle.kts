plugins {
    // The shadow jar plugin, It allows you to shade dependencies and create a fat jar, It supports userdev.
    // https://github.com/johnrengelman/shadow
    id("com.github.johnrengelman.shadow") version "8.1.1"

    // The userdev plugin from Paper.
    // https://github.com/PaperMC/paperweight
    id("io.papermc.paperweight.userdev") version "1.5.9"

    // The run-paper plugin by jpenilla.
    // https://github.com/jpenilla/run-task
    id("xyz.jpenilla.run-paper") version "2.2.3"

    // If you want kotlin, You should comment/ out or remove this.
    `java-library`

    // Only use this if you want Kotlin.
    // kotlin("jvm") version "1.9.10"
}

// This grabs "1.20.4" from gradle.properties.
val mcVersion = providers.gradleProperty("mcVersion").get()

repositories {
    // This is required for paperweight to function.
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Only use this if you want Kotlin.
    // compileOnly(libs.jetbrains.kotlin)

    // The paper equivalent of spigot buildtools where you get access to the server's nms or net.minecraft.server.
    // If an update comes out and you notice there something you want, Refresh your gradle cache.
    paperweight.paperDevBundle("$mcVersion-R0.1-SNAPSHOT")
}

// Only use this if you want Kotlin.
/*kotlin {
    jvmToolchain(17)
}*/

// If you want kotlin, You should comment/ out or remove this.
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
}

tasks {
    compileJava {
        // This makes sure everything uses UTF_8 encoding.
        options.encoding = Charsets.UTF_8.name()

        // Sets the release for compiling java.
        options.release.set(17)
    }

    // This is used when generating javadocs for your plugin.
    javadoc {
        // This makes sure everything uses UTF_8 encoding.
        options.encoding = Charsets.UTF_8.name()
    }

    // This is used for applying placeholders to your plugin.yml like below on line 115.
    processResources {
        // This makes sure everything uses UTF_8 encoding.
        filteringCharset = Charsets.UTF_8.name()
    }

    // If you want kotlin, You should comment/ out or remove this.
    /*compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
            javaParameters = true
        }
    }*/

    val jarsDir = File("$rootDir/jars")

    assemble {
        // Deletes if it exists to prevent old jars.
        if (jarsDir.exists()) jarsDir.delete()

        // Creates the directory.
        jarsDir.mkdirs()

        // Makes it so reobfJar runs next.
        dependsOn(reobfJar)
    }

    // This will relocate any dependencies that need to be relocated.
    // Uncomment this if you need to use it.
    /*shadowJar {
        listOf(
            // A common usage is to relocate bstats.
            "org.bstats"
        ).forEach {
            relocate(it, "libs.$it")
        }
    }*/

    // Allows you to run a dev server which builds your plugin and loads it.
    runServer {
        // Allows colors to format probably in console/terminal.
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        // Forces the server to run with UTF_8 encoding so special symbols work.
        defaultCharacterEncoding = Charsets.UTF_8.name()

        // Sets the minecraft version.
        minecraftVersion(mcVersion)
    }

    reobfJar {
        // Sets the output folder.
        outputJar.set(file("$jarsDir/${rootProject.name}-${rootProject.version}.jar"))
    }

    processResources {
        val props = mapOf(
            // Pulls the name of the project from settings.gradle.kts.
            "name" to rootProject.name,
            // Pulls the version of the project which is defined in gradle.properties.
            "version" to rootProject.version,
            // Pulls the group of the project which is defined in gradle.properties.
            "group" to rootProject.group,
            // Pulls the description of the project which is defined in gradle.properties.
            "description" to rootProject.description,
            // Pulls the api version of the project which is defined in gradle.properties.
            // apiVersion defines what versions of Paper your plugin will boot on.
            // If you wish to support multiple versions, look for "apiVersion" in gradle.properties in the root folder
            // and change it to 1.13 then you should be able to boot up.
            "apiVersion" to providers.gradleProperty("apiVersion").get(),
            // Pulls the authors of the project which is defined in gradle.properties.
            "authors" to providers.gradleProperty("authors").get(),
            // Pulls your website whether it be your github or your actual website defined in gradle.properties.
            "website" to providers.gradleProperty("website").get()
        )

        // Maps all the options to your plugin.yml on compilation, It replaces the variables with the correct data.
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}