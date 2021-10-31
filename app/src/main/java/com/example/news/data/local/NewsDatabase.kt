package com.example.news.data.local

import android.content.Context
import androidx.room.*
import com.example.news.data.remote.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getNews(): NewsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        /**
         * Doing this setup in the DI module
         * So that it will be available to the every part of the application
         * and Can be easily injected anywhere.
         */

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