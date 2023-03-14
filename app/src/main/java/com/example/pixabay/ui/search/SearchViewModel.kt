package com.example.pixabay.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabay.Constants
import com.example.pixabay.data.remote.PixabayRepository
import com.example.pixabay.data.remote.models.PixabayResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pixabayRepository: PixabayRepository
): ViewModel() {
    val searchQueryState = MutableStateFlow(Constants.DEFAULT_KEYWORD_FRUIT)

    val uiState: StateFlow<SearchUiState> = searchQueryState.map {
        try {
            val result = pixabayRepository.getImages(searchQueryState.value, Constants.FIRST_PAGE)
            if (result.hits.isNotEmpty()) {
                SearchUiState.Loaded(data = result)
            } else {
                SearchUiState.Empty
            }
        } catch (e: java.lang.Exception) {
            SearchUiState.Error(message = "Something went wrong!")
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchUiState.Loading
    )

    fun updateSearchQuery(newQuery: String) {
        searchQueryState.update { newQuery }
    }

    sealed class SearchUiState {
        object Empty : SearchUiState()
        object Loading : SearchUiState()
        class Loaded(val data: PixabayResult) : SearchUiState()
        class Error(val message: String) : SearchUiState()
    }
}