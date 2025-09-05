package com.example.apiimdb.data.dto

import com.example.apiimdb.domain.models.Movie

class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>) : Response() // в 15 спринте для создания чистой архитектуры заменил <Movie> на <MovieDto>, чтобы соблюсти инверсию зависимостей data/domain и еще наследовал Response()