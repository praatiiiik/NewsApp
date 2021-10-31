package com.example.news.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.data.local.NewsDao
import com.example.news.data.remote.NewsRetrofit
import com.example.news.data.remote.model.Article
import com.example.news.data.remote.model.NewsPostModel
import retrofit2.Response

class NewsRepo(private val dao: NewsDao) {

    /**
     * Those observers are not needed here
     * the suspend functions and data from API and Room can send continuous flows
     * And the observers are most suited at the View model class
     */

    val allData: LiveData<List<Article>> = dao.getNews()

    var newsResponse = MutableLiveData<Response<NewsPostModel>>()
    var newsApiService = NewsRetrofit()

    suspend fun insert(data : Article) {
        dao.insert(data)
    }

    suspend fun updateNewsData(){
        newsResponse.postValue(newsApiService.getNews("in"))
    }

}