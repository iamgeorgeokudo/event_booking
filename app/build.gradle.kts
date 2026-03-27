plugins {
    alias(libs.plugins.android.application)
    // If you use Kotlin for your Activities, ensure this is here:
    // id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.event_booking"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.event_booking"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // This is the CRITICAL block for ActivityEventsBinding to generate
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
}

dependencies {
    // UI & Core
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.recyclerview:recyclerview:1.3.1")

// Image Loading
    implementation("com.github.bumptech.glide:glide:4.16.0") // Added "com." at the start
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    // Networking (Retrofit + GSON)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Circular Images (Optional, used for profiles)
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Lifecycle (ViewModel/LiveData)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.google.zxing:core:3.5.3")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}