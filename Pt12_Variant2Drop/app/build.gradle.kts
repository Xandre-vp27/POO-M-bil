plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pt12_variant2drop"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.pt12_variant2drop"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("libs")
        }
    }
}

val natives by configurations.creating {
    isTransitive = false
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.gdx)
    implementation(libs.gdx.backend.android)
    implementation(libs.gdx.freetype)

    // Configuración de nativos
    val gdxVersion = libs.versions.gdx.get()
    natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi-v7a")
    natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-arm64-v8a")
    natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86")
    natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86_64")
    natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-armeabi-v7a")
    natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-arm64-v8a")
    natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86")
    natives("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86_64")
}

tasks.register<Copy>("copyNatives") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val libsDir = file("libs")
    doFirst {
        delete(libsDir)
    }
    natives.files.forEach { jar ->
        val outputDirName = when {
            jar.name.contains("arm64-v8a") -> "arm64-v8a"
            jar.name.contains("armeabi-v7a") -> "armeabi-v7a"
            jar.name.contains("x86_64") -> "x86_64"
            jar.name.contains("x86") -> "x86"
            else -> null
        }
        if (outputDirName != null) {
            from(zipTree(jar)) {
                include("*.so")
                into(outputDirName)
            }
        }
    }
    into(libsDir)
}

tasks.whenTaskAdded {
    if (name == "preBuild") {
        dependsOn("copyNatives")
    }
}
