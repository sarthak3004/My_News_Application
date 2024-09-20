package com.sarthak.mynewsapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_news")
data class BookmarkedNewsItem(
    @PrimaryKey
    val title: String = "",
    val source: String = "",
    val author: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = "",
)