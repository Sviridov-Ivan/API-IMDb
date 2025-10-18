package com.example.apiimdb.ui.cast


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiimdb.databinding.ActivityMoviesCastBinding
import com.example.apiimdb.presentation.cast.MoviesCastState
import com.example.apiimdb.presentation.cast.MoviesCastViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MoviesCastActivity : AppCompatActivity() {

    companion object {
        private const val ARGS_MOVIE_ID = "movie_id"

        fun newInstance(context: Context, movieId: String): Intent { // метод newInstance, который будет возвращать нам корректно настроенный Intent для показа новой Activity
            return Intent(context, MoviesCastActivity::class.java).apply {
                putExtra(ARGS_MOVIE_ID,movieId)
            }
        }
    }

    // инжект ViewModel
    private val moviesCastViewModel: MoviesCastViewModel by viewModel {
        parametersOf(intent.getStringExtra(ARGS_MOVIE_ID))
    }


    // Добавили адаптер для RecyclerView
    private val adapterDelegates = MoviesCastAdapterDelegates()
    private val adapter = ListDelegationAdapter(

        adapterDelegates.movieCastHeaderDelegate(),
        adapterDelegates.movieCastPersonDelegate(),
    )
    // До Адаптер ДЕЛЕГАТ
    //private val adapter = MoviesCastAdapter() !!!!! Нужно только удалить ненужные более классы: MoviesCastHeaderViewHolder, MoviesCastViewHolder, MoviesCastAdapter. 
    private lateinit var binding: ActivityMoviesCastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesCastBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Привязываем адаптер и LayoutManager к RecyclerView
        binding.moviesCastRecyclerView.adapter = adapter
        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(this)

        moviesCastViewModel.observeState().observe(this) {
            // В зависимости от UiState экрана показываем
            // разные состояния экрана
            when(it) {
                is MoviesCastState.Content -> showContent(it)
                is MoviesCastState.Error -> showError(it)
                is MoviesCastState.Loading -> showLoading()
            }
        }
    }

    private fun showLoading() {
        binding.contentContainer.isVisible = false
        binding.errorMessageTextView.isVisible = false

        binding.progressBar.isVisible = true

    }

    private fun showError(state: MoviesCastState.Error) {
        binding.contentContainer.isVisible = false
        binding.progressBar.isVisible = false

        binding.errorMessageTextView.isVisible = true
        binding.errorMessageTextView.text = state.message
    }

    private fun showContent(state: MoviesCastState.Content) {
        binding.progressBar.isVisible = false
        binding.errorMessageTextView.isVisible = false

        binding.contentContainer.isVisible = true

        // Меняем привязку стейта к UI-элементам
        binding.movieTitle.text = state.fullTitle
        adapter.items = state.items

        adapter.notifyDataSetChanged()
    }
}