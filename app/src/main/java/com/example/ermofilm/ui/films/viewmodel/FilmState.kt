package com.example.ermofilm.ui.films.viewmodel

import com.example.ermofilm.network.domain.model.Actor
import com.example.ermofilm.network.domain.model.CinemaItems
import com.example.ermofilm.network.domain.model.FilmItem
import com.example.ermofilm.network.domain.model.SequelsAndPrequelsItems

data class FilmState(
    val filmInfo: FilmItem? = null,
    val actors: List<Actor> = emptyList(),
    val cinema: List<CinemaItems> = emptyList(),
    val sequelsAndPrequels: List<SequelsAndPrequelsItems> = emptyList()
)