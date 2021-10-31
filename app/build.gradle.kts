plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.example.news"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode(1)
        versionName("1.0")

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.20")
    implementation("androidx.appcompat:appcompat:1.3.1")

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    // Room
    implementation("androidx.room:room-runtime:2.4.0-beta01")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    kapt("androidx.room:room-compiler:2.3.0")

    // Gson
    implementation("com.google.code.gson:gson:2.8.7")

    // Kotlin Android Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")

    // Retrofit
    implementation(Retrofit.RETROFIT)
    implementation(Retrofit.GSON_ADAPTER)
    implementation(Retrofit.RETROFIT_COROUTINES_ADAPTER)

    // Hilt + Dagger
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltViewModel)
    kapt(Hilt.daggerCompiler)
    kapt(Hilt.hiltCompiler)

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

//    // Glide
//    implementation 'com.github.bumptech.glide:glide:4.12.0'
//    kapt 'com.github.bumptech.glide:compiler:4.8.0'

    //Coil
    implementation(Coil.COIL)


    //recyclerView
//    implementation files ("recyclerview")
    implementation("androidx.recyclerview:recyclerview:1.2.1")


    // New Material Design
    implementation("com.google.android.material:material:1.4.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.3.0")
    kapt("android.arch.persistence.room:compiler:1.1.1")
}