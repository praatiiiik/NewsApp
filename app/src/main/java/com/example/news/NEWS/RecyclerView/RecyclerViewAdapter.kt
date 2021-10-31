package com.example.news.NEWS.RecyclerView

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.news.MainActivity
import com.example.news.NEWS.ConverterforImage.ImageStorageManager
import com.example.news.NEWS.ConverterforImage.ImageStorageToCache
import com.example.news.NEWS.LocalDatabase.Article
import com.example.news.R


class RecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.noteViewHolder>() {

    private val allNotes = ArrayList<Article>()

    inner class noteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
         val imgView = itemView.findViewById<ImageView>(R.id.rclrImageView)
         val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteViewHolder {
        val viewHolder = noteViewHolder(LayoutInflater.from(context).inflate(R.layout.news_recycler_view,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {
        val currentNotes = allNotes[position]
        holder.titleTextView.text = currentNotes.title
        holder.descriptionTextView.text = currentNotes.description
        holder.imgView.load(ImageStorageManager.getImageFromInternalStorage(context,currentNotes.image.toString()))
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }
    fun updateList(newList: List<Article>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}