package com.example.ermofilm.ui.search.viewmodel

import com.example.ermofilm.network.domain.model.FilmItem
import com.example.ermofilm.network.domain.model.FilmItemSearch

data class SearchState (
    val popularFilms: List<FilmItem> = emptyList(),
    val searchQuery: String = "",
    val encodedQuery: String  = "",
    val searchFilm:List<FilmItemSearch> = emptyList()
)