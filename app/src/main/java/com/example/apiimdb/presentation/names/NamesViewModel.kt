package com.example.apiimdb.presentation.names

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiimdb.R
import com.example.apiimdb.domain.api.NamesInteractor
import com.example.apiimdb.domain.models.Person
import com.example.apiimdb.presentation.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NamesViewModel(
    private val context: Context,
    private val namesInteractor: NamesInteractor
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

    }

    private var latestSearchText: String? = null

    private var searchJob: Job? = null // cоздаём переменную searchJob типа Job, которую инициализируем значением null
    private val stateLiveData = MutableLiveData< NamesState>()
    fun observeState(): LiveData<NamesState> = stateLiveData

    private val showToast = SingleLiveEvent<String?>()
    fun observeStateToast(): LiveData<String?> = showToast


    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText

        searchJob?.cancel() // Отменяем текущее выполнение searchJob при помощи метода cancel()
        searchJob = viewModelScope.launch { //Запускаем новую корутину (именно во viewModel) при помощи функции launch { }
            delay(SEARCH_DEBOUNCE_DELAY) // suspend-функция delay()
            searchRequest(changedText)
        }
        // Дебонс через Хандлер и implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
//        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
//
//        val searchRunnable = implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4") { searchRequest(changedText) }
//
//        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
//        handler.postAtTime(
//            searchRunnable,
//            SEARCH_REQUEST_TOKEN,
//            postTime,
//        )
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {

            renderState(NamesState.Loading)

            viewModelScope.launch {
                namesInteractor
                    .searchNames(newSearchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(foundNames: List<Person>?, errorMessage: String?) {
        val persons = mutableListOf<Person>()
        if (foundNames != null) {
            persons.addAll(foundNames)
        }

        when {
            errorMessage != null -> {
                renderState(NamesState.Error(message = context.getString(
                    R.string.something_went_wrong)))
                showToast.postValue(errorMessage)
            }
            persons.isEmpty() -> {
                renderState(NamesState.Empty(message = context.getString(R.string.nothing_found)))
            }
            else -> {
                renderState(NamesState.Content(persons = persons))
            }
        }
    }

    private fun renderState(state: NamesState) {
        stateLiveData.postValue(state)
    }
}