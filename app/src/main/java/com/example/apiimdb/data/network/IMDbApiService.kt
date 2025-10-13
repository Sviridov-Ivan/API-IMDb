package com.example.apiimdb.data.network

import com.example.apiimdb.data.dto.MovieDetailsResponse
import com.example.apiimdb.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}") // k_zcuw1ytf API_KEY
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse> // передачи текста поискового запроса

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse>
}


