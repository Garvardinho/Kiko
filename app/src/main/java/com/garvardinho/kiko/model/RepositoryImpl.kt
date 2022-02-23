package com.garvardinho.kiko.model

import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import retrofit2.Callback

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {
    override fun loadNowPlayingMoviesFromServer(callback: Callback<MovieDTO>) {
        remoteDataSource.loadNowPlayingMovies(callback)
    }

    override fun loadUpcomingMoviesFromServer(callback: Callback<MovieDTO>) {
        remoteDataSource.loadUpcomingMovies(callback)
    }

    override fun loadTopRatedMoviesFromServer(callback: Callback<MovieDTO>) {
        remoteDataSource.loadTopRatedMovies(callback)
    }
}