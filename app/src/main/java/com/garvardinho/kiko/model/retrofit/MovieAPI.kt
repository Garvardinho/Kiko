package com.garvardinho.kiko.model.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("now_playing")
    fun loadNowPlayingMovies(
        @Query("api_key") token: String,
        @Query("page") page: Int
    ) : Single<MovieListDTO>

    @GET("upcoming")
    fun loadUpcomingMovies(
        @Query("api_key") token: String,
        @Query("page") page: Int
    ) : Single<MovieListDTO>

    @GET("top_rated")
    fun loadTopRatedMovies(
        @Query("api_key") token: String,
        @Query("page") page: Int
    ) : Single<MovieListDTO>
}