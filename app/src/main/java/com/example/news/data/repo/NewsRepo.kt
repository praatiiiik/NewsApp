package com.example.news.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.data.local.News_DAO
import com.example.news.data.remote.model.Article
import com.example.news.data.remote.model.NewsPostModel
import com.example.news.util.Network.NewsRetrofit
import retrofit2.Response

class NewsRepo(private val dao: News_DAO) {

    val allData: LiveData<List<Article>> = dao.getNews()


    var newsResponse = MutableLiveData<Response<NewsPostModel>>()
    var newsApiService = NewsRetrofit()



    suspend fun insert(data : Article) {
        dao.upsert(data)
    }

    suspend fun updateNewsData(){
        newsResponse.postValue(newsApiService.getNews("in"))
    }

}