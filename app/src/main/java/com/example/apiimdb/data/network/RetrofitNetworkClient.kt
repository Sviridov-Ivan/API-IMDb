package com.example.apiimdb.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.apiimdb.data.NetworkClient
import com.example.apiimdb.data.dto.cast.MovieCastRequest
import com.example.apiimdb.data.dto.details.MovieDetailsRequest
import com.example.apiimdb.data.dto.movies.MoviesSearchRequest
import com.example.apiimdb.data.dto.Response
import com.example.apiimdb.data.dto.names.NamesSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RetrofitNetworkClient(private val imdbService: IMDbApiService, private val context: Context) : NetworkClient {

    override suspend fun doRequestSuspend(dto: Any): Response { // реализовал отдельно только для Names
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }

        if ((dto !is NamesSearchRequest)
            && (dto !is MoviesSearchRequest)
            && (dto !is MovieDetailsRequest)
            && (dto !is MovieCastRequest)) {
            return Response().apply { resultCode = 400 }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is NamesSearchRequest -> imdbService.searchNames(dto.expression)
                    is MoviesSearchRequest -> imdbService.searchMovies(dto.expression)
                    is MovieDetailsRequest -> imdbService.getMovieDetails(dto.movieId)
                    is MovieCastRequest -> imdbService.getFullCast(dto.movieId)
                    else -> null
                } ?: return@withContext Response().apply { resultCode = 400 }

                // IMDb API errorMessage check
//                if (!response.errorMessage.isNullOrEmpty()) {
//                    return@withContext response.apply { resultCode = 500 }
//                }

                // успех
                response.apply { resultCode = 200 }

            } catch (e: Exception) {
                Response().apply { resultCode = 500 }
            }
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
