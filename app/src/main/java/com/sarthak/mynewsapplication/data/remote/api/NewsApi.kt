package com.sarthak.mynewsapplication.data.remote.api

import com.sarthak.mynewsapplication.BuildConfig
import com.sarthak.mynewsapplication.data.remote.response.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getLatestNews(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("country") country: String = "us"
    ): NewsResponseDto

    companion object {
        const val API_KEY = BuildConfig.api_key
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}