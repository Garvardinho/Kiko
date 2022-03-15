package com.garvardinho.kiko.presenter

import com.garvardinho.kiko.model.MovieResultDTO

interface ViewDelegate {

    fun manageFavorite(movie: MovieResultDTO)
}