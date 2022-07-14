package com.garvardinho.kiko.model.retrofit

import io.reactivex.rxjava3.core.Single

interface RetrofitDataSource {
    fun loadNowPlayingMovies(): Single<MovieListDTO>
    fun loadUpcomingMovies(): Single<MovieListDTO>
    fun loadTopRatedMovies(): Single<MovieListDTO>
}