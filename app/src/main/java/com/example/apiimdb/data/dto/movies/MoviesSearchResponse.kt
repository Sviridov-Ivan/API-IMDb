package com.example.apiimdb.data.dto.movies

import com.example.apiimdb.data.dto.Response

class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>) : Response() // в 15 спринте для создания чистой архитектуры заменил <Movie> на <MovieDto>, чтобы соблюсти инверсию зависимостей data/domain и еще наследовал Response()