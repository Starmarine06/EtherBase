plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Add the Google Services plugin
    id("com.google.gms.google-services")
    //kapt
    id("kotlin-kapt")
}

android {
    namespace = "com.starmarine06.etherbase"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.starmarine06.etherbase"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx")
    // For using Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    //Main Acc
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.android.material:material:1.9.0")
    // New, unified Supabase Kotlin client
    implementation(platform("io.github.jan-tennert.supabase:bom:3.2.3"))
    implementation("io.github.jan-tennert.supabase:auth-kt")

    // Coroutines for async operations (if not already present)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // For taking pictures
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")
    implementation("androidx.camera:camera-extensions:1.3.0")
    // Glide dependencies
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Use kapt for annotation processing in Kotlin
    kapt("com.github.bumptech.glide:compiler:4.16.0")
    implementation("io.ktor:ktor-client-Android:KTOR_VERSION")
}