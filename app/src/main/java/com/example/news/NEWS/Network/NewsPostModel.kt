package com.example.news.NEWS.Network


import com.example.news.NEWS.LocalDatabase.Article
import com.google.gson.annotations.SerializedName

data class NewsPostModel(
    @SerializedName("articles")
    var articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)