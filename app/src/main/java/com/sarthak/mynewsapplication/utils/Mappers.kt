package com.sarthak.mynewsapplication.utils

import com.sarthak.mynewsapplication.data.remote.response.NewsItemDto
import com.sarthak.mynewsapplication.data.remote.response.NewsResponseDto
import com.sarthak.mynewsapplication.domain.model.NewsItem
import com.sarthak.mynewsapplication.domain.model.NewsResponse

fun NewsResponseDto.toNewsResponse() = NewsResponse(
    status = status,
    totalResults = totalResults ?: 0,
    newsItems = articles?.mapIndexed { index, item ->
        item.toNewsItem(index)
    } ?: emptyList(),
    code = code ?: "",
    message = message ?: ""
)

fun NewsItemDto.toNewsItem(id: Int) = NewsItem(
    id = id,
    source = source?.name ?: "",
    author = author ?: "",
    title = title ?: "",
    description = description ?: "",
    url = url ?: "",
    urlToImage = urlToImage ?: "",
    content = content ?: "",
    publishedAt = publishedAt ?: ""
)