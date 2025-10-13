package com.example.apiimdb.ui.poster

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.apiimdb.R
import com.example.apiimdb.presentation.poster.PosterViewModel

class PosterActivity : AppCompatActivity() {

    private var viewModel: PosterViewModel? = null
    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_poster)
        poster = findViewById(R.id.cover)
        // Мы не можем создать PosterPresenter раньше,
        // потому что нам нужен imageUrl, который
        // станет доступен только после super.onCreate
        val imageUrl = intent.extras?.getString("poster", "") ?: ""

        viewModel = ViewModelProvider(this, PosterViewModel.getFactory(imageUrl))
            .get(PosterViewModel::class.java)

        viewModel?.observeUrl()?.observe(this) {
            setupPosterImage(it)
        }
    }
    fun setupPosterImage(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(poster)
    }
}
