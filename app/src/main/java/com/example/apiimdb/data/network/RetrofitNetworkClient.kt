package com.example.apiimdb.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.apiimdb.data.NetworkClient
import com.example.apiimdb.data.dto.MovieCastRequest
import com.example.apiimdb.data.dto.MovieDetailsRequest
import com.example.apiimdb.data.dto.MoviesSearchRequest
import com.example.apiimdb.data.dto.Response


class RetrofitNetworkClient(private val imdbService: IMDbApiService, private val context: Context) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }

        // Добавили ещё одну проверку
        if ((dto !is MoviesSearchRequest) && (dto !is MovieDetailsRequest) && (dto !is MovieCastRequest)) {
            return Response().apply { resultCode = 400 }
        }

        // Добавили в выражение when ещё одну ветку
        val response = when (dto) {
            is MoviesSearchRequest -> imdbService.searchMovies(dto.expression).execute()
            is MovieDetailsRequest -> imdbService.getMovieDetails(dto.movieId).execute()
            else -> imdbService.getFullCast((dto as MovieCastRequest).movieId).execute()
        }
        val body = response.body()
        return if (body != null) {
            body.apply { resultCode = response.code() }
        } else {
            Response().apply { resultCode = response.code() }
        }
    }

    
    /*override fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }
        if ((dto !is MoviesSearchRequest) && (dto !is MovieDetailsRequest)) {
            return Response().apply { resultCode = 400 }
        }

        // Логируем входящие данные
        when (dto) {
            is MoviesSearchRequest -> Log.d("NetworkClient", "Поиск фильмов: expression = ${dto.expression}")
            is MovieDetailsRequest -> Log.d("NetworkClient", "Детали фильма: movieId = ${dto.movieId}")
        }

        val response = if (dto is MoviesSearchRequest) {
            imdbService.searchMovies(dto.expression).execute()
        } else {
            imdbService.getMovieDetails((dto as MovieDetailsRequest).movieId).execute()
        }

        // Логи запроса
        val requestUrl = response.raw().request().url()
        Log.d("NetworkClient", "🌐 URL запроса: $requestUrl")

        if (dto is MovieDetailsRequest) {
            Log.d("NetworkClient", "🆔 Movie ID: ${dto.movieId}")
        }

        // Логируем URL и код ответа
        Log.d("NetworkClient", "URL: ${response.raw().request().url().toString()}")
        Log.d("NetworkClient", "Код ответа: ${response.code()}")

        val body = response.body()
        return if (body != null) {
            Log.d("NetworkClient", "Тело ответа: $body")
            body.apply { resultCode = response.code() }
        } else {

            val errorBody = response.errorBody()?.string()

            Response().apply { resultCode = response.code() }
        }
    }*/

    /*override fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 } // Нет соединения
        }
//        if (dto is MoviesSearchRequest) { // проверяем, является ли переданный в него объект dto экземпляром какого-нибудь из классов, описывающих параметры запросов
//            val response = imdbService.searchMovies(dto.expression)
//                .execute() // передаём в метод searchMovies() строку с поисковым запросом и выполняем его, используя метод execute(). В результате в переменной resp будет находиться экземпляр класса Response<MoviesSearchResponse>.
//
//            val body = response.body()
//
//            return if (body != null) {
//                body.apply { resultCode = response.code() }
//
//            } else {
//                Response().apply {
//                    resultCode = response.code()
//                } // Если же dto имеет неверный тип, то мы просто возвращаем экземпляр класса Response, указав код ответа 400 (Bad Request — неверный запрос
//            }
//        } else {
//            return Response().apply { resultCode = 400 }
//        }
        return when (dto) {

            is MoviesSearchRequest -> { // проверяем, является ли переданный в него объект dto экземпляром какого-нибудь из классов, описывающих параметры запросов
                val response = imdbService.searchMovies(dto.expression)
                    .execute() // передаём в метод searchMovies() строку с поисковым запросом и выполняем его, используя метод execute(). В результате в переменной resp будет находиться экземпляр класса Response<MoviesSearchResponse>.
                val body = response.body()

                if (body != null) {
                    body.apply { resultCode = response.code() }

                } else {
                    Response().apply {
                        resultCode = response.code() } // Если же dto имеет неверный тип, то мы просто возвращаем экземпляр класса Response, указав код ответа 400 (Bad Request — неверный запрос
                }
            }

            is MovieDetailsRequest -> {
                val response = imdbService.getMovieDetails(dto.movieId).execute()

                // Показываем адрес, на который реально ушёл запрос
                Log.d("NetworkClient", "URL: ${response.raw().request().url().toString()}")

                // Показываем код ответа (200, 404, 401 и т.д.)
                Log.d("NetworkClient", "Код ответа: ${response.code()}")

                val body = response.body()
                if (body != null) {
                    Log.d("NetworkClient", "Тело ответа: $body")
                    body.apply { resultCode = response.code() }
                } else {
                    // Если тело пустое — покажем текст ошибки, который вернул сервер
                    val errorText = response.errorBody()?.string()
                    Log.e("NetworkClient", "Ошибка от сервера: $errorText")
                    Response().apply { resultCode = response.code() }
                }
            }

            else -> Response().apply { resultCode = 400 } // Неверный тип запроса
        }
    }*/
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
