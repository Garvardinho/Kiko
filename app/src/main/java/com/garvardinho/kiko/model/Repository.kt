package com.garvardinho.kiko.model

import io.reactivex.rxjava3.core.Single

interface Repository {
    fun loadNowPlayingMoviesFromServer(): Single<MovieDTO>
    fun loadUpcomingMoviesFromServer(): Single<MovieDTO>
    fun loadTopRatedMoviesFromServer(): Single<MovieDTO>
    fun loadFavoriteMoviesFromRealm() : List<MovieResultDTO>
    fun putMovieIntoRealm(movie: MovieResultDTO)
    fun deleteMovieFromRealm(movie: MovieResultDTO)
}