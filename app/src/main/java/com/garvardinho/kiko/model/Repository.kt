package com.garvardinho.kiko.model

interface Repository {
    fun loadNowPlayingMoviesFromServer(callback: retrofit2.Callback<MovieDTO>)
    fun loadUpcomingMoviesFromServer(callback: retrofit2.Callback<MovieDTO>)
    fun loadTopRatedMoviesFromServer(callback: retrofit2.Callback<MovieDTO>)
}