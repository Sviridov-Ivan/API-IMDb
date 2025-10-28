package com.example.apiimdb.data

import com.example.apiimdb.data.dto.names.NamesSearchRequest
import com.example.apiimdb.data.dto.names.NamesSearchResponse
import com.example.apiimdb.domain.api.NamesRepository
import com.example.apiimdb.domain.models.Person
import com.example.apiimdb.util.Resource

class NamesRepositoryImpl(private val networkClient: NetworkClient) : NamesRepository {

    override fun searchNames(expression: String): Resource<List<Person>> {
        val response = networkClient.doRequest(NamesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as NamesSearchResponse) {
                    Resource.Success(results.map {
                        Person(id = it.id,
                            name = it.title,
                            description = it.description,
                            photoUrl = it.image)
                    })
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}