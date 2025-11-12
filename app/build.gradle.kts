plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
}


android {
    namespace = "com.example.recipeworld"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.recipeworld"
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
}

dependencies {

    // AndroidX core
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.activity:activity-ktx:1.7.2")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0") {
        exclude(group = "xmlpull", module = "xmlpull")
    }
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") {
        exclude(group = "xmlpull", module = "xmlpull")
    }
    implementation("com.google.code.gson:gson:2.11.0") {
        exclude(group = "xmlpull", module = "xmlpull")
    }

    implementation("com.github.bumptech.glide:glide:4.16.0") {
        exclude(group = "xmlpull", module = "xmlpull")
    }
    implementation(libs.annotation.jvm)
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    implementation("androidx.room:room-runtime:2.6.1") {
        exclude(group = "xmlpull", module = "xmlpull")
    }
    implementation("androidx.room:room-ktx:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    implementation("androidx.room:room-ktx:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.annotation:annotation:1.6.0")
}