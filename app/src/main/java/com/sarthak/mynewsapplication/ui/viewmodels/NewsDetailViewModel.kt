package com.sarthak.mynewsapplication.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarthak.mynewsapplication.domain.model.NewsItem
import com.sarthak.mynewsapplication.domain.repository.NewsRepository
import com.sarthak.mynewsapplication.utils.toBookmarkNewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
): ViewModel() {
    private val _newsItem = MutableStateFlow( savedStateHandle.get<NewsItem>("newsItem")!!)
    val newsItem: StateFlow<NewsItem> = _newsItem

    private suspend fun addBookmark(newsItem: NewsItem) {
        newsRepository.addBookmarkedNewsItem(newsItem.toBookmarkNewsItem())
    }

    private suspend fun removeBookmark(newsItem: NewsItem) {
        newsRepository.removeBookmarkedNewsItem(newsItem.title)
    }

    private fun isBookmarked(title: String): Boolean {
        return newsRepository.isBookmarked(title)
    }

    suspend fun toggleBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            if(isBookmarked(_newsItem.value.title)) {
                removeBookmark(_newsItem.value)
            } else {
                addBookmark(_newsItem.value)
            }
            _newsItem.value = _newsItem.value.copy(
                isBookmarked = isBookmarked(_newsItem.value.title)
            )
        }
    }



}