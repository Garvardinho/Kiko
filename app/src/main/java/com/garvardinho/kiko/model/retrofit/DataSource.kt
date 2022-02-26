package com.garvardinho.kiko.model.retrofit

import com.garvardinho.kiko.model.MovieDTO
import com.garvardinho.kiko.model.MovieResultDTO
import retrofit2.Callback

interface DataSource {
    fun loadNowPlayingMovies(callback: Callback<MovieDTO>) {}
    fun loadUpcomingMovies(callback: Callback<MovieDTO>) {}
    fun loadTopRatedMovies(callback: Callback<MovieDTO>) {}
    fun loadFavoriteMovies(): List<MovieResultDTO> {
        return listOf()
    }
    fun putMovieIntoRealm(movie: MovieResultDTO) {}
    fun deleteMovieFromRealm(movie: MovieResultDTO) {}
}