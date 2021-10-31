object Coil {
    const val COIL = "io.coil-kt:coil:${Version.coil_version}"
}

object Retrofit {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Version.retrofit_version}"
    const val GSON_ADAPTER = "com.squareup.retrofit2:converter-gson:${Version.retrofit_version}"
    const val RETROFIT_COROUTINES_ADAPTER =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Version.retrofit_coroutines_adapter_version}"
}

object Hilt {
    const val hilt_gradle =
        "com.google.dagger:hilt-android-gradle-plugin:${Version.hilt_dagger_version}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hilt_dagger_version}"
    const val daggerCompiler =
        "com.google.dagger:hilt-android-compiler:${Version.hilt_dagger_version}"
    const val hiltViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Version.hilt_android_version}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Version.hilt_android_version}"
}

object Version {
    const val retrofit_version = "2.9.0"
    const val retrofit_coroutines_adapter_version = "0.9.2"
    const val coil_version = "1.3.1"
    const val hilt_dagger_version = "2.38.1"
    const val hilt_android_version = "1.0.0-alpha03"
}