package com.garvardinho.kiko.view

import com.garvardinho.kiko.model.MovieResultDTO

interface FavoritesView : KikoView {
    fun showFavoriteMovies(movies: List<MovieResultDTO>)
}