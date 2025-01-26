package com.example.ermofilm.ui.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ermofilm.R
import com.example.ermofilm.ui.navigation.NavigationDestination
import com.example.ermofilm.ui.search.viewmodel.SearchState
import com.example.ermofilm.ui.search.viewmodel.SearchViewModel

object SearchDestination: NavigationDestination {
    override val title = "Поиск"
    override val route = "search"
}

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navigateToFilm: (Int) -> Unit
){
    val state by viewModel.state.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ){
        if (state.searchQuery == "") {
            item {
                Text(
                    text = "Сейчас ищут",
                    color = Color.White,
                    fontSize = 35.sp,
                    fontFamily = FontFamily(Font(R.font.logo7))
                )
            }
            items(state.popularFilms) { film ->
                CardsSearch(
                    imgUrl = film.posterUrl,
                    title = film.nameRu ?: film.nameOriginal.toString(),
                    state = state,
                    navigateToFilm = navigateToFilm,
                    id = film.kinopoiskId
                )
            }
        } else {
            item {
                Text(
                    text = "Результаты поиска по: " + state.searchQuery,
                    color = Color.White,
                    fontSize = 35.sp,
                    fontFamily = FontFamily(Font(R.font.logo7))
                )
            }
            items(state.searchFilm) { film ->
                CardsSearch(
                    imgUrl = film.posterUrl,
                    title = film.nameRu,
                    state = state,
                    navigateToFilm = navigateToFilm,
                    id = film.filmId
                )
            }
        }
    }
}

@Composable
fun CardsSearch(
    imgUrl: String?,
    title: String?,
    state: SearchState,
    navigateToFilm: (Int) -> Unit,
    id: Int
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Log.d("CardsSearch", "Navigating to Film ID: $id")
                navigateToFilm(id)  }

            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            model = imgUrl,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)

            )
        title?.let {
            Text(
                text = it,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.logo7)),
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(30.dp)
                    .padding(start = 8.dp)
                )
        }
    }


}