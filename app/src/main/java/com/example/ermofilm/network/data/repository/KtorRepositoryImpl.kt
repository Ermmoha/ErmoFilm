package com.example.ermofilm.network.data.repository

import com.example.ermofilm.network.core.ApiConst
import com.example.ermofilm.network.domain.model.Actor
import com.example.ermofilm.network.domain.model.Cinema
import com.example.ermofilm.network.domain.model.FilmItem
import com.example.ermofilm.network.domain.model.FilmResponse
import com.example.ermofilm.network.domain.model.GenreListId
import com.example.ermofilm.network.domain.model.SearchFilm
import com.example.ermofilm.network.domain.model.SelectGenre
import com.example.ermofilm.network.domain.model.SequelsAndPrequelsItems
import com.example.ermofilm.network.domain.model.VideoResponse
import com.example.ermofilm.network.domain.repository.KtorRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import javax.inject.Inject

class KtorRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : KtorRepository {
    override suspend fun getFilmList(category: String): FilmResponse =
        httpClient.get(ApiConst.FILM_URL + category + "&page=1") {
            headers {
                append("X-API-KEY", "5d3845da-1e85-41ae-9445-f4def95ce797")
                append("Content-Type", "application/json")
            }
        }.body()

    override suspend fun getFilmById(id: Int): FilmItem =
        httpClient.get(ApiConst.INFO_FILM_URL + id) {
            headers {
                append("X-API-KEY", "5d3845da-1e85-41ae-9445-f4def95ce797")
                append("Content-Type", "application/json")
            }
        }.body()

    override suspend fun getActorById(id: Int): List<Actor> =
        httpClient.get(ApiConst.ACTORS_URL + id) {
            headers {
                append("X-API-KEY", "5d3845da-1e85-41ae-9445-f4def95ce797")
                append("Content-Type", "application/json")
            }
        }.body()

    override suspend fun getCinemaById(id: Int): Cinema =
        httpClient.get(ApiConst.INFO_FILM_URL + id + "/external_sources?page=1") {
            headers {
                append("X-API-KEY", "5d3845da-1e85-41ae-9445-f4def95ce797")
                append("Content-Type", "application/json")
            }
        }.body()

    override suspend fun getSequelsAndPrequels(id: Int): List<SequelsAndPrequelsItems> =
        httpClient.get(ApiConst.SEQUELES_AND_PREQUELS + id + "/sequels_and_prequels") {
            headers {
                append("X-API-KEY", "5d3845da-1e85-41ae-9445-f4def95ce797")
                append("Content-Type", "application/json")
            }
        }.body()

    override suspend fun getGenreList(): GenreListId =
        httpClient.get(ApiConst.GENRE_LIST_URL) {
            headers {
                append("X-API-KEY", "5d3845da-1e85-41ae-9445-f4def95ce797")
                append("Content-Type", "application/json")
            }
        }.body()

    override suspend fun getSearchFilm(encodedQuery: String): SearchFilm =
        httpClient.get(ApiConst.SEARCH_URL + encodedQuery + "&page=1") {
            headers {
                append("X-API-KEY", "5d3845da-1e85-41ae-9445-f4def95ce797")
                append("Content-Type", "application/json")
            }
        }.body()

    override suspend fun getSelectGenre(genreId: Int, type:  String): SelectGenre =
        httpClient.get(ApiConst.SELECT_GENRE_URL + type + genreId + "&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000&page=1") {
            headers {
                append("X-API-KEY", "5d3845da-1e85-41ae-9445-f4def95ce797")
                append("Content-Type", "application/json")
            }
        }.body()

    override suspend fun getTrailerFilm(id: Int): VideoResponse =
        httpClient.get(ApiConst.INFO_FILM_URL + id + "/videos") {
            headers {
                append("X-API-KEY", "5d3845da-1e85-41ae-9445-f4def95ce797")
                append("Content-Type", "application/json")
            }
        }.body()
}