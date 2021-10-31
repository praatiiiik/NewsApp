package com.example.news.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * Using Moshi is preferred instead of GSON,
 * we can create model class through `kotlin data class` plugins
 */

data class NewsPostModel(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)