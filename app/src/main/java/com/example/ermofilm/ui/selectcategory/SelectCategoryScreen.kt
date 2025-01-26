package com.example.ermofilm.ui.selectcategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ermofilm.ui.home.CardView
import com.example.ermofilm.ui.home.TitleFilms
import com.example.ermofilm.ui.navigation.NavigationDestination
import com.example.ermofilm.ui.selectcategory.viewmodel.SelectedCategoryViewModel

object SelectCategoryDestination: NavigationDestination{
    var genreId = 0
    override val title = "Выбранно"
    override val route = "select"
}

@Composable
fun SelectCategoryScreen(
    viewModel: SelectedCategoryViewModel,
    navigateToFilm: (Int) -> Unit
){
    val state by viewModel.state.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(6.dp)
    ) {
        items(state.selectGenreFilms) {
            Column {
                CardView(
                    imageUrl = it.posterUrl,
                    age = it.ratingAgeLimits,
                    rating = it.ratingKinopoisk,
                    id = it.kinopoiskId,
                    navigateToFilm = navigateToFilm
                )
                TitleFilms(title = it.nameRu ?: it.nameOriginal.toString())
            }
        }
    }
}