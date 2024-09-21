package com.sarthak.mynewsapplication.utils

import android.os.Build
import com.sarthak.mynewsapplication.data.local.BookmarkedNewsItem
import com.sarthak.mynewsapplication.data.remote.response.NewsItemDto
import com.sarthak.mynewsapplication.data.remote.response.NewsResponseDto
import com.sarthak.mynewsapplication.domain.model.NewsItem
import com.sarthak.mynewsapplication.domain.model.NewsResponse
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun NewsResponseDto.toNewsResponse() = NewsResponse(
    status = status,
    totalResults = totalResults ?: 0,
    newsItems = articles?.map {item ->
        item.toNewsItem()
    } ?: emptyList(),
    code = code ?: "",
    message = message ?: ""
)

fun NewsItemDto.toNewsItem() = NewsItem(
    source = source?.name ?: "",
    author = author ?: "",
    title = title ?: "",
    description = description ?: "",
    url = url ?: "",
    urlToImage = urlToImage ?: "",
    content = formatContent(content),
    publishedAt = formatPublishDate(publishedAt)
)

fun formatContent(content: String?): String {
    return content?.substringBefore("[+") ?: ""
}

fun formatPublishDate(publishDate: String?): String {
    if(publishDate == null) {
        return ""
    }
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date: Date = inputFormat.parse(publishDate) ?: return ""
    val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
    return outputFormat.format(date)
}

fun NewsItem.toBookmarkNewsItem() = BookmarkedNewsItem(
    title = title,
    source = source,
    author = author,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
)

fun BookmarkedNewsItem.toNewsItem() = NewsItem(
    title = title,
    source = source,
    author = author,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    isBookmarked = true
)