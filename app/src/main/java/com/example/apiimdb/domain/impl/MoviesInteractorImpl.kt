package com.example.apiimdb.domain.impl

import com.example.apiimdb.domain.api.MoviesInteractor
import com.example.apiimdb.domain.api.MoviesRepository
import com.example.apiimdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.apiimdb.domain.models.Movie
import com.example.apiimdb.domain.models.MovieCast
import com.example.apiimdb.domain.models.MovieDetails

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {


    override fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>> {
        return repository.searchMovies(expression).map { result ->
            when(result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun getMovieDetails(movieId: String): Flow<Pair<MovieDetails?, String?>> {
        return repository.getMovieDetails(movieId).map { result ->
            when(result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun getMovieCast(movieId: String): Flow<Pair<MovieCast?, String?>> {
        return repository.getMovieCast(movieId).map { result ->
            when(result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
// вынос общей логики, но не применял
/*private fun <T> Flow<Resource<T>>.mapToPair(): Flow<Pair<T?, String?>> {
    return map { result ->
        when (result) {
            is Resource.Success -> Pair(result.data, null)
            is Resource.Error -> Pair(null, result.message)
        }
    }
}
class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    override fun searchMovies(expression: String) =
        repository.searchMovies(expression).mapToPair()

    override fun getMovieDetails(movieId: String) =
        repository.getMovieDetails(movieId).mapToPair()

    override fun getMovieCast(movieId: String) =
        repository.getMovieCast(movieId).mapToPair()
}*/
