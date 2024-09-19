package com.sarthak.mynewsapplication.data.remote.response

data class NewsResponseDto(
    val status: String,
    val totalResults: Int?,
    val articles: List<NewsItemDto>?,
    val code: String?,
    val message: String?
)