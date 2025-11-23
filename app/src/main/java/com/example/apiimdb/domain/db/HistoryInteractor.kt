package com.example.apiimdb.domain.db

import com.example.apiimdb.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {

    fun historyMovies(): Flow<List<Movie>> // определяется один метод historyMovies(), возвращающий Flow со списком данных о фильмах
}