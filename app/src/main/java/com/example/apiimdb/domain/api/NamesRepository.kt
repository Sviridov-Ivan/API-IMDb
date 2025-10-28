package com.example.apiimdb.domain.api

import com.example.apiimdb.domain.models.Person
import com.example.apiimdb.util.Resource

interface NamesRepository {
    fun searchNames(expression: String) : Resource<List<Person>>
}