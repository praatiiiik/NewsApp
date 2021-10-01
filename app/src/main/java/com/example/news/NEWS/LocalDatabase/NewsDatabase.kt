package com.example.news.NEWS.LocalDatabase

import android.content.Context
import androidx.room.*

@Database(entities = [Article::class,forImage::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getNews(): News_DAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news.db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}