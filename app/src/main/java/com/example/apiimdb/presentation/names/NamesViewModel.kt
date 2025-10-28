package com.example.apiimdb.presentation.names

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apiimdb.R
import com.example.apiimdb.domain.api.NamesInteractor
import com.example.apiimdb.domain.models.Person
import com.example.apiimdb.presentation.SingleLiveEvent



class NamesViewModel(
    private val context: Context,
    private val namesInteractor: NamesInteractor
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

//            fun getFactory(/*value: Int*/): ViewModelProvider.Factory = viewModelFactory { // фабрика, но сначала нужно создать класс мовис аппликейшн
//                initializer {
//                    val app = (this[APPLICATION_KEY] as MoviesApplication)
//                    MoviesViewModel(app.applicationContext)
//                }
//            }
    }

    private val handler = Handler(Looper.getMainLooper())

    private val stateLiveData = MutableLiveData< NamesState>()
    fun observeState(): LiveData<NamesState> = stateLiveData

    private val showToast = SingleLiveEvent<String?>()
    fun observeStateToast(): LiveData<String?> = showToast

    private var latestSearchText: String? = null

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { searchRequest(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(NamesState.Loading)

            namesInteractor.searchNames(newSearchText, object : NamesInteractor.NamesConsumer {
                override fun consume(foundNames: List<Person>?, errorMessage: String?) {
                    val persons = mutableListOf<Person>()
                    if (foundNames != null) {
                        persons.addAll(foundNames)
                    }

                    when {
                        errorMessage != null -> {
                            renderState(
                                NamesState.Error(
                                    message = context.getString(
                                        R.string.something_went_wrong),
                                )
                            )
                            showToast.postValue(errorMessage)
                        }

                        persons.isEmpty() -> {
                            renderState(
                                NamesState.Empty(
                                    message = context.getString(R.string.nothing_found),
                                )
                            )
                        }

                        else -> {
                            renderState(
                                NamesState.Content(
                                    persons = persons,
                                )
                            )
                        }
                    }

                }
            })
        }
    }

    private fun renderState(state: NamesState) {
        stateLiveData.postValue(state)
    }

//override fun onCleared() {
//        super.onCleared()
//        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
//    }
}