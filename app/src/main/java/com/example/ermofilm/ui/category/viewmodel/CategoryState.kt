package com.example.ermofilm.ui.category.viewmodel

import com.example.ermofilm.network.domain.model.Genre

data class CategoryState (
    val genreList: List<Genre> = emptyList(),
)