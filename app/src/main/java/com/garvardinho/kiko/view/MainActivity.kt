package com.garvardinho.kiko.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.MainActivityBinding
import com.garvardinho.kiko.view.home.HomeFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, HomeFragment.newInstance())
                .commitNow()
        }

        supportActionBar?.title = null
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val bottomNavigationView: NavigationBarView = mainActivityBinding.bottomNavigation

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, HomeFragment())
                        .setTransition(TRANSIT_FRAGMENT_FADE)
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.action_favorites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, FavoritesFragment())
                        .setTransition(TRANSIT_FRAGMENT_FADE)
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.action_ratings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, RatingsFragment())
                        .setTransition(TRANSIT_FRAGMENT_FADE)
                        .commit()
                    return@setOnItemSelectedListener true
                }
            }

            false
        }
    }
}