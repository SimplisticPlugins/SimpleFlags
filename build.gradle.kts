import java.io.ByteArrayOutputStream

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.papermc.paperweight.userdev") version "1.5.9"

    id("xyz.jpenilla.run-paper") version "2.2.3"

    id("com.modrinth.minotaur") version "2.8.7"

    `java-library`
}

val mcVersion = providers.gradleProperty("mcVersion").get()

project.version = if (System.getenv("BUILD_NUMBER") != null) "${rootProject.version}-${System.getenv("BUILD_NUMBER")}" else rootProject.version

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

// The commit id for the "main" branch prior to merging a pull request.
val start = "2ed1e0"

// The commit id BEFORE merging the pull request so before "Merge pull request #30"
val end = "5d42ed"

val commitLog = getGitHistory().joinToString(separator = "") { formatGitLog(it) }

fun getGitHistory(): List<String> {
    val output: String = ByteArrayOutputStream().use { outputStream ->
        project.exec {
            executable("git")
            args("log",  "$start..$end", "--format=format:%h %s")
            standardOutput = outputStream
        }

        outputStream.toString()
    }

    return output.split("\n")
}

fun formatGitLog(commitLog: String): String {
    val hash = commitLog.take(7)
    val message = commitLog.substring(8) // Get message after commit hash + space between
    return "[$hash](https://github.com/Crazy-Crew/${rootProject.name}/commit/$hash) $message<br>"
}

val changes = """
${rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8)} 
## Commits  
<details>  
<summary>Other</summary>

$commitLog
</details>
""".trimIndent()

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

    val directory = File("$rootDir/jars/${project.name.lowercase()}")

    val isBeta: Boolean = providers.gradleProperty("isBeta").get().toBoolean()
    val type = if (isBeta) "Beta" else "Release"

    modrinth {
        versionType.set(type.lowercase())

        autoAddDependsOn.set(false)

        token.set(System.getenv("modrinth_token"))

        projectId.set(rootProject.name.lowercase())

        changelog.set(changes)

        versionName.set("${rootProject.name} ${project.version}")

        versionNumber.set("${project.version}")

        uploadFile.set("$directory/${rootProject.name}-${project.version}.jar")

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