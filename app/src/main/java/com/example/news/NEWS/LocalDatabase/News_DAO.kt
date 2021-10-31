package com.example.news.NEWS.LocalDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface News_DAO {

    //for daily news content
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(articles: List<Article>)

    @Query("select * from article order by id ASC")
    fun getNews() : LiveData<List<Article>>

    @Query("DELETE FROM article where id ")
    fun deleteNews()

    //for image
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveImage(image: forImage)

    @Query("select * from imageTable order by id ASC")
    fun gettmage() : LiveData<List<forImage>>

    @Query("DELETE FROM imageTable where id ")
    fun deleteImageData()


}