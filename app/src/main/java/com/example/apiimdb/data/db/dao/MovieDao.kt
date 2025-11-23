package com.example.apiimdb.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.apiimdb.data.db.entity.MovieEntity

@Dao
interface MovieDao { //определяет два метода для работы с таблицей movie_table базы данных в приложении

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>) // suspend обозначает асинхронный характер метода, то есть он может выполняться в фоновом потоке

    @Query("SELECT * FROM movie_table")
    suspend fun getMovies(): List<MovieEntity> // bозвращает список объектов MovieEntity из таблицы movie_table по запросу SELECT * FROM movie_table.
}