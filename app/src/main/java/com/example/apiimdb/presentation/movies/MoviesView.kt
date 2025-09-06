package com.example.apiimdb.presentation.movies

import com.example.apiimdb.domain.models.Movie
import com.example.apiimdb.ui.movies.models.MoviesState
import moxy.MvpView
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy

interface MoviesView : MvpView {

    // Методы, меняющие внешний вид экрана

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun render(state: MoviesState)

    // Методы одноразовых событий
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(additionalMessage: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openPoster(posterUrl: String)

}