package com.example.apiimdb.di

import android.content.Context
import com.example.apiimdb.data.NetworkClient
import com.example.apiimdb.data.local.SharedPreferencesSearchHistoryStorage
import com.example.apiimdb.data.network.IMDbApiService
import com.example.apiimdb.data.network.RetrofitNetworkClient
import com.example.apiimdb.data.network.SearchHistoryStorage
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<IMDbApiService> {
        Retrofit.Builder()
            .baseUrl("https://tv-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IMDbApiService::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    }

    factory { Gson() }

    single<SearchHistoryStorage> {
        SharedPreferencesSearchHistoryStorage(get(), get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }


}