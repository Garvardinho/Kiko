package com.garvardinho.kiko.screens

import com.garvardinho.kiko.model.MovieResultDTO
import com.github.terrakok.cicerone.Screen

interface KikoScreens {

    fun homeScreen(): Screen
    fun favoriteScreen(): Screen
    fun topRatedScreen(): Screen
    fun detailsScreen(movie: MovieResultDTO): Screen
}