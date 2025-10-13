package com.example.apiimdb.domain.models

data class MovieDetails( //data-класс, чтобы не тянуть Response (с resultCode) из data-слоя.
    val id: String?,
    val title: String?,
    val imDbRating: String?,
    val year: String?,
    val countries: String?,
    val genres: String?,
    val directors: String?,
    val writers: String?,
    val stars: String?,
    val plot: String?
)