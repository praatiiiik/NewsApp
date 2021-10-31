package com.example.news.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Networking {

    private val BASE_URL = "https://api.quotable.io/"

    /**
     * Preferred to hide this API key while pushing to the git.
     * We can add them in the Gradle files and add gradle to gitIgnore.
     */
    const val API_KEY = "5b9022d413b94e55857c3cb44f0ca2ad"

    /**
     * There are multiple features we can add here
     * OkHttp clients for Interceptors for logging,caching data and more
     *
     * Generally this is done in the DI Module packages
     */

    fun create(): NetworkService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}