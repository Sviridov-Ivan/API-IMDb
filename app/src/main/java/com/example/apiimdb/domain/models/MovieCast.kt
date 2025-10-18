package com.example.apiimdb.domain.models

data class MovieCast( // модель получилась значительно проще сетевой в результате удаления лишних прослоек (ActorResponse, DirectorResponse и прочих)
    val imdbId: String,
    val fullTitle: String,
    val directors: List<MovieCastPerson>,
    val writers: List<MovieCastPerson>,
    val actors: List<MovieCastPerson>,
    val others: List<MovieCastPerson>,
)

data class MovieCastPerson( //схлопывания всех данных разных участников в одну модель MovieCastPerson
    val id: String,
    val name: String,
    val description: String,
    val image: String?,
)
