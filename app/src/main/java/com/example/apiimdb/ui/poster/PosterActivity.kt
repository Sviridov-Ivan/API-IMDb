package com.example.apiimdb.ui.poster

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.apiimdb.util.Creator
import com.example.apiimdb.R
import com.example.apiimdb.presentation.poster.PosterViewModel

class PosterActivity : AppCompatActivity() {

    private var viewModel: PosterViewModel? = null

    //private lateinit var posterPresenter: PosterPresenter

    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
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


/*class PosterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge() // поддержка EdgeToEdge режима
        setContentView(R.layout.activity_poster)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.poster)) { v, insets -> // присваиваю id для головного layout в верстке
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val posterUrl = intent.getStringExtra("poster")

        Glide.with(this)
            .load(posterUrl)
            .into(findViewById(R.id.cover))

    }
}*/