import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    kotlin("android")
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

android {
    namespace = "com.akari.uicomponents"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 28

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget("11")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

publishing {
    // 1. Define la publicación
    publications {
        create<MavenPublication>("release") {
            // Especifica los componentes que quieres publicar.
            // 'release' es común para librerías.
            afterEvaluate {
                from(components["release"])
            }

            // 2. Define la identificación de la librería (las coordenadas)
            groupId = "com.akari"        // Tu dominio/empresa o un identificador único.
            artifactId = "uicomponents"  // El nombre de la librería, debe ser único.
            version = project.findProperty("libVersion")!!.toString()    // La versión actual. ¡Incrementa esto con cada cambio!
        }
    }

    // 3. Define los repositorios de destino
    repositories {
        // Añade el repositorio local de Maven
        mavenLocal()
    }
}