package com.example.news.util.RecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.news.util.ConverterforImage.ImageStorageManager
import com.example.news.data.remote.model.Article
import com.example.news.R


class RecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.noteViewHolder>() {

    private val allNotes = ArrayList<Article>()

    /**
     * This view Holder can be in different class
     * Using ListAdapter is preferred instead of the Normal Recycler View
     * List Adapter has DIFF_UTIL for more performance
     */
    inner class noteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
         val imgView = itemView.findViewById<ImageView>(R.id.newsThumbnailImageView)
         val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteViewHolder {
        return noteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.news_recycler_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {
        val currentNotes = allNotes[position]
        holder.titleTextView.text = currentNotes.title
        holder.descriptionTextView.text = currentNotes.description
        holder.imgView.load(ImageStorageManager.getImageFromInternalStorage(context,currentNotes.image.toString()))
    }

    override fun getItemCount() = allNotes.size

    fun updateList(newList: List<Article>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}