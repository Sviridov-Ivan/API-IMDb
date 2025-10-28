package com.example.apiimdb.domain.api

import com.example.apiimdb.domain.models.Person

interface NamesInteractor {
    fun searchNames(expression: String, consumer: NamesConsumer)

    interface NamesConsumer {
        fun consume(foundNames: List<Person>?, errorMessage: String?)
    }
}