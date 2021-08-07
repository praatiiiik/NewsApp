package com.example.news.NEWS.LocalDatabase


import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "article")
data class Article(
    @ColumnInfo
    @SerializedName("content")
    val content: String? = null ,
    @ColumnInfo
    @SerializedName("description")
    val description: String? = null,
    @ColumnInfo
    @SerializedName("publishedAt")
    val publishedAt: String? = null,
    @ColumnInfo
    @SerializedName("title")
    val title: String? = null,
    @ColumnInfo
    @SerializedName("url")
    val url: String? = null,
    @ColumnInfo
    @SerializedName("urlToImage")
    val urlToImage: String? = null,
    @ColumnInfo
    val index: Int? ,
    @ColumnInfo
    val image : String?
){
    @PrimaryKey(autoGenerate = false)
    var id : Int? = index
}