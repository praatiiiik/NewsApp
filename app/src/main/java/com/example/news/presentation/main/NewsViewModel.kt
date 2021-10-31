package com.example.news.presentation.main

import android.app.Application
import androidx.lifecycle.*
import com.example.news.data.local.NewsDatabase
import com.example.news.data.remote.NewsRetrofit
import com.example.news.data.remote.model.Article
import com.example.news.data.remote.model.NewsPostModel
import com.example.news.data.repo.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * ViewModel only interact with Views and to fetch data to views the viewmodel gets data
 * from the repository
 * Injecting only the repository is the best practice
 * Creating new instance of the Repository and API service is a heavy operation
 * Everytime NewsViewModel is called new instance are created un-necessary
 */


class NewsViewModel( application: Application) : AndroidViewModel(application) {

    /**
     * Here there should be observers/producers of the data
     * one private which remains mutable other public which remain immutable
     *
     */


//     var newsResponse = MutableLiveData<Response<NewsPostModel>>()
//     var newsApiService = NewsRetrofit()
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

