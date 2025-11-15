package com.example.apiimdb.domain.api

import com.example.apiimdb.domain.models.Person
import com.example.apiimdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface NamesRepository {
    //fun searchNames(expression: String) : Resource<List<Person>> // до перехода на корутины и Flow
    fun searchNames(expression: String) : Flow<Resource<List<Person>>>
}