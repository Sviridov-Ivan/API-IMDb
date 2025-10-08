package com.example.apiimdb.util

import android.content.Context
import com.example.apiimdb.data.MoviesRepositoryImpl
import com.example.apiimdb.data.network.RetrofitNetworkClient
import com.example.apiimdb.domain.api.MoviesInteractor
import com.example.apiimdb.domain.api.MoviesRepository
import com.example.apiimdb.domain.impl.MoviesInteractorImpl
import com.example.apiimdb.ui.movies.MoviesAdapter

object Creator {
//    private fun getMoviesRepository(context: Context): MoviesRepository { // вернёт экземпляр MoviesRepositoryImpl
//        return MoviesRepositoryImpl(RetrofitNetworkClient(context)) // конструктор которому в качестве сетевого клиента будет передан RetrofitNetworkClient
//    }
//
//    fun provideMoviesInteractor(context: Context): MoviesInteractor { // Он вернёт экземпляр MoviesInteractorImpl
//            return MoviesInteractorImpl(getMoviesRepository(context)) // в котором будет использоваться репозиторий с характеристиками, описанными в методе выше
//    }

    /*fun provideMoviesSearchPresenter(moviesView: MoviesView, context: Context, adapter: MoviesAdapter): MoviesSearchPresenter {
        return MoviesSearchPresenter(view = moviesView, context = context)
    }*/

    /*fun providePosterPresenter(posterView: PosterView, imageUrl: String): PosterPresenter {
        return PosterPresenter(posterView, imageUrl)
    }*/

}