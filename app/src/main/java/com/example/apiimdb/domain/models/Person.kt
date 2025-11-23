package com.example.apiimdb.domain.models

data class Person( // оставляю только нужные поля и переименовываю для удобства
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String)
