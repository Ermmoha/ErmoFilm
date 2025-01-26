package com.example.ermofilm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.ermofilm.network.di.NetworkModule
import com.example.ermofilm.ui.films.FilmScreen
import com.example.ermofilm.ui.films.viewmodel.FilmViewModel
import com.example.ermofilm.ui.home.HomeScreen
import com.example.ermofilm.ui.home.viewmodel.HomeViewModel
import com.example.ermofilm.ui.theme.ErmoFilmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ErmofilmApp()
        }
    }
}