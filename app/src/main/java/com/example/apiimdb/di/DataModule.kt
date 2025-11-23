package com.example.apiimdb.di

import android.content.Context
import androidx.room.Room
import com.example.apiimdb.data.NetworkClient
import com.example.apiimdb.data.db.AppDatabase
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
            .baseUrl("https://tv-api.com/") // добавляем /en/API/   для  деталей https://tv-api.com/en/API/
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

    single { // инициализацию базы данных
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db") //единственный экземпляр объекта, который создаёт экземпляр класса RoomDatabase и связывает его с именем базы данных database.db
            .build()
    }

}