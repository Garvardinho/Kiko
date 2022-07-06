package com.garvardinho.kiko.presenter

import com.garvardinho.kiko.model.MovieResultDTO

interface ViewDelegate {

    fun onBackPressed(): Boolean
    fun manageFavorite(movie: MovieResultDTO)
}