package com.example.apiimdb.data

import com.example.apiimdb.data.converter.MovieCastConverter
import com.example.apiimdb.data.dto.cast.MovieCastRequest
import com.example.apiimdb.data.dto.cast.MovieCastResponse
import com.example.apiimdb.data.dto.details.MovieDetailsRequest
import com.example.apiimdb.data.dto.details.MovieDetailsResponse
import com.example.apiimdb.data.dto.movies.MoviesSearchRequest
import com.example.apiimdb.data.dto.movies.MoviesSearchResponse
import com.example.apiimdb.domain.api.MoviesRepository
import com.example.apiimdb.domain.models.Movie
import com.example.apiimdb.domain.models.MovieCast
import com.example.apiimdb.util.Resource
import com.example.apiimdb.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(private val networkClient: NetworkClient,
                           // Добавили конвертер
                           private val movieCastConverter: MovieCastConverter,
    ) : MoviesRepository { // В конструктор класса передаётся сетевой клиент, и конкретная реализация будет уточняться при инициализации. суффикс Impl — сокращение от Implementation — реализация

    override fun searchMovies(expression: String): Flow<Resource<List<Movie>>> = flow { // выполняем запрос, передав в соответствующий метод сетевого клиента экземпляр класса MoviesSearchRequest с текстом поискового запроса
        val response = networkClient.doRequestSuspend(MoviesSearchRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                val movieResponse = response as MoviesSearchResponse
                val data = movieResponse.results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)
                }
                emit(Resource.Success(data))
            }
            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    // 🔹 Новый метод для получения детальной информации о фильме
    override fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>> = flow {
        val response = networkClient.doRequestSuspend(MovieDetailsRequest(movieId))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                val r =response as MovieDetailsResponse
                val data =  MovieDetails(
                            id = r.id,
                            title = r.title,
                            imDbRating = r.imDbRating,
                            year = r.year,
                            countries = r.countries,
                            genres = r.genres,
                            directors = r.directors,
                            writers = r.writers,
                            stars = r.stars,
                            plot = r.plot)

                    emit(Resource.Success(data))
                }
            else -> {
                emit(Resource.Error("Ошибка сервера"))

            }
        }
    }

    // Добавил новый метод для получения cast
    override fun getMovieCast(movieId: String): Flow<Resource<MovieCast>> = flow {
        // Поменял объект dto на нужный Request-объект
        val response = networkClient.doRequestSuspend(MovieCastRequest(movieId))
        when(response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к Интернету"))
            }
            200 -> {
                // Kонвертация!
                // используем конвертер вместо
                // прямой конвертации
                //with(response as MovieCastResponse) {
                val castResponse = response as MovieCastResponse
                val data = movieCastConverter.convert(castResponse)
                emit(Resource.Success(data))
            }
            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }
}