package com.garvardinho.kiko.model

import com.garvardinho.kiko.model.retrofit.DataSource
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import retrofit2.Callback

class RepositoryImpl(private val dataSource: DataSource) : Repository {
    override fun loadNowPlayingMoviesFromServer(callback: Callback<MovieDTO>) {
        dataSource.loadNowPlayingMovies(callback)
    }

    override fun loadUpcomingMoviesFromServer(callback: Callback<MovieDTO>) {
        dataSource.loadUpcomingMovies(callback)
    }

    override fun loadTopRatedMoviesFromServer(callback: Callback<MovieDTO>) {
        dataSource.loadTopRatedMovies(callback)
    }

    override fun loadFavoriteMoviesFromRealm() : List<MovieResultDTO> {
        return dataSource.loadFavoriteMovies()
    }

    override fun putMovieIntoRealm(movie: MovieResultDTO) {
        dataSource.putMovieIntoRealm(movie)
    }

    override fun deleteMovieFromRealm(movie: MovieResultDTO) {
        dataSource.deleteMovieFromRealm(movie)
    }
}