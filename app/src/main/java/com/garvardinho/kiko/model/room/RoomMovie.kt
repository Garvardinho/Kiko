package com.garvardinho.kiko.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val NOW_PLAYING = 0
const val UPCOMING = 1
const val TOP_RATED = 2

@Entity
data class RoomMovie(
    @PrimaryKey val title: String = "New movie",
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "vote_average") val voteAverage: Double = 10.0,
    var isFavorite: Boolean = false,
    val overview: String? = null,
    val type: Int
)