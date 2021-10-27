package com.example.news
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.news.NEWS.ConverterforImage.ImageStorageManager
import com.example.news.NEWS.NewsViewModel
import com.example.news.NEWS.RecyclerView.RecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.DelicateCoroutinesApi

class MainActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipe : SwipeRefreshLayout
    private lateinit var context: Context
    private lateinit var newsViewModel: NewsViewModel


    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.newsRecyclerView)
        swipe = findViewById(R.id.swipeToRefresh)

        context = this

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(this)
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = true
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

          newsViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NewsViewModel::class.java)

        collectingData(adapter)

        //to get the latest data from API
        swipe.setOnRefreshListener {
            adapter.allNotes.clear()
            adapter.allImageData.clear()
            recyclerView.scrollToPosition(0)
            newsViewModel.deleteImageData()
            clearImageFromData()
            newsViewModel.deleteNews()
            newsViewModel.getDataFromApi(context)
            swipe.isRefreshing = false;
        }


        Toast.makeText(this,"Swipe Down To Refresh", Toast.LENGTH_LONG).show()

    }

    //observe livedata and set to adapter
    @DelicateCoroutinesApi
    private fun collectingData(adapter: RecyclerViewAdapter) {

        newsViewModel.newsData.observe(this,{
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
