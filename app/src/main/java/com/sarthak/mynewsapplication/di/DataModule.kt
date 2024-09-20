package com.sarthak.mynewsapplication.di

import android.content.Context
import androidx.room.Room
import com.sarthak.mynewsapplication.data.local.BookmarkedNewsDao
import com.sarthak.mynewsapplication.data.local.BookmarkedNewsDatabase
import com.sarthak.mynewsapplication.data.remote.api.NewsApi
import com.sarthak.mynewsapplication.data.repository.NewsRepositoryImpl
import com.sarthak.mynewsapplication.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BookmarkedNewsDatabase {
        return Room.databaseBuilder(
            context,
            BookmarkedNewsDatabase::class.java,
            "bookmarked_news_database"
        ).build()
    }

    @Provides
    fun provideDao(database: BookmarkedNewsDatabase): BookmarkedNewsDao {
        return database.bookmarkedNewsDao()
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi, bookmarkedNewsDao: BookmarkedNewsDao): NewsRepository {
        return NewsRepositoryImpl(newsApi, bookmarkedNewsDao)
    }

}