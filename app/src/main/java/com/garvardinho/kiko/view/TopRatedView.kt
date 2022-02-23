package com.garvardinho.kiko.view

import com.garvardinho.kiko.model.MovieResultDTO

interface TopRatedView : KikoView {
    fun showTopRatedMovies(movies: List<MovieResultDTO>)
    fun showTopRatedLoading()
}