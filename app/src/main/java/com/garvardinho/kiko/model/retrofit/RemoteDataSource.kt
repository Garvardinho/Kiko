package com.garvardinho.kiko.model.retrofit

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class RemoteDataSource @Inject constructor(private val movieAPI: MovieAPI) : IRemoteDataSource {

    @Inject
    @Named("apiKey")
    lateinit var apiKey: String

    override fun loadNowPlayingMovies(): Single<MovieListDTO> {
        return movieAPI.loadNowPlayingMovies(apiKey, 1).subscribeOn(Schedulers.io())
    }

    override fun loadUpcomingMovies(): Single<MovieListDTO> {
        return movieAPI.loadUpcomingMovies(apiKey, 1).subscribeOn(Schedulers.io())
    }

    override fun loadTopRatedMovies(): Single<MovieListDTO> {
        return movieAPI.loadTopRatedMovies(apiKey, 1).subscribeOn(Schedulers.io())
    }
}