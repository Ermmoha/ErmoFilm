package com.example.ermofilm.ui.films

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ermofilm.R
import com.example.ermofilm.network.domain.model.Actor
import com.example.ermofilm.network.domain.model.VideoSite
import com.example.ermofilm.ui.films.viewmodel.FilmState
import com.example.ermofilm.ui.films.viewmodel.FilmViewModel
import com.example.ermofilm.ui.home.CardView
import com.example.ermofilm.ui.home.TitleFilms
import com.example.ermofilm.ui.navigation.NavigationDestination
import com.example.ermofilm.ui.selectcategory.SelectCategoryDestination.type

object FilmDestination: NavigationDestination {
    var filmId = 0
    override val title = "Фильм"
    override val route = "Film"
}

@Composable
fun FilmScreen(
    viewModel: FilmViewModel,
    navigateToFilm: (Int) -> Unit,
    navigateToSelectCategory:(Int) -> Unit

){
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showTrailerDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Black
    )  {    paddingValues ->

        if(showDialog)
            CustomDialogExample(
                onDismissRequest = { showDialog = false },
                state = state
            )

        if (showTrailerDialog) {
            CustomTrailerDialog(
                onDismissRequest = { showTrailerDialog = false },
                state = state
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
            ) {
            state.filmInfo?.let { Banner(imageUrl = it.posterUrl) }
            state.filmInfo?.nameRu?.let {
                Text(
                    text = it,
                    color = Color.White,
                    maxLines = 3,
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.logo3)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                state.filmInfo?.nameOriginal?.let {
                    InfoText(title = it)
                }
                InfoText(title = state.filmInfo?.year.toString())
                Row {
                    InfoText(title = state.filmInfo?.filmLength.toString() + " мин,")
                    InfoText(title = state.filmInfo?.ratingAgeLimits.toString() + "+")
                }
                Spacer(modifier = Modifier.size(15.dp))
            }

            FilmWatchButtons(
                onButtonClick = {
                    viewModel.getCinemaInfo(state.filmInfo?.kinopoiskId ?: 0)
                    showDialog = true
                },
                onTrailerClick = {
                    viewModel.getTrailerInfo(state.filmInfo?.kinopoiskId ?: 0)
                    showTrailerDialog = true
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val ratingKinopoisk = state.filmInfo?.ratingKinopoisk
                    Text(
                        text = if (ratingKinopoisk != null)
                        ratingKinopoisk.toString()
                        else "N/A",
                        fontWeight = FontWeight.Bold,
                        color = when {
                            ratingKinopoisk == null -> Color.White
                            ratingKinopoisk >= 7 -> Color(50, 205, 50)
                            ratingKinopoisk >= 4.9 -> Color(95, 158, 160)
                            else -> Color(139, 69, 19)
                        },
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Кинопоиск",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 16.sp
                    )

                }
                Column(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val ratingImdb = state.filmInfo?.ratingImdb
                    Text(
                        text = if (ratingImdb != null)
                        ratingImdb.toString()
                        else "N/A",
                        fontWeight = FontWeight.Bold,
                        color = when {
                            ratingImdb == null -> Color.White
                            ratingImdb >= 7 -> Color(50, 205, 50).copy(alpha = 0.8f)
                            ratingImdb >= 4.3 -> Color(95, 158, 160).copy(alpha = 0.8f)
                            else -> Color(139, 69, 19).copy(alpha = 0.7f)
                        },
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Imdb",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
            GenreRow(state, navigateToSelectCategory = navigateToSelectCategory)
            state.filmInfo?.slogan?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = it,
                        color = Color.Yellow,
                        modifier = Modifier
                            .align(Alignment.Center),
                        fontSize = 26.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.logo7))
                    )
                }
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.White,
                modifier = Modifier.padding(40.dp,0.dp)
            )
            state.filmInfo?.description?.let {
                Text(
                    text = it,
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp, start = 5.dp, bottom = 20.dp),
                    fontSize = 21.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.logo7))
                )
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.White,
                modifier = Modifier.padding(40.dp,0.dp)
            )
            ActorsRow(state = state)
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.White,
                modifier = Modifier.padding(40.dp,0.dp)
            )
            SequelsAndPrequelsRow(state = state, navigateToFilm = navigateToFilm)
        }

    }
}

@Composable
fun FilmWatchButtons(
    onButtonClick: () -> Unit,
    onTrailerClick: () -> Unit
){
    Column(modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .height(50.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                Modifier.size(30.dp)
            )
            Text(
                text = "Посмотреть фильм",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.logo3)),
                modifier = Modifier.padding(10.dp, 0.dp)

            )

        }

        Spacer(modifier = Modifier.size(13.dp))

        Button(
            onClick = onTrailerClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(70, 130, 180),
                contentColor = Color.White
            ),
            modifier = Modifier
                .height(50.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                Modifier.size(30.dp)
            )
            Text(
                text = "Трейлер",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.logo3)),
                modifier = Modifier.padding(10.dp, 0.dp)

            )

        }
        Spacer(modifier = Modifier.size(20.dp))
    }
}
@Composable
fun CustomDialogExample(
    onDismissRequest: () -> Unit,
    state: FilmState
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(500.dp,600.dp)
                .border(2.dp, Color.White, RoundedCornerShape(16.dp))
                .background(
                    Color.Black,
                )
                .padding(16.dp)
        ) {
            val context = LocalContext.current
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Доступные источники",
                    fontSize = 25.sp,
                    color = Color.Yellow,
                    fontFamily = FontFamily(Font(R.font.logo8)),
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.White,
                    modifier = Modifier.padding(40.dp,10.dp)
                )
                Spacer(Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .size(500.dp,370.dp)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (state.cinema.isNotEmpty()) {
                        items(state.cinema) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            data = Uri.parse(it.url)
                                        }
                                        context.startActivity(intent)
                                    }
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("https://images.weserv.nl/?url=${it.logoUrl}&output=jpg")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "logo",
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(end = 8.dp)
                                        .clip(CircleShape),
                                )
                                Text(
                                    text = it.platform,
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.logo7)),
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        item { // Обработка пустого списка
                            Text(
                                text = "Нет данных",
                                color = Color.Gray,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.White,
                    modifier = Modifier.padding(40.dp,10.dp)
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Yellow,
                        contentColor = Color.Black
                    ),
                    onClick = onDismissRequest
                ) {
                    Text("Закрыть")
                }
            }
        }
    }
}

@Composable
fun InfoText(
    title: String?
) {
    Text(
        text = if (!title.isNullOrBlank() && title != "null") {
            title.replace("age", " ")
        } else {
            "Неизвестно"
        },
        color = Color.Gray,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun ActorsRow(
    state: FilmState
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Съёмочная группа",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow {
            items(state.actors) { actor ->
                ActorCard(actor = actor)
            }
        }
    }
}

@Composable
fun ActorCard(
    actor: Actor
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
            AsyncImage(
                model = actor.posterUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Text(
                text = actor.nameRu ?: actor.nameEn ?: "Неизвестно",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = actor.professionText.replace("ы", " "),
                color = Color.Gray
            )

    }
}


@Composable
fun Banner(
    imageUrl: String?,
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun GenreTexts(
    genre: String,
    navigateToSelectCategory: (Int) -> Unit,
    id: Int
){

    TextButton(
        onClick = { navigateToSelectCategory(id) },
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color(169, 169, 169),
        )
    ) {
        Text(
            text = genre,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.logo8)),
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
fun GenreRow(
    state: FilmState,
    navigateToSelectCategory: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Log.d("GenreRow", "Genres: ${state.filmInfo?.genres}")
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Жанр: ",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.logo8)),
                textAlign = TextAlign.Start,
            )
            LazyRow(
            ) {
                state.filmInfo?.genres?.let { genres ->
                    items(genres) { genre ->
                        val genreId = state.genreMap[genre.genre] ?: 0
                        Log.d("GenreRow", "Genre ID: ${genre.id}")
                        GenreTexts(
                            genre.genre ?: "Жанр не указан",
                            id = genreId,
                            navigateToSelectCategory = {
                                navigateToSelectCategory(genreId)
                                type = "genres="
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Страна: ",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.logo8)),
                textAlign = TextAlign.Start,
            )
            LazyRow(
            ) {
                state.filmInfo?.countries?.let { countries ->
                    items(countries) { country ->
                        val countryId = state.countryMap[country.country] ?: 0
                        Log.d("GenreRow", "Country Name: , Country ID: $countryId")
                        GenreTexts(
                            country.country ?: "Страна не указана",
                            id = countryId,
                            navigateToSelectCategory = {
                                navigateToSelectCategory(countryId)
                                type = "countries="
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SequelsAndPrequelsRow(
    state: FilmState,
    navigateToFilm: (Int) -> Unit
) {
    Column {

        if (state.sequelsAndPrequels.isNullOrEmpty()) {
            Text(
                text = "Не имеет сиквелов или приквелов",
                color = Color.Gray,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 30.dp)
            )
        } else {
            Text(
                text = "Сиквелы и приквелы",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 20.dp)
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    state.sequelsAndPrequels
                ) {
                    Column {
                        CardView(
                            imageUrl = it.posterUrl,
                            navigateToFilm = navigateToFilm,
                            age = " ",
                            id = it.filmId,
                            rating = 100.5
                        )
                        TitleFilms(title = it.nameRu ?: it.nameOriginal.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTrailerDialog(
    onDismissRequest: () -> Unit,
    state: FilmState
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(500.dp,600.dp)
                .border(2.dp, Color.White, RoundedCornerShape(16.dp))
                .background(Color.Black)
                .padding(16.dp)
        ) {
            val context = LocalContext.current
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Трейлеры",
                    fontSize = 25.sp,
                    color = Color.Yellow,
                    fontFamily = FontFamily(Font(R.font.logo8)),
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.White,
                    modifier = Modifier.padding(40.dp, 10.dp)
                )
                Spacer(Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .size(500.dp,370.dp)
                        .padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (state.trailer.isNotEmpty()) {
                        items(state.trailer) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            data = Uri.parse(it.url)
                                        }
                                        context.startActivity(intent)
                                    }
                            ) {
                                Image(
                                    painter = if (it.site == VideoSite.YOUTUBE || it.site == VideoSite.UNKNOWN)
                                        painterResource(R.drawable.youtube)
                                    else if (it.site == VideoSite.KINOPOISK_WIDGET)
                                        painterResource(R.drawable.kinopoisk)
                                    else
                                        painterResource(R.drawable.yandexdisk),
                                    contentDescription = "logo",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(50.dp,45.dp)
                                        .padding(end = 8.dp)
                                        .clip(CircleShape),
                                )
                                Text(
                                    text = it.name,
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.logo7)),
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    } else {
                        item {
                            Text(
                                text = "Нет доступных трейлеров",
                                color = Color.Gray,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.White,
                    modifier = Modifier.padding(40.dp, 10.dp)
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Yellow,
                        contentColor = Color.Black
                    ),
                    onClick = onDismissRequest
                ) {
                    Text("Закрыть")
                }
            }
        }
    }
}








