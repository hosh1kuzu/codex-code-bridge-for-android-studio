import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    kotlin("jvm") version "2.1.10"
    id("org.jetbrains.intellij.platform") version "2.2.1"
}

group = "io.github.hosh1kuzu"
version = "0.4.1"

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    intellijPlatform {
        local("/Applications/Android Studio.app/Contents")
        testFramework(TestFrameworkType.Platform)
    }
}

kotlin {
    jvmToolchain(21)
}

intellijPlatform {
    pluginConfiguration {
        name = "Codex Code Bridge for Android Studio"
        version = project.version.toString()
        description = """
            Adds a Codex copy-link action to Android Studio's floating code toolbar.
        """.trimIndent()

        ideaVersion {
            sinceBuild = "242"
            untilBuild = provider { null }
        }
    }
}
