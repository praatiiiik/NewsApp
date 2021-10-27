package com.example.news.NEWS.RecyclerView

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.news.MainActivity
import com.example.news.NEWS.ConverterforImage.ImageStorageManager
import com.example.news.NEWS.ConverterforImage.ImageStorageToCache
import com.example.news.NEWS.LocalDatabase.Article
import com.example.news.NEWS.LocalDatabase.forImage
import com.example.news.R


class RecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.noteViewHolder>() {

     val allNotes = ArrayList<Article>()
     val allImageData = ArrayList<forImage>()


    inner class noteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
         val imgView = itemView.findViewById<ImageView>(R.id.rclrImageView)
         val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
         val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBarrr)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteViewHolder {
        val viewHolder = noteViewHolder(LayoutInflater.from(context).inflate(R.layout.news_recycler_view,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {
        val currentNotes = allNotes[position]
        holder.titleTextView.text = currentNotes.content.toString()

            try {
                    holder.progressBar.visibility = View.INVISIBLE
                if(allImageData.size==0){
                    holder.imgView.visibility = View.INVISIBLE
                    holder.progressBar.visibility = View.VISIBLE
                } else {
                    holder.imgView.visibility = View.VISIBLE
                    val imageDataa = allImageData[position]
                    holder.imgView.load(ImageStorageManager.getImageFromInternalStorage(context,imageDataa.imageName.toString()))
                    Log.d("recycler",imageDataa.imageName.toString())
                }
            }catch (e : Exception){
                Log.d("tag",e.toString())
                holder.progressBar.visibility = View.VISIBLE
            }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Article>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateImage(imageList :List<forImage>){
        allImageData.clear()
        allImageData.addAll(imageList)
        notifyDataSetChanged()
    }

}