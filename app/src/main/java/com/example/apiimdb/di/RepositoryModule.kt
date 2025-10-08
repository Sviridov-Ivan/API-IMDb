package com.example.apiimdb.di

import com.example.apiimdb.data.MoviesRepositoryImpl
import com.example.apiimdb.domain.api.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(get())
    }

//    single<SearchHistoryRepository> {
//        SearchHistoryRepositoryImpl(get())
//    } // не делал историю для этого проекта (но так в примере)
}