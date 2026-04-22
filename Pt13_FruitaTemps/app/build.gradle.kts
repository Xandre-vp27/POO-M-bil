plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pt13_fruitatemps"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.pt13_fruitatemps"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

configurations {
    create("natives")
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    
    val gdxVersion = "1.12.1"
    
    // Core
    implementation("com.badlogicgames.gdx:gdx:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-backend-android:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-freetype:$gdxVersion")
    
    // Natives
    "natives"("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi-v7a")
    "natives"("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-arm64-v8a")
    "natives"("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86")
    "natives"("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86_64")
    
    "natives"("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-armeabi-v7a")
    "natives"("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-arm64-v8a")
    "natives"("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86")
    "natives"("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86_64")
}

tasks.register<Copy>("copyNatives") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    doFirst {
        delete("src/main/jniLibs")
    }
    configurations.getByName("natives").forEach {
        val name = it.name
        if (name.contains("natives-armeabi-v7a")) {
            from(zipTree(it)) { include("*.so"); into("armeabi-v7a") }
        }
        if (name.contains("natives-arm64-v8a")) {
            from(zipTree(it)) { include("*.so"); into("arm64-v8a") }
        }
        if (name.contains("natives-x86_64")) {
            from(zipTree(it)) { include("*.so"); into("x86_64") }
        }
        if (name.contains("natives-x86")) {
            from(zipTree(it)) { include("*.so"); into("x86") }
        }
    }
    into("src/main/jniLibs")
}

tasks.named("preBuild") {
    dependsOn("copyNatives")
}