package com.garvardinho.kiko.viewmodel

import com.garvardinho.kiko.model.MovieDTO
import com.garvardinho.kiko.model.MovieResultDTO

sealed class AppState {
    data class Success(val nowPlayingMoviesData: List<MovieResultDTO>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
