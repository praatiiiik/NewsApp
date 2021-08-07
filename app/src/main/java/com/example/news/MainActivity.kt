package com.example.news

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.news.NEWS.ConverterforImage.ImageStorageManager
import com.example.news.NEWS.LocalDatabase.Article
import com.example.news.NEWS.Network.NewsPostModel
import com.example.news.NEWS.NewsViewModel
import com.example.news.NEWS.RecyclerView.RecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*
import retrofit2.Response


class MainActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsNewsButton : FloatingActionButton
    private lateinit var context: Context

    private lateinit var newsViewModel: NewsViewModel
    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.newsRecyclerView)
        newsNewsButton = findViewById(R.id.newNewsData)
        context = this

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(this)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.adapter = adapter

        val newsView = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))
        newsViewModel = newsView.get(NewsViewModel::class.java)

        newsViewModel.allNotess.observe(this, { list->
            list?.let {
                adapter.updateList(it)
                Log.d("imgact","run")////////////////////////////
            }
        })

        newsNewsButton.setOnClickListener {
            newsViewModel.UpdateNewsData()
                newsViewModel.newsResponse.observe(this, {
                    val postList = it.body()
                    if (postList != null) {
                            GlobalScope.launch(Dispatchers.IO) {
                                for(i in postList.articles.indices){
                                        newsViewModel.insertNews(Article(postList.articles[i].content,postList.articles[i].description,postList.articles[i].publishedAt,postList.articles[i].title,postList.articles[i].url,postList.articles[i].urlToImage,i,getBitmap(i,it).toString()))
                            }
                        }
                    }
                })
        }
    }
//Coil function
        private suspend fun getBitmap(i : Int,response : Response<NewsPostModel>) : Bitmap? {
    return if (response.body()?.articles?.get(i)?.urlToImage!=null){
        coroutineScope {
            val loading = ImageLoader(context)
            val request = ImageRequest.Builder(context).data(response.body()?.articles?.get(i)?.urlToImage).build()
            val result = (loading.execute(request) as SuccessResult).drawable
            ImageStorageManager.saveToInternalStorage(context,(result as BitmapDrawable).bitmap, result.bitmap.toString())
            Log.d("imgmain", result.toString())
            result.bitmap
        }
    } else {
        coroutineScope {
            val loading = ImageLoader(context)
            val request = ImageRequest.Builder(context).data(response.body()?.articles?.get(i-1)?.urlToImage).build()
            val result = (loading.execute(request) as SuccessResult).drawable
            ImageStorageManager.saveToInternalStorage(context,(result as BitmapDrawable).bitmap, result.bitmap.toString())

            Log.d("imgmain", result.toString())
            result.bitmap
        }
    }

        }
}


/*coroutineScope {
            val loading = ImageLoader(context)
            val request = ImageRequest.Builder(context).data(response.body()?.articles?.get(i-1)?.urlToImage).build()
            val result = (loading.execute(request) as SuccessResult).drawable
            Log.d("imgmain", result.toString())
            (result as BitmapDrawable).bitmap
        }*/