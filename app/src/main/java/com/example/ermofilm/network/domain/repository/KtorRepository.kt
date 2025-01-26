package com.example.ermofilm.network.domain.repository

import com.example.ermofilm.network.domain.model.Actor
import com.example.ermofilm.network.domain.model.Cinema
import com.example.ermofilm.network.domain.model.FilmItem
import com.example.ermofilm.network.domain.model.FilmResponse
import com.example.ermofilm.network.domain.model.GenreListId
import com.example.ermofilm.network.domain.model.SearchFilm
import com.example.ermofilm.network.domain.model.SelectGenre
import com.example.ermofilm.network.domain.model.SequelsAndPrequels

interface KtorRepository {
    suspend fun getFilmList(category: String): FilmResponse
    suspend fun getFilmById(id: Int): FilmItem
    suspend fun getActorById(id: Int): List<Actor>
    suspend fun getCinemaById(id: Int): Cinema
    suspend fun getSequelsAndPrequels(id: Int): SequelsAndPrequels
    suspend fun getGenreList(): GenreListId
    suspend fun getSearchFilm(encodedQuery: String): SearchFilm
    suspend fun getSelectGenre(genreId: Int): SelectGenre
}