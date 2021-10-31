object Coil {
    const val COIL = "io.coil-kt:coil:${Version.coil_version}"
}

object Retrofit{
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Version.retrofit_version}"
    const val GSON_ADAPTER = "com.squareup.retrofit2:converter-gson:${Version.retrofit_version}"
    const val RETROFIT_COROUTINES_ADAPTER = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Version.retrofit_coroutines_adapter_version}"
}

object Version{
    const val retrofit_version = "2.9.0"
    const val retrofit_coroutines_adapter_version = "0.9.2"
    const val coil_version = "1.3.1"
}