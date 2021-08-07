package com.example.news.NEWS

import androidx.lifecycle.LiveData
import com.example.news.NEWS.LocalDatabase.News_DAO
import com.example.news.NEWS.LocalDatabase.Article

class NewsRepo(private val dao: News_DAO) {

    val allData: LiveData<List<Article>> = dao.getNews()


    suspend fun insert(data : Article) {
        dao.upsert(data)
    }

}