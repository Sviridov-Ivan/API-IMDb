package com.example.apiimdb.domain.impl


import com.example.apiimdb.domain.api.NamesInteractor
import com.example.apiimdb.domain.api.NamesRepository
import com.example.apiimdb.util.Resource
import java.util.concurrent.Executors

class NamesInteractorImpl(private val repository: NamesRepository) : NamesInteractor { // Запрашиваем данные у репозитория в отдельном потоке и передаём полученный результат экземпляру NamesConsumer

    private val executor = Executors.newCachedThreadPool()

    override fun searchNames(expression: String, consumer: NamesInteractor.NamesConsumer) {
        executor.execute {
            when(val resource = repository.searchNames(expression)) {
                is Resource.Success -> { consumer.consume(resource.data, null) }
                is Resource.Error -> { consumer.consume(resource.data, resource.message) }
            }
        }
    }
}