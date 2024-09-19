package com.sarthak.mynewsapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarthak.mynewsapplication.domain.model.NewsResponse
import com.sarthak.mynewsapplication.domain.repository.NewsRepository
import com.sarthak.mynewsapplication.utils.FetchResult
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
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)