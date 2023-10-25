plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt")
    id("org.openapi.generator")
    id("org.jetbrains.dokka")
}

android {

    buildFeatures {
        buildConfig = true
    }

    sourceSets["main"].java.srcDirs("$buildDir/generated/api")

    namespace = "net.bunnystream.androidsdk"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "TUS_UPLOAD_ENDPOINT", "\"https://video.bunnycdn.com/tusupload\"")
        buildConfigField("String", "BASE_API", "\"https://video.bunnycdn.com\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("io.ktor:ktor-client-okhttp:2.3.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
    implementation("io.ktor:ktor-client-logging-jvm:2.3.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")

    implementation("io.arrow-kt:arrow-core:1.2.0")

    implementation("io.tus.java.client:tus-java-client:0.5.0")
    implementation("io.tus.android.client:tus-android-client:0.1.11")

    dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:1.9.10")
}

detekt {
    config.from("$rootDir/sdk/detekt.yml")
}

val specs = File("$rootDir/sdk/openapi").walk().map {
    Pair(it.name, it.path)
}.toMap().filter { it.key != "openapi" }

specs.forEach {
    tasks.create("openApiGenerate-${it.key}", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
        generatorName.set("kotlin")
        inputSpec.set(it.value)
        outputDir.set("$buildDir/generated/api")
        apiPackage.set("net.bunnystream.androidsdk.api")

        configOptions.set(mapOf(
            "dateLibrary" to "string",
            "serializationLibrary" to "gson"
        ))
    }
}

tasks.register("openApiGenerateAll") {
    dependsOn(specs.map { "openApiGenerate-${it.key}" })
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("openApiGenerateAll")
}

tasks.dokkaHtml.configure {
    outputDirectory.set(file("../docs"))
    moduleName.set("Bunny Stream Android SDK")
    dokkaSourceSets {
        configureEach {
            displayName.set("SDK")
            suppressGeneratedFiles.set(false)
            perPackageOption {
                matchingRegex.set("kotlin($|\\.).*")
                skipDeprecated.set(false)
                reportUndocumented.set(true)
                includeNonPublic.set(false)
            }

            // Suppress a package
            perPackageOption {
                matchingRegex.set(".*.internal.*")
                suppress.set(true)
            }
        }
    }
}