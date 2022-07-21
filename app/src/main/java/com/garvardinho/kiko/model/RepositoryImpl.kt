package com.garvardinho.kiko.model

import com.garvardinho.kiko.model.realm.IRealmDataSource
import com.garvardinho.kiko.model.retrofit.IRemoteDataSource
import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.model.retrofit.MovieListDTO
import com.garvardinho.kiko.model.room.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val realmDataSource: IRealmDataSource,
    private val cacheDao: MovieListDao,
) : Repository {
    override fun loadNowPlayingMoviesFromServer(): Single<MovieListDTO> {
        return remoteDataSource.loadNowPlayingMovies()
    }

    override fun loadUpcomingMoviesFromServer(): Single<MovieListDTO> {
        return remoteDataSource.loadUpcomingMovies()
    }

    override fun loadTopRatedMoviesFromServer(): Single<MovieListDTO> {
        return remoteDataSource.loadTopRatedMovies()
    }

    override fun loadFavoriteMoviesFromRealm(): List<MovieDTO> {
        return realmDataSource.loadFavoriteMovies()
    }

    override fun putMovieIntoRealm(movie: MovieDTO) {
        realmDataSource.putMovieIntoRealm(movie)
    }

    override fun deleteMovieFromRealm(movie: MovieDTO) {
        realmDataSource.deleteMovieFromRealm(movie)
    }

    override fun loadNowPlayingMoviesFromCache(): Single<List<MovieDTO>> {
        return cacheDao.loadNowPlayingMoviesFromCache().map {
            it.map { roomMovie ->
                MovieDTO(
                    title = roomMovie.title,
                    poster_path = roomMovie.posterPath,
                    release_date = roomMovie.releaseDate,
                    vote_average = roomMovie.voteAverage,
                    isFavorite = roomMovie.isFavorite,
                    overview = roomMovie.overview
                )
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun loadUpcomingMoviesFromCache(): Single<List<MovieDTO>> {
        return cacheDao.loadUpcomingMoviesFromCache().map {
            it.map { roomMovie ->
                MovieDTO(
                    title = roomMovie.title,
                    poster_path = roomMovie.posterPath,
                    release_date = roomMovie.releaseDate,
                    vote_average = roomMovie.voteAverage,
                    isFavorite = roomMovie.isFavorite,
                    overview = roomMovie.overview
                )
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun loadTopRatedMoviesFromCache(): Single<List<MovieDTO>> {
        return cacheDao.loadTopRatedMoviesFromCache().map {
            it.map { roomMovie ->
                MovieDTO(
                    title = roomMovie.title,
                    poster_path = roomMovie.posterPath,
                    release_date = roomMovie.releaseDate,
                    vote_average = roomMovie.voteAverage,
                    isFavorite = roomMovie.isFavorite,
                    overview = roomMovie.overview
                )
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun cacheNowPlayingMovies(movies: List<MovieDTO>) {
        Single.fromCallable {
            cacheDao.cacheNowPlayingMovies(movies.map {
                RoomMovie(
                    title = it.title,
                    posterPath = it.poster_path,
                    releaseDate = it.release_date,
                    voteAverage = it.vote_average,
                    isFavorite = it.isFavorite,
                    overview = it.overview,
                    type = NOW_PLAYING
                )
            })
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun cacheUpcomingMovies(movies: List<MovieDTO>) {
        Single.fromCallable {
            cacheDao.cacheUpcomingMovies(movies.map {
                RoomMovie(
                    title = it.title,
                    posterPath = it.poster_path,
                    releaseDate = it.release_date,
                    voteAverage = it.vote_average,
                    isFavorite = it.isFavorite,
                    overview = it.overview,
                    type = UPCOMING
                )
            })
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun cacheTopRatedMovies(movies: List<MovieDTO>) {
        Single.fromCallable {
            cacheDao.cacheTopRatedMovies(movies.map {
                RoomMovie(
                    title = it.title,
                    posterPath = it.poster_path,
                    releaseDate = it.release_date,
                    voteAverage = it.vote_average,
                    isFavorite = it.isFavorite,
                    overview = it.overview,
                    type = TOP_RATED
                )
            })
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}
