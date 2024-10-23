plugins {
    alias(libs.plugins.runPaper)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.shadow)

    `java-library`
}

rootProject.group = "com.ryderbelserion.simpleflags"
rootProject.version = "1.0.1"
rootProject.description = "A plugin that adds simple worldguard flags."

repositories {
    maven("https://repo.papermc.io/repository/maven-public")

    maven("https://repo.crazycrew.us/libraries")

    maven("https://maven.enginehub.org/repo")
}

dependencies {
    compileOnly(libs.vital.paper)

    compileOnly(libs.worldguard)

    compileOnly(libs.paper)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of("21"))
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    assemble {
        dependsOn(shadowJar)

        doLast {
            copy {
                from(shadowJar.get())
                into(rootProject.projectDir.resolve("jars"))
            }
        }
    }

    shadowJar {
        archiveClassifier.set("")
    }

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }

    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))

        projectId.set(rootProject.name)

        versionType.set("release")

        versionName.set("${rootProject.name} ${rootProject.version}")
        versionNumber.set(rootProject.version as String)

        changelog.set(rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8))

        uploadFile.set(shadowJar.get())

        gameVersions.set(listOf(libs.versions.minecraft.get()))

        loaders.addAll(listOf("purpur", "paper", "folia"))

        syncBodyFrom.set(rootProject.file("README.md").readText(Charsets.UTF_8))

        autoAddDependsOn.set(false)
        detectLoaders.set(false)
    }

    processResources {
        val props = mapOf(
            "name" to rootProject.name,
            "version" to rootProject.version,
            "group" to rootProject.group,
            "description" to rootProject.description,
            "apiVersion" to libs.versions.minecraft.get()
        )

        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}