package com.example.news.NEWS

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.NEWS.LocalDatabase.News_DAO
import com.example.news.NEWS.LocalDatabase.Article
import com.example.news.NEWS.Network.NewsPostModel
import com.example.news.NEWS.Network.NewsRetrofit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
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