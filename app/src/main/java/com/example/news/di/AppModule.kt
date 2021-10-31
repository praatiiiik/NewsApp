package com.example.news.di

import android.app.Application
import com.example.news.NEWS.LocalDatabase.NewsDatabase
import com.example.news.NEWS.Network.NewsRetrofit
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
    fun provideNetworkService() : NewsRetrofit = NewsRetrofit()

    // Complete Room Database Instance
    @Singleton
    @Provides
    fun provideQuoteDatabase(application: Application) =
        NewsDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun provideQuoteDao(database: NewsDatabase) = database.getNews()
}