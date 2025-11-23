package com.example.apiimdb.domain.db

import com.example.apiimdb.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun historyMovies(): Flow<List<Movie>> // Flow представляет собой поток элементов, который можно использовать для обработки и представления данных в приложении.
}