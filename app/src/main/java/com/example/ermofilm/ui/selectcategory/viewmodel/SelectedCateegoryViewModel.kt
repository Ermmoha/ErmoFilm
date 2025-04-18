package com.example.ermofilm.ui.selectcategory.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ermofilm.network.domain.repository.KtorRepository
import com.example.ermofilm.ui.selectcategory.SelectCategoryDestination.genreId
import com.example.ermofilm.ui.selectcategory.SelectCategoryDestination.type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedCategoryViewModel @Inject constructor(
    private val ktorRepository: KtorRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SelectState())
    val state = _state.asStateFlow()

    init {
        getFilms(genreId = genreId, type = type)
    }

    private fun getFilms(genreId: Int, type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val selectFilms = ktorRepository.getSelectGenre(genreId, type)
                Log.d("HomeViewModel", "Response: $selectFilms")

                selectFilms.items.let { list ->
                    _state.update { it.copy(selectGenreFilms = list) }
                    }

                } catch (e: Exception) {
                    Log.d("HomeViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }
}