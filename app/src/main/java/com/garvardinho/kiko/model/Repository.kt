package com.garvardinho.kiko.model

interface Repository {
    fun getNowPlayingMoviesFromServer()
    fun getUpcomingMoviesFromServer()
}