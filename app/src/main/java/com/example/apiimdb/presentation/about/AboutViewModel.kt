package com.example.apiimdb.presentation.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiimdb.domain.api.MoviesInteractor
import kotlinx.coroutines.launch

class AboutViewModel(private val movieId: String, private val moviesInteractor: MoviesInteractor,) : ViewModel() {

    private val stateLiveData = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = stateLiveData

    init {
        loadMovieAbout()
    }

    private fun loadMovieAbout() {

        viewModelScope.launch {
            moviesInteractor.getMovieDetails(movieId)
                .collect { (about, error) ->
                    when {
                        about != null -> {
                            // about получен успешно → отдаём Content
                            stateLiveData.postValue(AboutState.Content(about)
                            )
                        }
                        error != null -> {
                            // Ошибка с текстом → отдаём Error
                            stateLiveData.postValue(AboutState.Error(error))
                        }
                        else -> {
                            stateLiveData.postValue(AboutState.Error("Unknoun error")
                            )
                        }
                    }
                }
        }
    }
}