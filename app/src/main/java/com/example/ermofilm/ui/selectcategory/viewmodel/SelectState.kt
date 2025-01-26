package com.example.ermofilm.ui.selectcategory.viewmodel

import com.example.ermofilm.network.domain.model.FilmItem

data class SelectState (
    val selectGenreFilms: List<FilmItem> = emptyList()
)