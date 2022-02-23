package com.garvardinho.kiko.model.retrofit

import com.garvardinho.kiko.model.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("now_playing")
    fun loadNowPlayingMovies(
        @Query("api_key") token: String,
        @Query("page") page: Int
    ) : Call<MovieDTO>

    @GET("upcoming")
    fun loadUpcomingMovies(
        @Query("api_key") token: String,
        @Query("page") page: Int
    ) : Call<MovieDTO>

    @GET("top_rated")
    fun loadTopRatedMovies(
        @Query("api_key") token: String,
        @Query("page") page: Int
    ) : Call<MovieDTO>
}