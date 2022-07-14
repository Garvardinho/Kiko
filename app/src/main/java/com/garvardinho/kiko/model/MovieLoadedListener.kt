package com.garvardinho.kiko.model

import com.garvardinho.kiko.model.retrofit.MovieListDTO

interface MovieLoadedListener {
    fun onLoaded(nowPlayingMovieListDTOS: MovieListDTO, upcomingMovieListDTOS: MovieListDTO)
    fun onFailed(throwable: Throwable)
}