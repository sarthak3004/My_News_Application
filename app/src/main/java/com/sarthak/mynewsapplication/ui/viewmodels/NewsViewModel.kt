package com.sarthak.mynewsapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarthak.mynewsapplication.domain.model.NewsItem
import com.sarthak.mynewsapplication.domain.model.NewsResponse
import com.sarthak.mynewsapplication.domain.repository.NewsRepository
import com.sarthak.mynewsapplication.utils.FetchResult
import com.sarthak.mynewsapplication.utils.toBookmarkNewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getNewsList()
            getBookmarks()
        }
    }

    private fun getBookmarks() {
        _uiState.value = _uiState.value.copy(
            bookmarks = newsRepository.getBookmarkedNewsItems()
        )
    }

    private suspend fun addBookmark(newsItem: NewsItem) {
        newsRepository.addBookmarkedNewsItem(newsItem.toBookmarkNewsItem())
    }

    private suspend fun removeBookmark(newsItem: NewsItem) {
        newsRepository.removeBookmarkedNewsItem(newsItem.title)
    }

    private fun isBookmarked(title: String): Boolean {
        return newsRepository.isBookmarked(title)
    }

    suspend fun toggleBookmark(newsItem: NewsItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if(isBookmarked(newsItem.title)) {
                removeBookmark(newsItem)
            } else {
                addBookmark(newsItem)
            }

            val updatedBookmarks = newsRepository.getBookmarkedNewsItems()
            val updatedNewsItems = _uiState.value.newsResponse.newsItems.map { item ->
                if (item.title == newsItem.title) {
                    item.copy(isBookmarked = isBookmarked(newsItem.title))
                } else {
                    item
                }
            }
            _uiState.value = _uiState.value.copy(
                bookmarks = updatedBookmarks,
                newsResponse = _uiState.value.newsResponse.copy(
                    newsItems = updatedNewsItems,
                )
            )
        }
    }

    private suspend fun getNewsList() {
        newsRepository.getLatestNews().collect{ fetchResult ->
            when(fetchResult) {
                is FetchResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = fetchResult.message,
                        newsResponse = NewsResponse()
                    )
                }
                is FetchResult.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        isError = false,
                        errorMessage = "",
                        newsResponse = NewsResponse()
                    )
                }
                is FetchResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isError = false,
                        isLoading = false,
                        errorMessage = "",
                        newsResponse = fetchResult.data ?: NewsResponse()
                    )
                }
            }
        }
    }
}

data class NewsUiState(
    val newsResponse: NewsResponse = NewsResponse(),
    val bookmarks: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)