package com.example.apiimdb.ui.names

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apiimdb.domain.models.Person

class PersonsAdapter : RecyclerView.Adapter<PersonViewHolder>() { // этом списке нет обработки нажатий, а значит, нет необходимости в какой-то ещё логике

    var persons = mutableListOf<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder = PersonViewHolder(parent)

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(persons[position])
    }

    override fun getItemCount(): Int = persons.size
    }
