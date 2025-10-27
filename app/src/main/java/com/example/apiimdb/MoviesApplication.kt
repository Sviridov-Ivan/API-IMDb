package com.example.apiimdb

import android.app.Application
import com.example.apiimdb.di.dataModule
import com.example.apiimdb.di.interactorModule
import com.example.apiimdb.di.navigationModule
import com.example.apiimdb.di.repositoryModule
import com.example.apiimdb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule,
                navigationModule
            )
        }
    }
}