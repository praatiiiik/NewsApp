package com.example.news
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import coil.imageLoader
import com.example.news.NEWS.ConverterforImage.ImageStorageManager
import com.example.news.NEWS.NewsViewModel
import com.example.news.NEWS.RecyclerView.RecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsNewsButton : FloatingActionButton
    private lateinit var context: Context
    private lateinit var progBar : ProgressBar
    private lateinit var newsViewModel: NewsViewModel


    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.newsRecyclerView)
        newsNewsButton = findViewById(R.id.newNewsData)
        progBar = findViewById(R.id.progress_bar)
        context = this

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(this)
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = true

          newsViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NewsViewModel::class.java)

        collectingData(adapter)

        //to get the latest data from API
        newsNewsButton.setOnClickListener {
            adapter.allNotes.clear()
            adapter.allImageData.clear()
            progBar.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
            newsNewsButton.visibility = View.INVISIBLE
            recyclerView.scrollToPosition(0)
            newsViewModel.deleteImageData()
            clearImageFromData()
            newsViewModel.deleteNews()
            newsViewModel.getDataFromApi(context)
        }
    }

    //observe livedata and set to adapter
    @DelicateCoroutinesApi
    private fun collectingData(adapter: RecyclerViewAdapter) {

        newsViewModel.newsData.observe(this,{
            progBar.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
            newsNewsButton.visibility = View.VISIBLE
            Log.d("live","sending article data")
            adapter.updateList(it)
        })

            newsViewModel.imageData.observe(this,{
                if(it!=null){
                    if(it.size>19){
                        Log.d("live","sending image data")
                        adapter.updateImage(it)
                    }
                }
            })

    }




    private fun clearImageFromData()  {
        ImageStorageManager.delete(context)
    }


}
