package com.example.apiimdb.domain.api

import com.example.apiimdb.domain.models.Movie
import com.example.apiimdb.domain.models.MovieCast
import com.example.apiimdb.domain.models.MovieDetails

interface MoviesInteractor { //это интерфейс, с помощью которого слой Presentation будет общаться со слоем Domain
    fun searchMovies(expression: String, consumer: MoviesConsumer)
    fun getMovieDetails(movieId: String, consumer: MoviesDetailsConsumer)

    fun getMovieCast(movieId: String, consumer: MoviesCastConsumer) // запрос через Repository, и отправим полученный результат через Consumer


    interface MoviesConsumer { // Для передачи результатов поискового запроса, который будет выполняться в отдельном потоке, нужен Callback. Его роль здесь выполняет MoviesConsumer, описанный в качестве вложенного интерфейса.
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    interface MoviesDetailsConsumer {
        fun consume(movieDetails: MovieDetails?, errorMessage: String?)
    }

    interface MoviesCastConsumer {
        fun consume(movieCast: MovieCast?, errorMessage: String?)
    }
}