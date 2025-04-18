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
        getGenre()
        Log.d("FilmViewModel", "${state.value.sequelsAndPrequels}")
    }


    private fun getGenre(){
        viewModelScope.launch {
            try {
                val genreList = ktorRepository.getGenreList()
                Log.d("HomeViewModel", "Response: $genreList")

                val genreMapping = genreList.genres.associateBy({ it.genre ?: "" }, { it.id })
                val countryMapping = genreList.countries.associateBy ({ it.country ?: "" }, { it.id })

                _state.update { it.copy(
                    genreList = genreList.genres,
                    genreMap = genreMapping,
                    countryMap = countryMapping,
                    countryList = genreList.countries)
                }
            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error: ${e.localizedMessage}")
            }
        }
    }

    private fun getFilmInfo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filmInfo = ktorRepository.getFilmById(id)
                val actorsInfo = ktorRepository.getActorById(id)
                val sequelsPrequels = ktorRepository.getSequelsAndPrequels(id)
                Log.d("FilmViewModel", "Sequels and Prequels: ${sequelsPrequels}")

                _state.update { it.copy(
                    filmInfo = filmInfo!!,
                    actors = actorsInfo,
                    sequelsAndPrequels = sequelsPrequels

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
    fun getTrailerInfo(id: Int) {
        viewModelScope.launch {
            try {
                val trailerInfo = ktorRepository.getTrailerFilm(id)
                _state.update { it.copy(trailer = trailerInfo.items) }
            } catch (e: Exception) {
                Log.d("FilmViewModelTrailer", "Error: ${e.localizedMessage}")
            }
        }
    }
}