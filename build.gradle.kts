// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.6.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.6.1" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.1" apply false

    // Never versions mess up markdown table generation
    id("org.openapi.generator") version "7.6.0" apply false
    
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10" apply false
    id("org.jetbrains.dokka") version "1.9.10"
}

tasks.dokkaGfmMultiModule {
    moduleName.set("Bunny Stream Android API")
    outputDirectory.set(file("docs"))
}