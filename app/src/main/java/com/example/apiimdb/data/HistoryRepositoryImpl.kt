package com.example.apiimdb.data

import com.example.apiimdb.data.converters.MovieDbConvertor
import com.example.apiimdb.data.db.AppDatabase
import com.example.apiimdb.data.db.entity.MovieEntity
import com.example.apiimdb.domain.db.HistoryRepository
import com.example.apiimdb.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl( // наследует интерфейс HistoryRepository и реализует метод historyMovies()
    private val appDatabase: AppDatabase,
    private val movieDbConvertor: MovieDbConvertor,
) : HistoryRepository {

    override fun historyMovies(): Flow<List<Movie>> = flow { // создаётся поток, который запрашивает данные о фильмах из базы данных с помощью метода getMovies() интерфейса MovieDao
        val movies = appDatabase.movieDao().getMovies()
        emit(convertFromMovieEntity(movies))
    }

    private fun convertFromMovieEntity(movies: List<MovieEntity>): List<Movie> { // данные преобразуются в объекты Movie с помощью конвертера MovieDbConvertor и возвращаются в виде потока.
        return movies.map { movie -> movieDbConvertor.map(movie) }
    }
}