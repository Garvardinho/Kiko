package com.garvardinho.kiko.model

import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.model.retrofit.MovieListDTO
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun loadNowPlayingMoviesFromServer(): Single<MovieListDTO>
    fun loadUpcomingMoviesFromServer(): Single<MovieListDTO>
    fun loadTopRatedMoviesFromServer(): Single<MovieListDTO>

    fun loadFavoriteMoviesFromRealm() : List<MovieDTO>
    fun putMovieIntoRealm(movie: MovieDTO)
    fun deleteMovieFromRealm(movie: MovieDTO)

    fun loadNowPlayingMoviesFromCache(): Single<List<MovieDTO>>
    fun loadUpcomingMoviesFromCache(): Single<List<MovieDTO>>
    fun loadTopRatedMoviesFromCache(): Single<List<MovieDTO>>

    fun cacheNowPlayingMovies(movies: List<MovieDTO>)
    fun cacheUpcomingMovies(movies: List<MovieDTO>)
    fun cacheTopRatedMovies(movies: List<MovieDTO>)
}