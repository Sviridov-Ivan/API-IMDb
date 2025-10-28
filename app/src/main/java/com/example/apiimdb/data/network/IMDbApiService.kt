package com.example.apiimdb.data.network

import com.example.apiimdb.data.dto.cast.MovieCastResponse
import com.example.apiimdb.data.dto.details.MovieDetailsResponse
import com.example.apiimdb.data.dto.movies.MoviesSearchResponse
import com.example.apiimdb.data.dto.names.NamesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}") // k_zcuw1ytf API_KEY
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse> // передачи текста поискового запроса

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse> // получение деталей о фильме

    @GET("/en/API/FullCast/k_zcuw1ytf/{movie_id}")
    fun getFullCast(@Path("movie_id") movieId: String): Call<MovieCastResponse> // получение деталей о касте

    @GET("/en/API/SearchName/k_zcuw1ytf/{expression}")
    fun searchNames(@Path("expression") expression: String): Call<NamesSearchResponse> // получение имен актеров
}


