package com.garvardinho.kiko.model.retrofit

import com.garvardinho.kiko.model.MovieDTO
import com.garvardinho.kiko.model.MovieResultDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.Callback

interface RetrofitDataSource {
    fun loadNowPlayingMovies(): Single<MovieDTO>
    fun loadUpcomingMovies(): Single<MovieDTO>
    fun loadTopRatedMovies(): Single<MovieDTO>
}