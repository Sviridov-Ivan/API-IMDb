package com.example.apiimdb.ui


import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apiimdb.ui.about.AboutFragment
import com.example.apiimdb.ui.poster.PosterFragment

class DetailsViewPagerAdapter(
    fragmentManager: androidx.fragment.app.FragmentManager,
    lifecycle: Lifecycle,
    private val posterUrl: String,
    private val movieId: String
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PosterFragment.newInstance(posterUrl)
            else -> AboutFragment.newInstance(movieId)
        }
    }
}