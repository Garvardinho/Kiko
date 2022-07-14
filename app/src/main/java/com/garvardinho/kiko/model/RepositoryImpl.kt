package com.garvardinho.kiko.model

import com.garvardinho.kiko.model.realm.RealmDataSource
import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.model.retrofit.MovieListDTO
import com.garvardinho.kiko.model.retrofit.RetrofitDataSource
import com.garvardinho.kiko.model.room.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryImpl(
    private val retrofitDataSource: RetrofitDataSource,
    private val realmDataSource: RealmDataSource,
) : Repository {
    override fun loadNowPlayingMoviesFromServer(): Single<MovieListDTO> {
        return retrofitDataSource.loadNowPlayingMovies()
    }

    override fun loadUpcomingMoviesFromServer(): Single<MovieListDTO> {
        return retrofitDataSource.loadUpcomingMovies()
    }

    override fun loadTopRatedMoviesFromServer(): Single<MovieListDTO> {
        return retrofitDataSource.loadTopRatedMovies()
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
        return AppDatabase.getInstance().movieListDao().loadNowPlayingMoviesFromCache().map {
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
        return AppDatabase.getInstance().movieListDao().loadUpcomingMoviesFromCache().map {
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
        return AppDatabase.getInstance().movieListDao().loadTopRatedMoviesFromCache().map {
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
            AppDatabase.getInstance().movieListDao().cacheNowPlayingMovies(movies.map {
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
            AppDatabase.getInstance().movieListDao().cacheUpcomingMovies(movies.map {
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
            AppDatabase.getInstance().movieListDao().cacheTopRatedMovies(movies.map {
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
