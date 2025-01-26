package com.example.ermofilm.ui.category.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ermofilm.network.domain.repository.KtorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val ktorRepository: KtorRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryState())
    val state = _state.asStateFlow()

    init {
        getGenre()
    }

    private fun getGenre(){
        viewModelScope.launch {
            try {
                val genreList = ktorRepository.getGenreList()
                Log.d("HomeViewModel", "Response: $genreList")

                genreList.genres.let { list ->
                    _state.update { it.copy(genreList = list) }
                }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error: ${e.localizedMessage}")
        }
    }
    }
}
