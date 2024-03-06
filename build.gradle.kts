plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.papermc.paperweight.userdev") version "1.5.9"

    id("xyz.jpenilla.run-paper") version "2.2.3"

    `java-library`
}

val mcVersion = providers.gradleProperty("mcVersion").get()

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly(libs.worldguard)

    paperweight.paperDevBundle("$mcVersion-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

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

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(mcVersion)
    }

    reobfJar {
        outputJar.set(file("$jarsDir/${rootProject.name}-${rootProject.version}.jar"))
    }

    processResources {
        val props = mapOf(
            "name" to rootProject.name,
            "version" to rootProject.version,
            "group" to rootProject.group,
            "description" to rootProject.description,
            "apiVersion" to providers.gradleProperty("apiVersion").get()
        )

        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}