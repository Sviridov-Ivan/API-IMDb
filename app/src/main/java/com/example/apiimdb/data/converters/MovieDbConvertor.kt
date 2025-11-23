package com.example.apiimdb.data.converters

import com.example.apiimdb.data.db.entity.MovieEntity
import com.example.apiimdb.domain.models.Movie

class MovieDbConvertor { // конвертер для преобразования объектов типа Movie в тип базы данных MovieEntityи из типа БД в экземпляр класса Domain-слоя — Movie.

    fun map(movie: Movie): MovieEntity {
        return MovieEntity(movie.id, movie.resultType, movie.image, movie.title, movie.description)
    }

    fun map(movie: MovieEntity): Movie {
        return Movie(movie.id, movie.resultType, movie.image, movie.title, movie.description)
    }
}