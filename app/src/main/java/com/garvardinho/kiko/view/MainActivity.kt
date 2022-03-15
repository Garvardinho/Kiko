package com.garvardinho.kiko.view

import android.os.Bundle
import android.view.MenuItem
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.MainActivityBinding
import com.garvardinho.kiko.openFragment
import com.garvardinho.kiko.view.favorites.FavoritesFragment
import com.garvardinho.kiko.view.home.HomeFragment
import com.garvardinho.kiko.view.toprated.TopRatedFragment
import com.google.android.material.navigation.NavigationBarView
import io.realm.Realm
import io.realm.RealmConfiguration
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {

    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .allowQueriesOnUiThread(true)
                .build()
        )

        mainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, HomeFragment())
                .commitNow()
        }
        supportActionBar?.title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initBottomNavigation()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            finish()
            supportFragmentManager.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initBottomNavigation() {
        val bottomNavigationView: NavigationBarView = mainActivityBinding.bottomNavigation

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    supportFragmentManager.openFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.action_favorites -> {
                    supportFragmentManager.openFragment(FavoritesFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.action_ratings -> {
                    supportFragmentManager.openFragment(TopRatedFragment())
                    return@setOnItemSelectedListener true
                }
            }

            false
        }
    }
}
