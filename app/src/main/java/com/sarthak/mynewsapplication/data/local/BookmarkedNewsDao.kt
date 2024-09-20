package com.sarthak.mynewsapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarthak.mynewsapplication.domain.model.NewsItem
import kotlinx.coroutines.flow.Flow


@Dao
interface BookmarkedNewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewsItem(bookmarkedNewsItem: BookmarkedNewsItem)

    @Query("DELETE FROM bookmarked_news WHERE title = :title")
    suspend fun removeNewsItem(title: String)

//    @Query("SELECT * FROM bookmarked_news")
//    fun getBookmarkedNews(): Flow<List<NewsItem>>

    @Query("SELECT COUNT(*) FROM bookmarked_news WHERE title = :title")
    fun isBookmarked(title: String): Int
}