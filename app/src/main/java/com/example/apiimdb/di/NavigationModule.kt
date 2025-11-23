package com.example.apiimdb.di

import com.example.apiimdb.ui.core.navigation.Router
import com.example.apiimdb.ui.core.navigation.RouterImpl
import org.koin.dsl.module


val navigationModule = module {
    val router = RouterImpl()

    single<Router> { router }

    single { router.navigatorHolder }

}