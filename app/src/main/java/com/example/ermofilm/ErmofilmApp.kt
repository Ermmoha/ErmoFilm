package com.example.ermofilm

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ermofilm.ui.navigation.ErmofilmNavGraph

@Composable
fun ErmofilmApp(
    navController: NavHostController = rememberNavController()
){
    ErmofilmNavGraph(
        navController = navController
    )
}