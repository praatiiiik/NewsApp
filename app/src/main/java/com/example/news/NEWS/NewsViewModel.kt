package com.example.news.NEWS

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.news.NEWS.DAilyNewsRepo.NewsRepo
import com.example.news.NEWS.LocalDatabase.NewsDatabase
import com.example.news.NEWS.LocalDatabase.Article
import com.example.news.NEWS.LocalDatabase.forImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel( application: Application) : AndroidViewModel(application) {


     var newsData : LiveData<List<Article>>
     var imageData : LiveData<List<forImage>>
     private val repositiory : NewsRepo

    init {
        val dao = NewsDatabase.getDatabase(application).getNews()
        repositiory = NewsRepo(dao)
        newsData = repositiory.allData
        imageData = repositiory.imageData
    }

    //get data from roomDB at initial level and set data to flow object
    fun getDAta(){
        viewModelScope.launch {
            newsData = repositiory.allData
        }
    }

    //These function get data from roomDB and set that data to flow object
     fun getDataFromApi(context: Context){
         viewModelScope.launch(Dispatchers.IO) {
             repositiory.newsReesponse(context)
         }
    }


     fun deleteNews(){
         viewModelScope.launch(Dispatchers.IO) {
             repositiory.deleteNews()
         }
    }

    fun deleteImageData(){
        viewModelScope.launch(Dispatchers.IO) {
            repositiory.deleteImage()
        }
    }




}

