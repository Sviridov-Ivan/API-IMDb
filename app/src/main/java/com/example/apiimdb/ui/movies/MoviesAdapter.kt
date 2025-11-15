package com.example.apiimdb.ui.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apiimdb.domain.models.Movie

//class MoviesAdapter(val clickListener: MovieClickListener) : RecyclerView.Adapter<MovieViewHolder>() { //добавил val clickListener: MovieClickListener для обработки нажатия на результат поиска в MoviesActivity
//
//    var movies = ArrayList<Movie>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
//        MovieViewHolder.from(parent)
//
//    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
//        holder.bind(movies.get(position))
//        holder.itemView.setOnClickListener { clickListener.onMovieClick(movies.get(position))} // обработка нажатия на результат поиска ввиде открытия Постер Активити
//    }
//
//    override fun getItemCount(): Int = movies.size
//
//    fun interface MovieClickListener { // обработка нажатия на результат поиска ввиде открытия Постер Активити
//        fun onMovieClick(movie: Movie)
//    }
//}

class MoviesAdapter(private val onMovieClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {

    var movies = ArrayList<Movie>()
    private var clickListener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder.from(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            clickListener?.invoke(movie)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun setOnClickListener(listener: (Movie) -> Unit) {
        clickListener = listener
    }
}