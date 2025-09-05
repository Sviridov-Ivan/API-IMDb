package com.example.apiimdb.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apiimdb.R
import com.example.apiimdb.domain.models.Movie

class MovieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
    .inflate(R.layout.item_imdb_item, parent, false)) {

    val cover: ImageView = itemView.findViewById(R.id.cover) // создаем переменные для связи с ХМЛ
    val title: TextView = itemView.findViewById(R.id.title)  // создаем переменные для связи с ХМЛ
    val description: TextView = itemView.findViewById(R.id.description)  // создаем переменные для связи с ХМЛ

    fun bind(movie: Movie) {
        Glide.with(itemView) // загружаем картинки из Глайд
            .load(movie.image)
            .into(cover)

        title.text = movie.title
        description.text = movie.description
    }
}