package com.garvardinho.kiko.view.screens

import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.github.terrakok.cicerone.Screen

interface KikoScreens {

    fun homeScreen(): Screen
    fun favoriteScreen(): Screen
    fun topRatedScreen(): Screen
    fun detailsScreen(movie: MovieDTO): Screen
}