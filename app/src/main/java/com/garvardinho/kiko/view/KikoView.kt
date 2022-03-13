package com.garvardinho.kiko.view

import com.garvardinho.kiko.model.MovieResultDTO

interface KikoView {
    fun showError()
    fun manageFavorite(movie: MovieResultDTO)
}