plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.example.triviatrek"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.triviatrek"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
//        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.appcompat:appcompat:1.5.1")



    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

//    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

//    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
//
//    // Import the BoM for the Firebase platform
//    implementation (platform("com.google.firebase:firebase-bom:31.0.0"))
//
//    // Declare the dependency for the Firebase Authentication library
//    implementation ("com.google.firebase:firebase-auth-ktx")

    implementation (libs.lottie.compose.v641)


//    implementation(libs.mongodb.driver.kotlin.coroutine.v511)

//    implementation("org.jetbrains.kotlinx:kotlinx-coroutine-json:1.5.1")
//
//    implementation("org.mongodb:mongodb-driver-sync:4.8.1")
//    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.1.1")
//    implementation("org.mongodb:bson-kotlinx:5.1.1")
////    implementation("org.mongodb:bson-kotlinx:5.1.1")
////
//    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.3");



//    implementation("org.mongodb:bson-kotlinx:5.1.0")

}