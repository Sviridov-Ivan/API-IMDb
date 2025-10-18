package com.example.apiimdb.data

import com.example.apiimdb.data.converter.MovieCastConverter
import com.example.apiimdb.data.dto.MovieCastRequest
import com.example.apiimdb.data.dto.MovieCastResponse
import com.example.apiimdb.data.dto.MovieDetailsRequest
import com.example.apiimdb.data.dto.MovieDetailsResponse
import com.example.apiimdb.data.dto.MoviesSearchRequest
import com.example.apiimdb.data.dto.MoviesSearchResponse
import com.example.apiimdb.domain.api.MoviesRepository
import com.example.apiimdb.domain.models.Movie
import com.example.apiimdb.domain.models.MovieCast
import com.example.apiimdb.util.Resource
import com.example.apiimdb.domain.models.MovieDetails

class MoviesRepositoryImpl(private val networkClient: NetworkClient,
                           // Добавили конвертер
                           private val movieCastConverter: MovieCastConverter,
    ) : MoviesRepository { // В конструктор класса передаётся сетевой клиент, и конкретная реализация будет уточняться при инициализации. суффикс Impl — сокращение от Implementation — реализация

    override fun searchMovies(expression: String): Resource<List<Movie>> { // выполняем запрос, передав в соответствующий метод сетевого клиента экземпляр класса MoviesSearchRequest с текстом поискового запроса
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)
                })
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    // 🔹 Новый метод для получения детальной информации о фильме
    override fun getMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                with(response as MovieDetailsResponse) {
                    Resource.Success(
                        MovieDetails(
                            id = id,
                            title = title,
                            imDbRating = imDbRating,
                            year = year,
                            countries = countries,
                            genres = genres,
                            directors = directors,
                            writers = writers,
                            stars = stars,
                            plot = plot,
                        )
                    )
                }
            }

            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }

    // Добавил новый метод для получения cast
    override fun getMovieCast(movieId: String): Resource<MovieCast> {
        // Поменял объект dto на нужный Request-объект
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        return when(response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к Интернету")
            }
            200 -> {
                // Kонвертация!
                // используем конвертер вместо
                // прямой конвертации
                //with(response as MovieCastResponse) {
                    Resource.Success(
                        data = movieCastConverter.convert(response as MovieCastResponse)
                    )
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}