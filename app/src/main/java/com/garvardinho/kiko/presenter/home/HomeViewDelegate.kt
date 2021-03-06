package com.garvardinho.kiko.presenter.home

import com.garvardinho.kiko.presenter.ViewDelegate

interface HomeViewDelegate : ViewDelegate {

    fun loadNowPlayingMovies()
    fun loadUpcomingMovies()
    fun filterNowPlayingMovies(by: Int)
    fun filterUpcomingMovies(by: Int)
}