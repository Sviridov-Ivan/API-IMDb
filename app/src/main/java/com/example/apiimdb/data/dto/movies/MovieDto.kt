package com.example.apiimdb.data.dto.movies

data class MovieDto( //  служит для работы с сырыми данными, полученными, например, из сети.
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String)