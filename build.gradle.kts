plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.papermc.paperweight.userdev") version "1.5.9"

    id("xyz.jpenilla.run-paper") version "2.2.3"

    id("com.modrinth.minotaur") version "2.8.7"

    `java-library`
}

base {
    archivesName.set(rootProject.name)
}

val mcVersion = providers.gradleProperty("mcVersion").get()

project.version = if (System.getenv("BUILD_NUMBER") != null) "${rootProject.version}-${System.getenv("BUILD_NUMBER")}" else rootProject.version

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://repo.triumphteam.dev/snapshots/")

    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.bundle)

    compileOnly(libs.triumphcmds)

    compileOnly(libs.worldguard)

    compileOnly(libs.configme)

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
        doFirst {
            delete(jarsDir)

            jarsDir.mkdirs()
        }

        // Makes it so reobfJar runs next.
        dependsOn(reobfJar)

        doLast {
            copy {
                from(project.layout.buildDirectory.file("libs/${rootProject.name}-${project.version}.jar"))
                into(jarsDir)
            }
        }
    }

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(mcVersion)
    }

    val isBeta: Boolean = providers.gradleProperty("isBeta").get().toBoolean()
    val type = if (isBeta) "Beta" else "Release"

    modrinth {
        versionType.set(type.lowercase())

        autoAddDependsOn.set(false)

        token.set(System.getenv("modrinth_token"))

        projectId.set(rootProject.name.lowercase())

        changelog.set(rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8))

        versionName.set("${rootProject.name} ${project.version}")

        versionNumber.set("${project.version}")

        uploadFile.set("$jarsDir/${rootProject.name}-${project.version}.jar")

        gameVersions.add(mcVersion)

        loaders.addAll("paper", "purpur")
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