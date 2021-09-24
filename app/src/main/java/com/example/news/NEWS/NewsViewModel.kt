package com.example.news.NEWS

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.news.NEWS.LocalDatabase.NewsDatabase
import com.example.news.NEWS.LocalDatabase.Article
import com.example.news.NEWS.Network.NewsPostModel
import com.example.news.NEWS.Network.NewsRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel( application: Application) : AndroidViewModel(application) {

     var newsResponse = MutableLiveData<Response<NewsPostModel>>()
     var newsApiService = NewsRetrofit()
     var newsData : Flow<List<Article>>
     lateinit var newsDataa : LiveData<List<Article>>
     val repositiory : NewsRepo

    init {
        val dao = NewsDatabase.getDatabase(application).getNews()
        repositiory = NewsRepo(dao)
        newsData = repositiory.allData
    }



    fun UpdateNewsData() = viewModelScope.launch(Dispatchers.IO) {
         repositiory.updateNewsData()

    }

    fun insertNews(note : Article) = viewModelScope.launch(Dispatchers.IO) {
        repositiory.insert(note)
    }


}

