package com.example.apiimdb.domain.api

import com.example.apiimdb.domain.models.Person
import kotlinx.coroutines.flow.Flow

interface NamesInteractor {
    //fun searchNames(expression: String, consumer: NamesConsumer) // до перехода на корутины и Flow
    fun searchNames(expression: String): Flow<Pair<List<Person>?, String?>>

//    interface NamesConsumer { //имея возможность работать с Flow, мы можем обойтись и без него
//        fun consume(foundNames: List<Person>?, errorMessage: String?)
//    }
}