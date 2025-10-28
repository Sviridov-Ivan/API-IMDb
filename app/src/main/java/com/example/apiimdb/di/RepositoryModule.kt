package com.example.apiimdb.di

import com.example.apiimdb.data.MoviesRepositoryImpl
import com.example.apiimdb.data.NamesRepositoryImpl
import com.example.apiimdb.data.converter.MovieCastConverter
import com.example.apiimdb.domain.api.MoviesRepository
import com.example.apiimdb.domain.api.NamesRepository
import org.koin.dsl.module

val repositoryModule = module {

    // Добавили фабрику для конвертера
    factory { MovieCastConverter() }


    single<MoviesRepository> {
        // Добавили ещё один `get()`, чтобы количество
        // аргументов совпадало
        MoviesRepositoryImpl(get(),get())
    }

    single<NamesRepository> {
        NamesRepositoryImpl(get())
    }

}