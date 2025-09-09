package com.example.apiimdb.presentation.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class PosterViewModel(private val posterUrl: String) : ViewModel() { // наследоваться от ViewModel.



    companion object {
        fun getFactory(url: String): ViewModelProvider.Factory = viewModelFactory { //функция getFactory() принимает в качестве параметра ссылку, которая передаётся в конструктор PosterViewModel.
            initializer {
                PosterViewModel(url)
            }
        }
    }

    private val urlLiveData = MutableLiveData(posterUrl) // Добавим LiveData для хранения ссылки на постер:
    fun observeUrl(): LiveData<String> = urlLiveData
}