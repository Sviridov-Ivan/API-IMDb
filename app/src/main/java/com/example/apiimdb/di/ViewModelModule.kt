package com.example.apiimdb.di

import com.example.apiimdb.presentation.about.AboutViewModel
import com.example.apiimdb.presentation.cast.MoviesCastViewModel
import com.example.apiimdb.presentation.movies.MoviesViewModel
import com.example.apiimdb.presentation.poster.PosterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MoviesViewModel(get(), get())
    }

    viewModel { (url: String) ->
        PosterViewModel(url)
    }

    viewModel { (movieId: String) ->
        AboutViewModel(movieId, get())
    }

    viewModel { (movieId: String) ->
        MoviesCastViewModel(movieId, get())
    }

}