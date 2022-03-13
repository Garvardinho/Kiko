package com.garvardinho.kiko.presenter

interface HomeViewDelegate : ViewDelegate {
    fun loadNowPlayingMovies()
    fun loadUpcomingMovies()
}