package com.example.news

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
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
import kotlinx.coroutines.flow.collect
import retrofit2.Response


class MainActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsNewsButton : FloatingActionButton
    private lateinit var context: Context
    private lateinit var manager: ActivityManager
    private lateinit var progBar : ProgressBar
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    val fromDb = CoroutineScope(Job() + Dispatchers.Main)
    var fetchFromServer: Job? = null
    var cannFetchFromDb = false




    private lateinit var newsViewModel: NewsViewModel
    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.newsRecyclerView)
        newsNewsButton = findViewById(R.id.newNewsData)
        progBar = findViewById(R.id.progress_bar)
        context = this
        manager = (getSystemService(ACTIVITY_SERVICE) as ActivityManager)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(this)
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        val newsView = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))
        newsViewModel = newsView.get(NewsViewModel::class.java)

        collectingData(adapter)

        newsNewsButton.setOnClickListener {

            Log.d("img","fromDb insideClick "+fromDb.isActive.toString())
            Log.d("img","fetchFromServer insideClick "+ fetchFromServer?.isActive.toString())


            cannFetchFromDb = true
            recyclerView.visibility = View.GONE
            progBar.visibility = View.VISIBLE
            newsNewsButton.visibility = View.INVISIBLE
          //  fromDb.cancel()


            recyclerView.scrollToPosition(0)
            clearImageFromData()  //delete from app specefic file


            newsViewModel.UpdateNewsData()


                newsViewModel.repositiory.newsResponse.observe(this, { response ->
                    val postList = response.body()
                    if (postList != null) {
                        fetchFromServer = scope.launch(Dispatchers.IO) {
                                launch{
                                    for (i in postList.articles.indices) {
                                        newsViewModel.insertNews(
                                            Article(
                                                postList.articles[i].content,
                                                postList.articles[i].description,
                                                postList.articles[i].publishedAt,
                                                postList.articles[i].title,
                                                postList.articles[i].url,
                                                postList.articles[i].urlToImage,
                                                i,
                                                getBitmap(i, response).toString()
                                            )
                                        )
                                    if(i==19){
                                        break
                                    }
                                }
                                    Log.d("img","launch about to end")
                            }.join()
                            Log.d("img","launch ends")
                            cannFetchFromDb = false
                            launch(Dispatchers.Main) {
                                collectingData(adapter)
                            }
                        }
                    }
                })

        }
    }

    @DelicateCoroutinesApi
    private   fun collectingData(adapter: RecyclerViewAdapter) {
        newsViewModel.newsData.observe(this@MainActivity,{
                     adapter.updateList(it)
                 })
                    progBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    newsNewsButton.visibility = View.VISIBLE
    }



    //Coil function
        private suspend fun getBitmap(i : Int,response : Response<NewsPostModel>) : Bitmap? {
        try {
            return if (response.body()?.articles?.get(i)?.urlToImage != null) {
                coroutineScope {
                    val loading = ImageLoader(context)
                    val request = ImageRequest.Builder(context)
                        .data(response.body()?.articles?.get(i)?.urlToImage).build()
                    val result = (loading.execute(request) as SuccessResult).drawable
                    val string = ImageStorageManager.saveToInternalStorage(
                        context,
                        (result as BitmapDrawable).bitmap,
                        result.bitmap.toString()
                    )
                    Log.d("imgmain", result.toString())
                    result.bitmap
                }
            } else {
                coroutineScope {
                    val loading = ImageLoader(context)
                    val request = ImageRequest.Builder(context)
                        .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
                        .build()
                    val result = (loading.execute(request) as SuccessResult).drawable
                    ImageStorageManager.saveToInternalStorage(
                        context,
                        (result as BitmapDrawable).bitmap,
                        result.bitmap.toString()
                    )
                    Log.d("imgmain", result.toString())
                    result.bitmap
                }
            }
        } catch (e : Exception){
            return coroutineScope {
                val loading = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
                    .build()
                val result = (loading.execute(request) as SuccessResult).drawable
                ImageStorageManager.saveToInternalStorage(
                    context,
                    (result as BitmapDrawable).bitmap,
                    result.bitmap.toString()
                )
                Log.d("imgmain", result.toString())
                result.bitmap
            }
        }
    }


    private fun clearImageFromData()  {
        ImageStorageManager.delete(context)
    }


}
