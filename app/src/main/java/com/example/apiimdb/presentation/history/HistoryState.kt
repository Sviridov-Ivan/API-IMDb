package com.example.apiimdb.presentation.history

import com.example.apiimdb.domain.models.Movie

sealed interface HistoryState {

    object Loading : HistoryState // данные ещё не загружены

    data class Content(
        val movies: List<Movie> // состояние Content содержит список фильмов
    ) : HistoryState

    data class Empty(
        val message: String // данные отсутствуют или недоступны
    ) : HistoryState
}