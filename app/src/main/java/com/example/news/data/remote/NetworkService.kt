package com.example.news.data.remote

import com.example.news.data.remote.model.NewsPostModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    /**
     * For more cleaner projects we implement this interface and then create the
     * instance of the implemented class
     * It helps us later in the testing of the API's
     */

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String
    ): Response<NewsPostModel>
}