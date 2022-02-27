package com.garvardinho.kiko.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.MainActivityBinding
import com.garvardinho.kiko.view.home.HomeFragment
import com.garvardinho.kiko.view.ratings.RatingsFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    supportFragmentManager.openFragment(RatingsFragment())
                    return@setOnItemSelectedListener true
                }
            }

            false
        }
    }
}

fun FragmentManager.openFragment(fragment: Fragment) {
    this.beginTransaction()
        .replace(R.id.main_fragment, fragment)
        .setTransition(TRANSIT_FRAGMENT_FADE)
        .addToBackStack(null)
        .commit()
}

fun TextView.setTextWithBoldTitle(title: String, text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml("<b>$title</b> $text",
            Html.FROM_HTML_MODE_COMPACT)
    }
}
