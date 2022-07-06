package com.garvardinho.kiko.screens

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.view.details.MovieDetailsFragment
import com.garvardinho.kiko.view.favorites.FavoritesFragment
import com.garvardinho.kiko.view.home.HomeFragment
import com.garvardinho.kiko.view.toprated.TopRatedFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object AndroidScreens : KikoScreens {

    override fun homeScreen(): Screen {
        return FragmentScreen { HomeFragment() }
    }

    override fun favoriteScreen(): Screen {
        return FragmentScreen { FavoritesFragment() }
    }

    override fun topRatedScreen(): Screen {
        return FragmentScreen { TopRatedFragment() }
    }

    override fun detailsScreen(movie: MovieResultDTO): Screen {
        return FragmentScreen { MovieDetailsFragment.newInstance(movie) }
    }
}