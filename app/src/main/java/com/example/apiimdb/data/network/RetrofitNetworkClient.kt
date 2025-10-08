package com.example.apiimdb.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.resourceinspection.annotation.Attribute
import com.example.apiimdb.data.NetworkClient
import com.example.apiimdb.data.dto.MovieDto
import com.example.apiimdb.data.dto.MoviesSearchRequest
import com.example.apiimdb.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.contracts.contract

class RetrofitNetworkClient(private val imdbService: IMDbApiService, private val context: Context) : NetworkClient {
//    private val imdbBaseUrl = "https://tv-api.com"
//
//    private val retrofit =
//        Retrofit.Builder() // инициализируем экземпляр Retrofit и сервис для выполнения запросов с использованием интерфейса IMDbApiService
//            .baseUrl(imdbBaseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

    //private val imdbService = retrofit.create(IMDbApiService::class.java)

    override fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }
        if (dto is MoviesSearchRequest) { // проверяем, является ли переданный в него объект dto экземпляром какого-нибудь из классов, описывающих параметры запросов
            val response = imdbService.searchMovies(dto.expression)
                .execute() // передаём в метод searchMovies() строку с поисковым запросом и выполняем его, используя метод execute(). В результате в переменной resp будет находиться экземпляр класса Response<MoviesSearchResponse>.

            val body = response.body()

            return if (body != null) {
                body.apply { resultCode = response.code() }

            } else {
                Response().apply {
                    resultCode = response.code()
                } // Если же dto имеет неверный тип, то мы просто возвращаем экземпляр класса Response, указав код ответа 400 (Bad Request — неверный запрос
            }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}
