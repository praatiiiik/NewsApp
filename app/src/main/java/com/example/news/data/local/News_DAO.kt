package com.example.news.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.data.remote.model.Article

@Dao
interface News_DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Query("select * from article order by id ASC")
    fun getNews() : LiveData<List<Article>>

}