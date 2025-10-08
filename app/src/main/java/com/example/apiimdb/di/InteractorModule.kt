package com.example.apiimdb.di

import com.example.apiimdb.domain.api.MoviesInteractor
import com.example.apiimdb.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }

//    single<SearchHistoryInteractor> {
//        SearchHistoryInteractorImpl(get())
//    } // не делал историю для этого проекта (но так в примере)

}