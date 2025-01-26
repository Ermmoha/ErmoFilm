package com.example.ermofilm.ui.home.viewmodel

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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ktorRepository: KtorRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
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

                val bestFilms = ktorRepository.getFilmList("TOP_250_MOVIES")
                Log.d("HomeViewModel", "Response: $bestFilms")

                bestFilms.items.let { list ->
                    _state.update { it.copy(bestFilms = list) }
                }

                val zombieFilms = ktorRepository.getFilmList("VAMPIRE_THEME")
                Log.d("HomeViewModel", "Response: $zombieFilms")

                zombieFilms.items.let { list ->
                    _state.update { it.copy(zombieFilms = list) }
                }

                val comicsFilms = ktorRepository.getFilmList("COMICS_THEME")
                Log.d("HomeViewModel", "Response: $comicsFilms")

                comicsFilms.items.let { list ->
                    _state.update { it.copy(comicsFilms = list) }
                }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }
}