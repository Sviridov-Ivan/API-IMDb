package com.example.apiimdb.presentation.cast

import com.example.apiimdb.domain.models.MovieCastPerson
import com.example.apiimdb.ui.RVItem

sealed interface MoviesCastRVItem : RVItem {  // sealed, чтобы нам было проще приводить элементы списка к нужным типам данных

    data class HeaderItem(
        val headerText: String,
    ) : MoviesCastRVItem

    data class PersonItem(
        val data: MovieCastPerson,
    ) : MoviesCastRVItem
}