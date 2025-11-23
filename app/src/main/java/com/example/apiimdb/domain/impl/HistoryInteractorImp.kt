package com.example.apiimdb.domain.impl

import com.example.apiimdb.domain.db.HistoryInteractor
import com.example.apiimdb.domain.db.HistoryRepository
import com.example.apiimdb.domain.models.Movie
import kotlinx.coroutines.flow.Flow

class HistoryInteractorImpl( // HistoryInteractorImpl получает зависимость HistoryRepository из Koin-модуля repositoryModule.
    private val historyRepository: HistoryRepository
): HistoryInteractor {

    override fun historyMovies(): Flow<List<Movie>> {
        return historyRepository.historyMovies() //Метод historyMovies() вызывает метод репозитория, который, в свою очередь, достаёт список сохранённых фильмов в базе данных
    }
}