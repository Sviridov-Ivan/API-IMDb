package com.example.apiimdb.domain.impl

import com.example.apiimdb.domain.api.MoviesInteractor
import com.example.apiimdb.domain.api.MoviesRepository
import com.example.apiimdb.util.Resource
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            when(val resource = repository.searchMovies(expression)) {
                is Resource.Success -> { consumer.consume(resource.data, null) }
                is Resource.Error -> { consumer.consume(null, resource.message) }
            }
        }
    }

    override fun getMovieDetails(movieId: String, consumer: MoviesInteractor.MoviesDetailsConsumer) {
        executor.execute {
            when(val resource = repository.getMovieDetails(movieId)) {
                is Resource.Success -> { consumer.consume(resource.data, null) }
                is Resource.Error -> { consumer.consume(null, resource.message) }
            }
        }
    }

    override fun getMovieCast(movieId: String, consumer: MoviesInteractor.MoviesCastConsumer) {
        executor.execute {
            when(val resource = repository.getMovieCast(movieId)) {
                is Resource.Success -> { consumer.consume(resource.data, null) }
                is Resource.Error -> { consumer.consume(null, resource.message) }
            }
        }
    }
}


//  Однако Handler нельзя применять в Domain-слое, поскольку он — часть Android SDK.
//  И если вы не хотите использовать executor, то можно создавать поток обычным образом

/*class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        val t = Thread {
            consumer.consume(repository.searchMovies(expression))
        }
        t.start()
    }
} */