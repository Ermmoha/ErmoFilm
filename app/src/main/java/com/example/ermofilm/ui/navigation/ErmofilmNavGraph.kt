package com.example.ermofilm.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ermofilm.ui.category.CategoryDestination
import com.example.ermofilm.ui.category.CategoryScreen
import com.example.ermofilm.ui.category.viewmodel.CategoryViewModel
import com.example.ermofilm.ui.components.BottomBar
import com.example.ermofilm.ui.components.TopBar
import com.example.ermofilm.ui.films.FilmDestination
import com.example.ermofilm.ui.films.FilmScreen
import com.example.ermofilm.ui.films.viewmodel.FilmViewModel
import com.example.ermofilm.ui.home.HomeDestination
import com.example.ermofilm.ui.home.HomeScreen
import com.example.ermofilm.ui.home.viewmodel.HomeViewModel
import com.example.ermofilm.ui.search.SearchDestination
import com.example.ermofilm.ui.search.SearchScreen
import com.example.ermofilm.ui.search.viewmodel.SearchViewModel
import com.example.ermofilm.ui.selectcategory.SelectCategoryDestination
import com.example.ermofilm.ui.selectcategory.SelectCategoryScreen
import com.example.ermofilm.ui.selectcategory.viewmodel.SelectedCategoryViewModel

@Composable
fun ErmofilmNavGraph(
    navController: NavHostController
){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val searchViewModel = hiltViewModel<SearchViewModel>()

    Scaffold(
            topBar = {
                TopBar(
                    navigateToBack = { navController.popBackStack() },
                    navigateToSearch = {
                        navController.navigate(SearchDestination.route)
                    },
                    searchValue = searchViewModel.state.collectAsState().value.searchQuery,
                    onSearchValueChange = { searchViewModel.updateSearchQuery(it) },
                    isSearching = currentRoute == SearchDestination.route,
                    onDone = { searchViewModel.getSearchFilm() }
                ) },
            bottomBar = {
                if (currentRoute == HomeDestination.route || currentRoute == CategoryDestination.route)
                BottomBar(
                    navigateToCategory = {
                        navController.navigate(CategoryDestination.route)
                    },
                    navigateToHome = {
                        navController.navigate(HomeDestination.route)
                    },
                    navigateToSearch = {
                        navController.navigate(SearchDestination.route)
                    }
                ) }
        ) { contentPadding ->
            NavHost(
                navController = navController,
                startDestination = CategoryDestination.route,
                modifier = Modifier.padding(contentPadding)
            ) {
                composable(route = HomeDestination.route) {
                    val homeViewModel = hiltViewModel<HomeViewModel>()
                    HomeScreen(
                        viewModel = homeViewModel,
                        navigateToFilm = {
                            navController.navigate(FilmDestination.route)
                            FilmDestination.filmId = it
                        }
                    )
                }
                composable(route = FilmDestination.route) { 
                    val filmViewModel = hiltViewModel<FilmViewModel>()
                    FilmScreen(
                        viewModel = filmViewModel,
                        navigateToFilm = {
                            navController.navigate(FilmDestination.route)
                            FilmDestination.filmId = it
                        }
                    )
                }
                composable(route = CategoryDestination.route) {
                    val categoryViewModel = hiltViewModel<CategoryViewModel>()
                    CategoryScreen(
                        viewModel = categoryViewModel,
                        navigateToSelectCategory = {
                            navController.navigate(SelectCategoryDestination.route)
                            SelectCategoryDestination.genreId = it
                        }
                    )
                }
                composable(route = SearchDestination.route) {
                    SearchScreen(
                        viewModel = searchViewModel,
                        navigateToFilm = {
                            navController.navigate(FilmDestination.route)
                            FilmDestination.filmId = it
                        }
                    )
                }
                composable(route = SelectCategoryDestination.route){
                    val selectedCategoryViewModel = hiltViewModel<SelectedCategoryViewModel>()
                    SelectCategoryScreen(
                        viewModel = selectedCategoryViewModel,
                        navigateToFilm = {
                            navController.navigate(FilmDestination.route)
                            FilmDestination.filmId = it
                        }
                    )
                }
            }
        }
    }