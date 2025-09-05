package com.example.apiimdb.data

import com.example.apiimdb.data.dto.MovieDto
import com.example.apiimdb.data.dto.Response

interface NetworkClient { // интерфейс для унификации работы с сетью - неважно ретрофит или еще что. Реализация будет в RetrofitNetworkClient
    fun doRequest(dto: Any) : Response
}