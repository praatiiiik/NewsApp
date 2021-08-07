package com.example.news.NEWS

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.news.NEWS.LocalDatabase.NewsDatabase
import com.example.news.NEWS.LocalDatabase.Article
import com.example.news.NEWS.Network.NewsPostModel
import com.example.news.NEWS.Network.NewsRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel( application: Application) : AndroidViewModel(application) {

     var newsResponse = MutableLiveData<Response<NewsPostModel>>()
     var newsApiService = NewsRetrofit()
    val allNotess: LiveData<List<Article>>
    private val repositiory : NewsRepo

    init {
        val dao = NewsDatabase.getDatabase(application).getNews()
        repositiory = NewsRepo(dao)
        allNotess = repositiory.allData
        Log.d("imgvie",allNotess.value?.size.toString())/////////////////////////////////////////////////
    }


    fun UpdateNewsData() = viewModelScope.launch(Dispatchers.IO) {
         newsResponse.postValue(newsApiService.getNews("in"))
    }

    fun insertNews(note : Article) = viewModelScope.launch(Dispatchers.IO) {
        repositiory.insert(note)
    }


}

// newsDao.upsert(Article(postList.articles[2].author,postList.articles[2].content,postList.articles[2].description,postList.articles[2].publishedAt,postList.articles[2].title,postList.articles[2].url,postList.articles[2].urlToImage))