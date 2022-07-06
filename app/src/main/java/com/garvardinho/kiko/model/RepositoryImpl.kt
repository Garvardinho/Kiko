package com.garvardinho.kiko.model

import com.garvardinho.kiko.model.realm.RealmDataSource
import com.garvardinho.kiko.model.retrofit.RetrofitDataSource
import io.reactivex.rxjava3.core.Single

class RepositoryImpl(
    private val retrofitDataSource: RetrofitDataSource,
    private val realmDataSource: RealmDataSource
    ) : Repository {
    override fun loadNowPlayingMoviesFromServer(): Single<MovieDTO> {
        return retrofitDataSource.loadNowPlayingMovies()
    }

    override fun loadUpcomingMoviesFromServer(): Single<MovieDTO> {
        return retrofitDataSource.loadUpcomingMovies()
    }

    override fun loadTopRatedMoviesFromServer(): Single<MovieDTO> {
        return retrofitDataSource.loadTopRatedMovies()
    }

    override fun loadFavoriteMoviesFromRealm() : List<MovieResultDTO> {
        return realmDataSource.loadFavoriteMovies()
    }

    override fun putMovieIntoRealm(movie: MovieResultDTO) {
        realmDataSource.putMovieIntoRealm(movie)
    }

    override fun deleteMovieFromRealm(movie: MovieResultDTO) {
        realmDataSource.deleteMovieFromRealm(movie)
    }
}