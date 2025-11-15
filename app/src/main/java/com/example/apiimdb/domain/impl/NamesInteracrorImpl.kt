package com.example.apiimdb.domain.impl


import com.example.apiimdb.domain.api.NamesInteractor
import com.example.apiimdb.domain.api.NamesRepository
import com.example.apiimdb.domain.models.Person
import com.example.apiimdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class NamesInteractorImpl(private val repository: NamesRepository) : NamesInteractor { // Запрашиваем данные у репозитория в отдельном потоке и передаём полученный результат экземпляру NamesConsumer

    override fun searchNames(expression: String): Flow<Pair<List<Person>?, String?>> {
        return repository.searchNames(expression).map { result ->
            when(result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
    // Использовал до корутин и Flow
//    private val executor = Executors.newCachedThreadPool()
//
//    override fun searchNames(expression: String, consumer: NamesInteractor.NamesConsumer) {
//        executor.execute {
//            when(val resource = repository.searchNames(expression)) {
//                is Resource.Success -> { consumer.consume(resource.data, null) }
//                is Resource.Error -> { consumer.consume(resource.data, resource.message) }
//            }
//        }
//    }
}