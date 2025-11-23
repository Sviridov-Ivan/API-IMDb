package com.example.apiimdb.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apiimdb.data.db.dao.MovieDao
import com.example.apiimdb.data.db.entity.MovieEntity

@Database(version = 1, entities = [MovieEntity::class]) // version указывает версию базы данных, которая должна быть использована при создании экземпляра класса AppDatabase. Параметр entities определяет список классов сущностей, которые должны быть зарегистрированы в базе данных
abstract class AppDatabase : RoomDatabase() { // наследуется от интерфейса RoomDatabase
    abstract fun movieDao(): MovieDao // возвращает объект MovieDao — интерфейс для работы с сущностями таблицы movie_table в приложении
}