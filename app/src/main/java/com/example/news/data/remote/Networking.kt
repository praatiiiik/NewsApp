package com.example.news.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Networking {

    private val BASE_URL = "https://api.quotable.io/"

    const val API_KEY = "5b9022d413b94e55857c3cb44f0ca2ad"

    fun create(): NetworkService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}