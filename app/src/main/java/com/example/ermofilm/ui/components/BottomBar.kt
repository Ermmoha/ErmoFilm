package com.example.ermofilm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    navigateToCategory: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToProfile:() -> Unit
    ){
    var selectedItemIndex by remember { mutableIntStateOf(2) }
    BottomAppBar(
        containerColor = Color.Black,
        modifier = Modifier
            .height(65.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            ) {
                NavigationBarItem(
                    selected = selectedItemIndex == 1,
                    onClick = {
                        selectedItemIndex = 1
                        navigateToCategory()
                              },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color(211, 211, 211),
                        indicatorColor = Color.Yellow
                    ),
                    icon = { 
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            modifier =
                            if (selectedItemIndex == 1)
                                Modifier.size(35.dp)
                            else Modifier.size(30.dp)
                        )
                    })
                NavigationBarItem(
                    selected = selectedItemIndex == 2,
                    onClick = {
                        selectedItemIndex = 2
                        navigateToHome()
                              },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color(211, 211, 211),
                        indicatorColor = Color.Yellow),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            modifier =
                            if (selectedItemIndex == 2)
                                Modifier.size(35.dp)
                            else Modifier.size(30.dp)
                    ) })
            NavigationBarItem(
                selected = selectedItemIndex == 3,
                onClick = {
                    selectedItemIndex = 3
                          navigateToProfile()
                          },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color(211, 211, 211),
                    indicatorColor = Color.Yellow),
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier =
                        if (selectedItemIndex == 3)
                        Modifier.size(35.dp)
                        else Modifier.size(30.dp)
                    ) })
            }
        }
}

//@Preview(showSystemUi = true)
//@Composable
//fun BottomBarPreview() {
//    BottomBar(index = 1)
//}