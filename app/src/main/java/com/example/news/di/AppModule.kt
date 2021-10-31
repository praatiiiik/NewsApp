package com.example.news.di

import android.app.Application
import com.example.news.data.local.NewsDatabase
import com.example.news.data.remote.NetworkService
import com.example.news.data.remote.Networking
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Complete Retrofit Instance
    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService = Networking.create()

    // Complete Room Database Instance
    @Singleton
    @Provides
    fun provideQuoteDatabase(application: Application) =
        NewsDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun provideQuoteDao(database: NewsDatabase) = database.getNews()
}