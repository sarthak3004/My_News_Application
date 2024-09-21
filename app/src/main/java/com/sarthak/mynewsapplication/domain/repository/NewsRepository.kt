package com.sarthak.mynewsapplication.domain.repository

import com.sarthak.mynewsapplication.data.local.BookmarkedNewsItem
import com.sarthak.mynewsapplication.domain.model.NewsItem
import com.sarthak.mynewsapplication.domain.model.NewsResponse
import com.sarthak.mynewsapplication.utils.FetchResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getLatestNews(): Flow<FetchResult<NewsResponse>>
    suspend fun addBookmarkedNewsItem(bookmarkedNewsItem: BookmarkedNewsItem)
    suspend fun removeBookmarkedNewsItem(title: String)
    fun getBookmarkedNewsItems(): List<NewsItem>
    fun isBookmarked(title: String): Boolean
}