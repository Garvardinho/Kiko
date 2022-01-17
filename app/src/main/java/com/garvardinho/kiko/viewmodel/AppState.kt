package com.garvardinho.kiko.viewmodel

import com.garvardinho.kiko.model.Movie

sealed class AppState {
    data class Success(val nowPlayingMoviesData: List<Movie>,
                       val upcomingMoviesData: List<Movie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
