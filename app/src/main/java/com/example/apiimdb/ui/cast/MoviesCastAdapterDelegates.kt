package com.example.apiimdb.ui.cast

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.apiimdb.databinding.ListItemCastBinding
import com.example.apiimdb.databinding.ListItemHeaderBinding
import com.example.apiimdb.presentation.cast.MoviesCastRVItem
import com.example.apiimdb.ui.RVItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class MoviesCastAdapterDelegates {


    // 🔹 Делегат для заголовков на экране состава участников
    fun movieCastHeaderDelegate() =
        adapterDelegateViewBinding<MoviesCastRVItem.HeaderItem, RVItem, ListItemHeaderBinding>(
            { layoutInflater, root -> ListItemHeaderBinding.inflate(layoutInflater, root, false) }
        ) {
            bind {
                binding.headerTextView.text = item.headerText
            }
        }

    // 🔹 Делегат для участников (актёров)
    fun movieCastPersonDelegate() =
        adapterDelegateViewBinding<MoviesCastRVItem.PersonItem, RVItem, ListItemCastBinding>(
            { layoutInflater, root -> ListItemCastBinding.inflate(layoutInflater, root, false) }
        ) {
            bind {
                if (item.data.image == null) {
                    binding.actorImageView.isVisible = false
                } else {
                    Glide.with(itemView)
                        .load(item.data.image)
                        .into(binding.actorImageView)
                    binding.actorImageView.isVisible = true
                }

                binding.actorNameTextView.text = item.data.name
                binding.actorDescriptionTextView.text = item.data.description
            }
        }
}