package com.example.apiimdb.ui.movies


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.example.apiimdb.R
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiimdb.databinding.FragmentMoviesBinding
import com.example.apiimdb.domain.models.Movie
import com.example.apiimdb.presentation.movies.MoviesState
import com.example.apiimdb.presentation.movies.MoviesViewModel
import com.example.apiimdb.ui.detalis.DetailsFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue
import com.example.apiimdb.ui.core.navigation.Router
import com.example.apiimdb.ui.root.RootActivity
import com.example.apiimdb.util.debounce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MoviesFragment : Fragment() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    // Инжектируем роутер для навигации
    private val router: Router by inject()

    private val viewModel: MoviesViewModel by viewModel<MoviesViewModel>()

    //private val adapter = MoviesAdapter() // изменил из-за реализации перехода анимации
    private var adapter: MoviesAdapter? = null

    private var isClickAllowed = true

    private val handler = Handler(Looper.getMainLooper())

    private var textWatcher: TextWatcher? = null

    private lateinit var binding: FragmentMoviesBinding

    private lateinit var onMovieClickDebounce: (Movie) -> Unit // это ссылка на вторую функцию, которую будет возвращать debounce()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Реализация задержки появления экрана при переходе на другой фрагмент через функцию Debounce и корутины
        onMovieClickDebounce = debounce<Movie>(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { movie ->
            findNavController().navigate(R.id.action_moviesFragment_to_detailsFragment,
                DetailsFragment.createArgs(movie.id, movie.image))
        }

        adapter = MoviesAdapter { movie ->
            (activity as RootActivity).animateBottomNavigationView()
            onMovieClickDebounce(movie)
        }
        // Здесь пришлось поправить использование Context
        binding.movies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.movies.adapter = adapter


        adapter?.setOnClickListener { movie ->
            onMovieClickDebounce(movie)

            // До использования реализации // Реализация задержки появления...
//            if (clickDebounce()) { // навигация с помощью NavController
//                findNavController().navigate(R.id.action_moviesFragment_to_detailsFragment,
//                    DetailsFragment.createArgs(movie.id, movie.image))

                // С использованием Jetpack Navigation Component уже не нужно
                // Переходим на следующий экран с помощью РОУТЕРА
//                router.openFragment(
//                    DetailsFragment.newInstance(
//                        movieId = movie.id,
//                        posterUrl = movie.image
//                    )
//                )
//                // Навигируемся на следующий экран до внедрения Роутера и Навигатора
//                parentFragmentManager.commit {
//                    replace(
//                        // Указали, в каком контейнере работаем
//                        R.id.rootFragmentContainerView,
//                        // Создали фрагмент
//                        DetailsFragment.newInstance(
//                            movieId = movie.id,
//                            posterUrl = movie.image
//                        ),
//                        // Указали тег фрагмента
//                        DetailsFragment.TAG
//                    )
//                    // Добавляем фрагмент в Back Stack
//                    addToBackStack(DetailsFragment.TAG)
//                }

                // когда было DetailsActivity
                // Здесь пришлось поправить использование Context
//                val intent = Intent(requireContext(), DetailsActivity::class.java)
//                intent.putExtra("id", movie.id) // 🔹 тот же ключ, что и в DetailsActivity
//                intent.putExtra("poster", movie.image)
//                startActivity(intent)
            //}
        }

        // Здесь пришлось заменить LifecycleOwner на ViewLifecycleOwner
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        // Здесь пришлось заменить LifecycleOwner на ViewLifecycleOwner
        viewModel.observeStateToast().observe(viewLifecycleOwner) { message -> // ХЗ ПРАВИЛЬНО ИЛИ НЕТ
            message?.let {
                // Здесь пришлось поправить использование Context
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        binding.movies.adapter = null //?
        textWatcher?.let { binding.queryInput.removeTextChangedListener(it) }
    }

    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Empty -> showEmpty(state.message)
        }
    }

    private fun showLoading() { // групповой использование байндинг
        binding.apply {
            movies.visibility = View.GONE
            placeholderMessage.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun showError(errorMessage: String) {
        binding.apply {
            movies.visibility = View.GONE
            placeholderMessage.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            placeholderMessage.text = errorMessage
        }
    }

    private fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    private fun showContent(moviesList: List<Movie>) {
        binding.apply {
            movies.visibility = View.VISIBLE
            placeholderMessage.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
        adapter?.movies?.clear()
        adapter?.movies?.addAll(moviesList)
        adapter?.notifyDataSetChanged()
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false

            viewLifecycleOwner.lifecycleScope.launch { // запуск корутины именно во фрагменте
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
            //handler.postDelayed({ isClickAllowed = true },CLICK_DEBOUNCE_DELAY) // использовал handler.postDelayed до Корутин
        }
        return current
    }
}