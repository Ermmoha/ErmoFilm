package com.example.ermofilm.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ermofilm.R
import com.example.ermofilm.ui.home.viewmodel.HomeState
import com.example.ermofilm.ui.home.viewmodel.HomeViewModel
import com.example.ermofilm.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val title = "Главный"
    override val route = "home"
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToFilm: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = Color.Black
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item { Category(category = "Популярно сейчас") }
            item { CardRow(state, count = 1, navigateToFilm = navigateToFilm) }
            item { Category(category = "Мы рекомендуем") }
            item { CardRow(state, count = 2, navigateToFilm = navigateToFilm) }
            item { Category(category = "Любителям вампиров") }
            item { CardRow(state, count = 3, navigateToFilm = navigateToFilm) }
            item { Category(category = "Любителям комиксов") }
            item { CardRow (state, count = 4, navigateToFilm = navigateToFilm) }
        }
    }
}

@Composable
fun Category(category: String) {
    Text(
        text = category,
        fontSize = 30.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.logo3)),
        modifier = Modifier.padding(5.dp,0.dp)

    )
}

@Composable
fun TitleFilms(title: String) {
        Text(
            text = title,
            color = Color.White,
            maxLines = 2,
            fontSize = 15.sp,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            lineHeight = 20.sp,
            modifier = Modifier
                .padding(8.dp)
                .height(40.dp)
                .width(150.dp),
            textAlign = TextAlign.Start
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardView(
    imageUrl: String?,
    age: String?,
    rating: Double?,
    id: Int,
    navigateToFilm: (Int) -> Unit
){
    Card(
        onClick = { navigateToFilm(id) },
        modifier = Modifier
            .height(200.dp)
            .width(150.dp)
            .padding(3.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
        if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Text(
            text = "Нет изображения",
            color = Color.Gray,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        ) }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 12.dp))
                .background(Color.Black.copy(alpha = 0.7f))
                .align(Alignment.BottomStart)
                .size(50.dp, 28.dp),
        ) {
            Text(
                text = age?.let { "$it+" } ?: "16+",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.Center)
            )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 12.dp))
                    .background(
                        when {
                            rating == null -> Color.Gray.copy(alpha = 0.7f)
                            rating >= 7 -> Color(50, 205, 50).copy(alpha = 0.8f)
                            rating >= 4.3 -> Color(95, 158, 160).copy(alpha = 0.8f)
                            else -> Color(139, 69, 19).copy(alpha = 0.7f)
                        },
                    )
                    .align(Alignment.TopEnd)
                    .size(40.dp, 25.dp),
            ) {
                Text(
                    text = rating?.let {
                        "$it"
                    } ?: "N/A",
                    color = when {
                        rating == null ->  Color.White
                            else -> Color.Black
                                 },
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CardRow(
    state: HomeState,
    count: Int,
    navigateToFilm: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            if (count == 1)
             state.popularFilms
            else if (count == 2 )
                state.bestFilms
            else if (count == 3)
                state.zombieFilms
            else state.comicsFilms
        ) { film ->
            Column {
                CardView(imageUrl = film.posterUrl,
                    rating = film.ratingKinopoisk,
                    navigateToFilm = navigateToFilm,
                    id = film.kinopoiskId,
                    age = film.ratingAgeLimits?.replace("age", ""))
                TitleFilms(title = film.nameRu?: film.nameOriginal.toString())
            }
        }
    }
}
//@Preview(showSystemUi = true)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen()
//}