package com.example.apiimdb.di


import com.example.apiimdb.domain.api.MoviesInteractor
import com.example.apiimdb.domain.api.NamesInteractor
import com.example.apiimdb.domain.db.HistoryInteractor
import com.example.apiimdb.domain.impl.HistoryInteractorImpl
import com.example.apiimdb.domain.impl.MoviesInteractorImpl
import com.example.apiimdb.domain.impl.NamesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }

    single<NamesInteractor> {
        NamesInteractorImpl(get())
    }

    single<HistoryInteractor> { //  зависимость HistoryInteractor
        HistoryInteractorImpl(get())
    }

//    single<SearchHistoryInteractor> {
//        SearchHistoryInteractorImpl(get())
//    } // не делал историю для этого проекта (но так в примере)

}