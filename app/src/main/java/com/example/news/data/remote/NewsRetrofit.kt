package com.example.news.data.remote

import com.example.news.data.remote.model.NewsPostModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "5b9022d413b94e55857c3cb44f0ca2ad"
interface NewsRetrofit {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String
    ): Response<NewsPostModel>

    companion object {
        operator fun invoke(): NewsRetrofit {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor).build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsRetrofit::class.java)
        }
    }
}




//https://newsapi.org/v2/top-headlines?country=in&apiKey=a64565dca3b14bdc865dc954ab1b6f12