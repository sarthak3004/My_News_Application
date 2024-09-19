package com.sarthak.mynewsapplication.domain.model

data class NewsResponse(
    val status: String = "",
    val totalResults: Int = 0,
    val newsItems: List<NewsItem> = emptyList(),
    val code: String = "",
    val message: String = ""
)