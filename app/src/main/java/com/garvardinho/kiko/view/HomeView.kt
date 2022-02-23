package com.garvardinho.kiko.view

import com.garvardinho.kiko.model.MovieResultDTO

interface HomeView : KikoView {
    fun showNowPlayingMovies(movies: List<MovieResultDTO>)
    fun showUpcomingMovies(movies: List<MovieResultDTO>)
    fun showNowPlayingLoading()
    fun showUpcomingLoading()
}
