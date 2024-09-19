package com.sarthak.mynewsapplication.domain.repository

import com.sarthak.mynewsapplication.domain.model.NewsResponse
import com.sarthak.mynewsapplication.utils.FetchResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getLatestNews(): Flow<FetchResult<NewsResponse>>
}