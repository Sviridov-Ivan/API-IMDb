package com.example.apiimdb.domain.api

import com.example.apiimdb.domain.models.Movie

interface MoviesInteractor { //это интерфейс, с помощью которого слой Presentation будет общаться со слоем Domain
    fun searchMovies(expression: String, consumer: MoviesConsumer)

    interface MoviesConsumer { // Для передачи результатов поискового запроса, который будет выполняться в отдельном потоке, нужен Callback. Его роль здесь выполняет MoviesConsumer, описанный в качестве вложенного интерфейса.
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }
}