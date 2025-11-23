package com.example.apiimdb.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table") // kласс MovieEntity является сущностью таблицы movie_table
data class MovieEntity(
    @PrimaryKey // поле id имеет строковый тип и является первичным ключом таблицы (аннотация PrimaryKey)
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String,
)
