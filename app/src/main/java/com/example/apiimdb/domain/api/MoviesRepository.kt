package com.example.apiimdb.domain.api

import com.example.apiimdb.domain.models.Movie
import com.example.apiimdb.domain.models.MovieDetails
import com.example.apiimdb.util.Resource

interface MoviesRepository { // Некий Interactor (Use Case) сможет использовать этот интерфейс, чтобы получить список фильмов по поисковому запросу. интерфейс для связи слоя Domain со слоем Data
    fun searchMovies(expression: String): Resource<List<Movie>>

    fun getMovieDetails(movieId: String): Resource<MovieDetails> // используем Response напрямую поскольку все поля, необходимые слою Domain, находятся непосредственно в теле ответа
}