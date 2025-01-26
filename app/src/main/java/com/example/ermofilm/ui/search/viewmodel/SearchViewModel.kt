package com.example.ermofilm.ui.search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ermofilm.network.domain.repository.KtorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
private val ktorRepository: KtorRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        loadFilms()
    }

    private fun loadFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val popularFilm = ktorRepository.getFilmList("TOP_POPULAR_ALL")
                Log.d("HomeViewModel", "Response: $popularFilm")

                popularFilm.items.let { list ->
                    _state.update { it.copy(popularFilms = list) }
                }

            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }

    fun updateSearchQuery(value: String) {
        _state.update {
            it.copy(
                searchQuery = value,
                encodedQuery = value.toEncodedQuery()
            )
        }
    }

    private fun String.toEncodedQuery() =
        URLEncoder.encode(this, StandardCharsets.UTF_8.toString())

    fun getSearchFilm() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val encodedQuery = state.value.encodedQuery
                val searchFilm = ktorRepository.getSearchFilm(encodedQuery)
                Log.d("SearchViewModel", "Error: $searchFilm")
                searchFilm.films.let { list ->
                    _state.update { it.copy(searchFilm = list) }
                }

                } catch (e: Exception) {
                    Log.d("HomeViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }
}

