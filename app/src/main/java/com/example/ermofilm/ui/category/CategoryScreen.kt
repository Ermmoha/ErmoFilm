package com.example.ermofilm.ui.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ermofilm.R
import com.example.ermofilm.ui.category.viewmodel.CategoryState
import com.example.ermofilm.ui.category.viewmodel.CategoryViewModel
import com.example.ermofilm.ui.navigation.NavigationDestination
import com.example.ermofilm.ui.selectcategory.SelectCategoryDestination.type

object CategoryDestination: NavigationDestination {
    override val title = "Категории"
    override val route = "Category"
}

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    navigateToSelectCategory: (Int) -> Unit
){
    val state by viewModel.state.collectAsState()
    Scaffold(
        containerColor = Color.Black
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item (
                span = { GridItemSpan(2) }
            ){
                Text(
                    text = "Жанры",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.logo7)),
                    fontSize = 35.sp,
                )
            }
            items(state.genreList) { genre ->
                Column {
                        CardCategory(
                            title = genre.genre ?: "Неизвестный жанр",
                            navigateToSelectCategory = navigateToSelectCategory,
                            genreId = genre.id,
                            state = state
                        )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardCategory(
    title: String,
    navigateToSelectCategory: (Int) -> Unit,
    genreId: Int,
    state: CategoryState
) {
    Card(
        onClick = {
            navigateToSelectCategory(genreId)
            type = "genres="
                  },
        modifier = Modifier
            .height(150.dp)
            .width(230.dp)
            .padding(3.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = when {
                    genreId == 1 -> painterResource(R.drawable.triller)
                    genreId == 2 -> painterResource(R.drawable.dramma)
                    genreId == 3 -> painterResource(R.drawable.kriminal)
                    genreId == 4 -> painterResource(R.drawable.melodramma)
                    genreId == 5 -> painterResource(R.drawable.detektiv)
                    genreId == 6 -> painterResource(R.drawable.fantastika)
                    genreId == 7 -> painterResource(R.drawable.prikluchenia)
                    genreId == 8 -> painterResource(R.drawable.biografy)
                    genreId == 9 -> painterResource(R.drawable.filmnuar)
                    genreId == 10 -> painterResource(R.drawable.western)

                    else -> painterResource(R.drawable.dog)
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(245, 195, 29).copy(alpha = 0.1f),
                                Color(245, 195, 29).copy(alpha = 0.4f),
                                Color(245, 195, 29).copy(alpha = 0.7f)
                            )
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    maxLines = 2,
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.logo7)),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .offset(x = 1.dp, y = 1.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = title,
                    color = Color.White,
                    maxLines = 2,
                    fontSize = 23.sp,
                    fontFamily = FontFamily(Font(R.font.logo7)),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}