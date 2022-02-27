package com.garvardinho.kiko.model

interface MovieLoadedListener {
    fun onLoaded(nowPlayingMovieDTOS: MovieDTO, upcomingMovieDTOS: MovieDTO)
    fun onFailed(throwable: Throwable)
}