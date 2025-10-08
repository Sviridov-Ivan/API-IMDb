package com.example.apiimdb.data.local

import android.content.SharedPreferences
import com.example.apiimdb.data.network.SearchHistoryStorage
import com.google.gson.Gson

class SharedPreferencesSearchHistoryStorage (
    private val prefs: SharedPreferences,
    private val gson: Gson
) : SearchHistoryStorage {

    // Реализация методов, в которых происходит чтение и запись данных в SharedPreferences

}