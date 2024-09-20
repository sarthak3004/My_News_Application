package com.sarthak.mynewsapplication.utils

import com.sarthak.mynewsapplication.data.local.BookmarkedNewsItem
import com.sarthak.mynewsapplication.data.remote.response.NewsItemDto
import com.sarthak.mynewsapplication.data.remote.response.NewsResponseDto
import com.sarthak.mynewsapplication.domain.model.NewsItem
import com.sarthak.mynewsapplication.domain.model.NewsResponse

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
    content = content ?: "",
    publishedAt = publishedAt ?: ""
)

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
)