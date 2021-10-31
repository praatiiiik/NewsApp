package com.example.news.data.remote

import com.example.news.data.remote.model.NewsPostModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String
    ): Response<NewsPostModel>
}