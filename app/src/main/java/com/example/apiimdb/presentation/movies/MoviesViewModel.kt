package com.example.apiimdb.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiimdb.domain.api.MoviesInteractor
import com.example.apiimdb.domain.models.Movie
import com.example.apiimdb.R
import com.example.apiimdb.presentation.SingleLiveEvent
import com.example.apiimdb.util.debounce
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val context: Context,
    private val moviesInteractor: MoviesInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<MoviesState>()
    fun observeState(): LiveData<MoviesState> = stateLiveData

    private val showToast = SingleLiveEvent<String?>()
    fun observeStateToast(): LiveData<String?> = showToast

            private var latestSearchText: String? = null

        private val handler = Handler(Looper.getMainLooper())

        private val movieSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            searchRequest(changedText)

        }

        // с использованием Корутин спринт 20
        fun searchDebounce(changedText: String) {
            if (latestSearchText != changedText) {
                latestSearchText = changedText
                movieSearchDebounce(changedText)
            }
        }
          // Использовал для отложенного поиска через handler до использования Корутин
//        fun searchDebounce(changedText: String) {
//            if (latestSearchText == changedText) {
//                return
//            }
//
//            this.latestSearchText = changedText
//            handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
//
//            val searchRunnable = Runnable { searchRequest(changedText) }
//
//            val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
//            handler.postAtTime(
//                searchRunnable,
//                SEARCH_REQUEST_TOKEN,
//                postTime,
//            )
//        }

        private fun searchRequest(newSearchText: String) {
            if (newSearchText.isNotEmpty()) {
                renderState(
                    MoviesState.Loading
                )

                viewModelScope.launch { // использование корутин
                    moviesInteractor
                        .searchMovies(newSearchText)
                        .collect { pair ->
                            processResult(pair.first, pair.second)
                        }
                }
            }
        }

    private fun processResult(foundMovies: List<Movie>?, errorMessage: String?) {
        val movies = mutableListOf<Movie>()
        if (foundMovies != null) {
            movies.addAll(foundMovies)
        }

        when {
            errorMessage != null -> {
                renderState(MoviesState.Error(errorMessage = context.getString(
                    R.string.something_went_wrong)))
                showToast.postValue(errorMessage)
            }
            movies.isEmpty() -> {
                renderState(MoviesState.Empty(message = context.getString(R.string.nothing_found)))
            }
            else -> {
                renderState(MoviesState.Content(movies = movies))
            }
        }
    }
    private fun renderState(state: MoviesState) {
            stateLiveData.postValue(state)
    }

    override fun onCleared() {
            super.onCleared()
            handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
    }
}
