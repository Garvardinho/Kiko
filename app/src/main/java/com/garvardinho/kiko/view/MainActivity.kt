package com.garvardinho.kiko.view

import android.os.Bundle
import android.view.MenuItem
import com.garvardinho.kiko.App
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.MainActivityBinding
import com.garvardinho.kiko.view.screens.AndroidScreens
import com.garvardinho.kiko.view.home.HomeFragment
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.navigation.NavigationBarView
import io.realm.Realm
import io.realm.RealmConfiguration
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {

    private lateinit var mainActivityBinding: MainActivityBinding
    private val navigator = AppNavigator(this, R.id.main_fragment)

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

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigationHolder.removeNavigator()
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
                    App.instance.router.navigateTo(AndroidScreens.homeScreen())
                    return@setOnItemSelectedListener true
                }

                R.id.action_favorites -> {
                    App.instance.router.navigateTo(AndroidScreens.favoriteScreen())
                    return@setOnItemSelectedListener true
                }

                R.id.action_ratings -> {
                    App.instance.router.navigateTo(AndroidScreens.topRatedScreen())
                    return@setOnItemSelectedListener true
                }
            }

            false
        }
    }
}
