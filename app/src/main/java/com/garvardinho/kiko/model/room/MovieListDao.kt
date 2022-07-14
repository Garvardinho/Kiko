package com.garvardinho.kiko.model.room

import androidx.room.*
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieListDao {
    @Query("SELECT * FROM roommovie WHERE type = $NOW_PLAYING")
    fun loadNowPlayingMoviesFromCache(): Single<List<RoomMovie>>

    @Query("SELECT * FROM roommovie WHERE type = $UPCOMING")
    fun loadUpcomingMoviesFromCache(): Single<List<RoomMovie>>

    @Query("SELECT * FROM roommovie WHERE type = $TOP_RATED")
    fun loadTopRatedMoviesFromCache(): Single<List<RoomMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cacheNowPlayingMovies(movies: List<RoomMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cacheUpcomingMovies(movies: List<RoomMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cacheTopRatedMovies(movies: List<RoomMovie>)

    @Query("DELETE FROM roommovie")
    fun clearCache()
}