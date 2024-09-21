package com.sarthak.mynewsapplication.data.repository

import android.util.Log
import com.sarthak.mynewsapplication.data.local.BookmarkedNewsDao
import com.sarthak.mynewsapplication.data.local.BookmarkedNewsItem
import com.sarthak.mynewsapplication.data.remote.api.NewsApi
import com.sarthak.mynewsapplication.domain.model.NewsItem
import com.sarthak.mynewsapplication.domain.model.NewsResponse
import com.sarthak.mynewsapplication.domain.repository.NewsRepository
import com.sarthak.mynewsapplication.utils.FetchResult
import com.sarthak.mynewsapplication.utils.toNewsItem
import com.sarthak.mynewsapplication.utils.toNewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val bookmarkedNewsDao: BookmarkedNewsDao
): NewsRepository {

    override suspend fun getLatestNews(): Flow<FetchResult<NewsResponse>> = flow {
        emit(FetchResult.Loading())
        val newsResponseDto = try {
            newsApi.getLatestNews()
        } catch (e: IOException) {
            e.printStackTrace()
            emit(FetchResult.Error(message = "Oh! Could not load data."))
            return@flow
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(FetchResult.Error(message = "Oh! Could not load data."))
            return@flow
        } catch (e: Exception) {
            e.printStackTrace()
            emit(FetchResult.Error(message = "Oh! Could not load data."))
            return@flow
        }
        val newsItems = newsResponseDto.toNewsResponse().newsItems.map { item ->
            item.isBookmarked = bookmarkedNewsDao.isBookmarked(item.title) == 1
            item
        }
        val newsResponse = newsResponseDto.toNewsResponse().copy(
            newsItems = newsItems
        )
        emit(FetchResult.Success(data = newsResponse))
    }

    override suspend fun addBookmarkedNewsItem(bookmarkedNewsItem: BookmarkedNewsItem) = bookmarkedNewsDao.addNewsItem(bookmarkedNewsItem)

    override suspend fun removeBookmarkedNewsItem(title: String) = bookmarkedNewsDao.removeNewsItem(title)

    override fun getBookmarkedNewsItems(): List<NewsItem> {
        val bookmarkedNewsItems = bookmarkedNewsDao.getBookmarkedNews()
        val newsItems = bookmarkedNewsItems.map {
            it.toNewsItem()
        }
        return  newsItems
    }

    override fun isBookmarked(title: String): Boolean {
        return bookmarkedNewsDao.isBookmarked(title) == 1
    }
}