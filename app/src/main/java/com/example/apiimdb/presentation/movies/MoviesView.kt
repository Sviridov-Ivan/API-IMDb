package com.example.apiimdb.presentation.movies

import com.example.apiimdb.domain.models.Movie
import com.example.apiimdb.ui.movies.models.MoviesState

interface MoviesView {

    // Методы, меняющие внешний вид экрана

    fun render(state: MoviesState)

    // Методы одноразовых событий

    fun showToast(additionalMessage: String)

}