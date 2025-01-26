package com.example.ermofilm.ui.home.viewmodel

import com.example.ermofilm.network.domain.model.FilmItem

data class HomeState(
    val popularFilms: List<FilmItem> = emptyList(),
    val bestFilms: List<FilmItem> = emptyList(),
    val zombieFilms: List<FilmItem> = emptyList(),
    val comicsFilms: List<FilmItem> = emptyList()
)
