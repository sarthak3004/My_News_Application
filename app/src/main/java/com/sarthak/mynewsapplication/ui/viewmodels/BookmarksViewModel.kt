package com.sarthak.mynewsapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarthak.mynewsapplication.domain.model.NewsItem
import com.sarthak.mynewsapplication.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {
    private val _bookmarks = MutableStateFlow(emptyList<NewsItem>())
    val bookmarks: StateFlow<List<NewsItem>> = _bookmarks

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getBookmarks()
        }
    }

    private fun getBookmarks() {
        _bookmarks.value = newsRepository.getBookmarkedNewsItems()
    }

    private suspend fun removeBookmark(title: String) {
        newsRepository.removeBookmarkedNewsItem(title)
    }

    private fun isBookmarked(title: String): Boolean {
        return newsRepository.isBookmarked(title)
    }

    suspend fun toggleBookmark(newsItem: NewsItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if(isBookmarked(newsItem.title)) {
                removeBookmark(newsItem.title)
            }
            val updatedNewsItems = _bookmarks.value.filter { item ->
                item.title != newsItem.title
            }
            _bookmarks.value = updatedNewsItems
        }
    }
}