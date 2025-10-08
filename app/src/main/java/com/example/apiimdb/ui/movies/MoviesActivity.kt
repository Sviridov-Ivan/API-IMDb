package com.example.apiimdb.ui.movies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiimdb.ui.poster.PosterActivity
import com.example.apiimdb.databinding.ActivityMainBinding
import com.example.apiimdb.domain.models.Movie
import com.example.apiimdb.presentation.movies.MoviesViewModel
import com.example.apiimdb.presentation.movies.MoviesState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesActivity : AppCompatActivity() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private lateinit var binding: ActivityMainBinding // инициализация бандинга
    private val viewModel: MoviesViewModel by viewModel()
    //private var viewModel: MoviesViewModel? = null

    private val adapter = MoviesAdapter()
    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    //private val moviesSearchPresenter = Creator.provideMoviesSearchPresenter(this, context = this, adapter)

    private var textWatcher: TextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater) // подключение байндинга
        setContentView(binding.root)


        //moviesSearchPresenter.onCreate()

        binding.movies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.movies.adapter = adapter

        adapter.setOnClickListener { movie ->
            if (clickDebounce()) {
                val intent = Intent(this, PosterActivity::class.java)
                intent.putExtra("poster", movie.image)
                startActivity(intent)
            }
        }

        viewModel.observeState().observe(this) {
            render(it)
        }

        viewModel.observeStateToast().observe(this) { message -> // ХЗ ПРАВИЛЬНО ИЛИ НЕТ
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel?.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        textWatcher?.let { binding.queryInput.addTextChangedListener(it) }

        //moviesSearchPresenter.onCreate()

    }

    override fun onDestroy() {
        super.onDestroy()
        textWatcher?.let { binding.queryInput.removeTextChangedListener(it) }//moviesSearchPresenter.onDestroy()
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Empty -> showEmpty(state.message)
        }
    }

    fun showLoading() { // групповой использование байндинг
        binding.apply {
            movies.visibility = View.GONE
            placeholderMessage.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }

    }

    fun showError(errorMessage: String) {
        binding.apply {
            movies.visibility = View.GONE
            placeholderMessage.visibility = View.VISIBLE
            progressBar.visibility = View.GONE

            placeholderMessage.text = errorMessage
        }

    }

    fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    fun showContent(moviesList: List<Movie>) {
        binding.apply {
            movies.visibility = View.VISIBLE
            placeholderMessage.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
        adapter.movies.clear()
        adapter.movies.addAll(moviesList)
        adapter.notifyDataSetChanged()
    }

    fun showToast(additionalMessage: String) {
        Toast.makeText(this, additionalMessage, Toast.LENGTH_LONG).show()

    }


}