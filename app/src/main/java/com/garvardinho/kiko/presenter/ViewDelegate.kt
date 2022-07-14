package com.garvardinho.kiko.presenter

import com.garvardinho.kiko.model.retrofit.MovieDTO

interface ViewDelegate {

    fun onBackPressed(): Boolean
    fun manageFavorite(movie: MovieDTO)
}