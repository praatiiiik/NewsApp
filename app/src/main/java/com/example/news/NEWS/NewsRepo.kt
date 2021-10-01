package com.example.news.NEWS

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.news.NEWS.ConverterforImage.ImageStorageManager
import com.example.news.NEWS.LocalDatabase.News_DAO
import com.example.news.NEWS.LocalDatabase.Article
import com.example.news.NEWS.LocalDatabase.forImage
import com.example.news.NEWS.Network.NewsPostModel
import com.example.news.NEWS.Network.NewsRetrofit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

class NewsRepo(private val dao: News_DAO) {

    val allData: LiveData<List<Article>> = dao.getNews()
    val imageData : LiveData<List<forImage>> = dao.gettmage()

    //   var newsResponse = Response<NewsPostModel>()
    var newsApiService = NewsRetrofit()



    suspend fun insert(data : List<Article>) {
        dao.upsert(data)
    }

    suspend fun saveImage(img : forImage){
        dao.saveImage(img)
    }

    fun deleteNews(){
        dao.deleteNews()
    }

    fun deleteImage(){
        dao.deleteImageData()
    }




    suspend fun newsReesponse(context: Context){
        val response = newsApiService.getNews("in")
        val newsresponse  = response.body()
        if (newsresponse != null) {
            insert(newsresponse.articles)
            for(i in newsresponse.articles.indices){
                saveImage(forImage(getBitmap(newsresponse.articles[i].urlToImage,context,i).toString(),i))
            }
        }

    }







    private suspend fun getBitmap(string: String?,context: Context,i:Int?) : Bitmap? {
        try {
            return if (string != null) {
                coroutineScope {
                    val loading = ImageLoader(context)
                    val request = ImageRequest.Builder(context).data(string).build()
                    val result = (loading.execute(request) as SuccessResult).drawable
                    val string1 = ImageStorageManager.saveToInternalStorage(
                        context,
                        (result as BitmapDrawable).bitmap,
                        result.bitmap.toString()
                    )
                    result.bitmap
                }
            } else {
                coroutineScope {
                    val loading = ImageLoader(context)
                    Log.d("tag","from else")
                    val request = ImageRequest.Builder(context)
                        .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
                        .build()
                    val result = (loading.execute(request) as SuccessResult).drawable
                    ImageStorageManager.saveToInternalStorage(
                        context,
                        (result as BitmapDrawable).bitmap,
                        result.bitmap.toString()
                    )
                    result.bitmap
                }
            }
        } catch (e : Exception){
            return coroutineScope {
                val loading = ImageLoader(context)
                Log.d("abc","from catch $e")
                val request = ImageRequest.Builder(context)
                    .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
                    .build()
                val result = (loading.execute(request) as SuccessResult).drawable
                ImageStorageManager.saveToInternalStorage(
                    context,
                    (result as BitmapDrawable).bitmap,
                    result.bitmap.toString()
                )
                result.bitmap
            }
        }
    }


}


/*
private suspend fun getBitmap(string: String,context: Context) : Bitmap? {
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
 */