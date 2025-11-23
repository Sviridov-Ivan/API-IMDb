package com.example.apiimdb.presentation.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiimdb.domain.api.MoviesInteractor
import com.example.apiimdb.domain.models.MovieCast
import com.example.apiimdb.ui.core.RVItem
import kotlinx.coroutines.launch


// В конструктор пробросили необходимые для запроса параметры
class MoviesCastViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<MoviesCastState>()
    fun observeState(): LiveData<MoviesCastState> = stateLiveData

    init {
        loadMovieCast()
    }

    private fun loadMovieCast() {
        // При старте экрана покажем  ProgressBar
        stateLiveData.postValue(MoviesCastState.Loading)

        viewModelScope.launch { // запускаем новый поток при помощи корутин
            moviesInteractor.getMovieCast(movieId)
                .collect { (cast, error) ->
                    when {
                        cast != null -> {
                            stateLiveData.postValue(castToUiStateContent(cast)
                            )
                        }
                        error != null -> {
                            stateLiveData.postValue(MoviesCastState.Error(error)
                            )
                        }
                        else -> {
                            stateLiveData.postValue(MoviesCastState.Error("Unknown error"))
                        }
                    }
                }
        }
    }
    private fun castToUiStateContent(cast: MovieCast): MoviesCastState {
        // Строим список элементов RecyclerView
        val items = buildList<MoviesCastRVItem> {
            // Если есть хотя бы один режиссёр, добавим заголовок
            if (cast.directors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Directors")
                this += cast.directors.map { MoviesCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один сценарист, добавим заголовок
            if (cast.writers.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Writers")
                this += cast.writers.map { MoviesCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один актёр, добавим заголовок
            if (cast.actors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Actors")
                this += cast.actors.map { MoviesCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один дополнительный участник, добавим заголовок
            if (cast.others.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Others")
                this += cast.others.map { MoviesCastRVItem.PersonItem(it) }
            }
        }


        return MoviesCastState.Content(
            fullTitle = cast.fullTitle,
            items = items
        )
    }


}
sealed interface MoviesCastState {
    object Loading : MoviesCastState

    data class Content(
        val fullTitle: String,
        val items: List<RVItem>,
        //val items: List<MoviesCastRVItem>, // до Адаптер делегат
    ) : MoviesCastState

    data class Error(
        val message: String,
    ) : MoviesCastState
}