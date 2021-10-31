package com.example.news.presentation.main

import android.app.Application
import androidx.lifecycle.*
import com.example.news.data.local.NewsDatabase
import com.example.news.data.remote.model.Article
import com.example.news.data.remote.model.NewsPostModel
import com.example.news.util.Network.NewsRetrofit
import com.example.news.data.repo.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel( application: Application) : AndroidViewModel(application) {

     var newsResponse = MutableLiveData<Response<NewsPostModel>>()
     var newsApiService = NewsRetrofit()
     var newsData : LiveData<List<Article>>
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

