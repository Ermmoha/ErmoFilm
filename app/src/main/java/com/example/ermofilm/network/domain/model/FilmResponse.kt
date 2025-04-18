package com.example.ermofilm.network.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmResponse(
    @SerialName("total")
    val total: Int = 0,
    @SerialName("totalPages")
    val totalPages: Int = 0,
    @SerialName("items")
    val items: List<FilmItem> = emptyList()
)

@Serializable
data class FilmItem(
    val kinopoiskId: Int = 0,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val nameOriginal: String? = null,
    val countries: List<Country> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val ratingKinopoisk: Double? = null,
    val ratingImdb: Double? = null,
    val year: Int? = null,
    val type: String = "UNKNOWN",
    val posterUrl: String = "",
    val posterUrlPreview: String,
    val logoUrl: String? = null,
    val coverUrl: String? = null,
    val ratingAgeLimits: String? = null,
    val filmLength: Int? = null,
    val slogan: String? = null,
    val description: String? = null,
)

@Serializable
data class Country(
    val id: Int = 0,
    val country: String?
)

@Serializable
data class Genre(
    val id: Int = 0,
    val genre: String?
)

@Serializable
data class Actor(
    val staffId:  Int,
    val nameRu: String?,
    val nameEn: String?,
    val description: String?,
    val posterUrl: String,
    val professionText: String,
    val professionKey: ProfessionKey
)

enum class ProfessionKey {
    WRITER,
    OPERATOR,
    EDITOR,
    COMPOSER,
    PRODUCER_USSR,
    TRANSLATOR,
    DIRECTOR,
    DESIGN,
    PRODUCER,
    ACTOR,
    VOICE_DIRECTOR,
    UNKNOWN
}

@Serializable
data class Cinema(
    val total: Int,
    val items: List<CinemaItems> = emptyList()
)

@Serializable
data class CinemaItems(
    val url: String = "",
    val platform: String = "",
    val logoUrl: String = "",
    val positiveRating: String = "",
    val negativeRating: String = "",
    val author: String = "",
    val title: String? = "",
    val description: String = ""

)

@Serializable
data class SequelsAndPrequelsResponse(
    val items: List<SequelsAndPrequelsItems> = emptyList()
)
@Serializable
data class SequelsAndPrequelsItems(
    val filmId: Int,
    val nameRu: String? = "",
    val nameEn: String? = "",
    val nameOriginal: String? = "",
    val posterUrl: String = "",
    val posterUrlPreview: String = "",
    val relationType: RelationType 
)

@Serializable
enum class RelationType {
    SEQUEL,
    PREQUEL,
    REMAKE,
    @SerialName("NUNKNOW")
    UNKNOWN
}

@Serializable
data class GenreListId(
    val countries: List<Country> = emptyList(),
    val genres: List<Genre> = emptyList(),
)

@Serializable
data class SearchFilm(
    val keyword: String = "",
    val pagesCount: Int = 1,
    val searchFilmsCountResult: Int = 0,
    val films: List<FilmItemSearch> = emptyList()
)

@Serializable
data class FilmItemSearch(
    val filmId: Int = 0,
    val nameRu: String = "",
    val nameEn: String = "",
    val type: Type,
    val year: String = "",
    val description: String = "",
    val filmLength: String = "",
    val countries: List<Country> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val rating: String = "",
    val ratingVoteCount: Int = 0,
    val posterUrl: String = "",
    val posterUrlPreview: String = ""

)

@Serializable
enum class Type{
    FILM,
    TV_SHOW,
    VIDEO,
    MINI_SERIES,
    TV_SERIES,
    UNKNOWN
}

@Serializable
data class SelectGenre(
    val total: Int = 0,
    val totalPages: Int = 0,
    val items: List<FilmItem> = emptyList()
)

@Serializable
data class VideoResponse(
    val total: Int,
    val items: List<VideoItem>
)

@Serializable
data class VideoItem(
    val url: String,
    val name: String,
    val site: VideoSite
)

@Serializable
enum class VideoSite {
    YOUTUBE,
    KINOPOISK_WIDGET,
    YANDEX_DISK,
    UNKNOWN
}