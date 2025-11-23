package com.example.apiimdb.presentation.about

import com.example.apiimdb.domain.models.MovieDetails

sealed interface AboutState {

    data class Content(val movie: MovieDetails) : AboutState

    data class Error(val message: String) : AboutState
}