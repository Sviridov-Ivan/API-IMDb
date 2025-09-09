package com.example.apiimdb.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apiimdb.R
import com.example.apiimdb.databinding.ItemImdbItemBinding
import com.example.apiimdb.domain.models.Movie

class MovieViewHolder(private val binding: ItemImdbItemBinding) :  RecyclerView.ViewHolder(binding.root)  /*(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
    .inflate(R.layout.item_imdb_item, parent, false))*/ { // для байндинга

    val cover: ImageView = itemView.findViewById(R.id.cover) // создаем переменные для связи с ХМЛ
    val title: TextView = itemView.findViewById(R.id.title)  // создаем переменные для связи с ХМЛ
    val description: TextView = itemView.findViewById(R.id.description)  // создаем переменные для связи с ХМЛ

    fun bind(movie: Movie) {
        Glide.with(binding.root) // загружаем картинки из Глайд
            .load(movie.image)
            .into(binding.cover)

        binding.title.text = movie.title
        binding.description.text = movie.description
    }

    companion object { // для байндинга
        fun from(parent: ViewGroup): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemImdbItemBinding.inflate(inflater, parent, false)
            return MovieViewHolder(binding)
        }
    }
}