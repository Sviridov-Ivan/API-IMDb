package com.example.apiimdb.ui.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.apiimdb.R
import com.example.apiimdb.databinding.ActivityRootBinding
import org.koin.core.qualifier._q


class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding

    // С использованием Jetpack Navigation Component уже не нужно
//    // Заинжектили NavigatorHolder,
//    // чтобы прикрепить к нему Navigator
//    private val navigatorHolder: NavigatorHolder by inject()
//
//    // Создали Navigator
//    private val navigator = NavigatorImpl(
//        fragmentContainerViewId = R.id.rootFragmentContainerView,
//        fragmentManager = supportFragmentManager
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Привязываем вёрстку к экрану
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // подключение navController из JetPackNavigationComponent
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment // инициализация rootFragmentContainerView как навхостфрагмент
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController) // привязка navController к bottomNavigationView

        // скрытие bottomNavigationView на определенных фрагментах
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailsFragment, R.id.moviesCastFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
        // С использованием Jetpack Navigation Component уже не нужно
//        if (savedInstanceState == null) { //чтобы фрагмент не добавлялся при изменениях конфигурации
//            // С помощью навигатора открываем первый экран
//            navigator.openFragment(
//                MoviesFragment()
//            )
//              // До внедрения Навигатора и Роутера
////            supportFragmentManager.commit {
////                this.add(R.id.rootFragmentContainerView, MoviesFragment())
////            }
//        }
    }

    // С использованием Jetpack Navigation Component уже не нужно

//    // Прикрепляем Navigator к NavigatorHolder
//    override fun onResume() {
//        super.onResume()
//        navigatorHolder.attachNavigator(navigator)
//    }
//
//    // Открепляем Navigator от NavigatorHolder
//    override fun onPause() {
//        super.onPause()
//        navigatorHolder.detachNavigator()
//    }
}