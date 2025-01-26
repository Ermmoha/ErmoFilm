package com.example.ermofilm.ui.films.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ermofilm.network.domain.repository.KtorRepository
import com.example.ermofilm.ui.films.FilmDestination.filmId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmViewModel @Inject constructor(
    private val ktorRepository: KtorRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FilmState())
    val state = _state.asStateFlow()

    init {
        getFilmInfo(filmId)
        Log.d("FilmViewModel", "${state.value.sequelsAndPrequels}")
    }

    private fun getFilmInfo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filmInfo = ktorRepository.getFilmById(id)
                val actorsInfo = ktorRepository.getActorById(id)
                val sequelsPrequels = ktorRepository.getSequelsAndPrequels(id)
                Log.d("FilmViewMod", "Response: $sequelsPrequels")

                _state.update { it.copy(
                    filmInfo = filmInfo!!,
                    actors = actorsInfo,
                    sequelsAndPrequels = sequelsPrequels.items!!

                ) }
            } catch (e: Exception) {
                Log.e("FilmViewModel", "Stacktrace: ${e.stackTraceToString()}")
                Log.d("FilmViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }

    fun getCinemaInfo(id: Int) {
        viewModelScope.launch {
            try {
                val cinemaInfo = ktorRepository.getCinemaById(id)
                _state.update { it.copy(cinema = cinemaInfo.items) }
            } catch (e: Exception) {
                Log.d("FilmViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }
}