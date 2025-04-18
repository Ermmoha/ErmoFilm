package com.example.ermofilm.ui.films.viewmodel

import com.example.ermofilm.network.domain.model.Actor
import com.example.ermofilm.network.domain.model.CinemaItems
import com.example.ermofilm.network.domain.model.Country
import com.example.ermofilm.network.domain.model.FilmItem
import com.example.ermofilm.network.domain.model.Genre
import com.example.ermofilm.network.domain.model.SequelsAndPrequelsItems
import com.example.ermofilm.network.domain.model.VideoItem

data class FilmState(
    val filmInfo: FilmItem? = null,
    val actors: List<Actor> = emptyList(),
    val cinema: List<CinemaItems> = emptyList(),
    val sequelsAndPrequels: List<SequelsAndPrequelsItems> = emptyList(),
    val genreList: List<Genre> = emptyList(),
    val countryList: List<Country> = emptyList(),
    val genreMap: Map<String, Int> = emptyMap(),
    val countryMap: Map<String, Int> = emptyMap(),
    val trailer: List<VideoItem> = emptyList()

)