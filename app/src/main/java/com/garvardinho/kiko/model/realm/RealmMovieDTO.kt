package com.garvardinho.kiko.model.realm

import com.garvardinho.kiko.model.retrofit.MovieDTO
import io.realm.RealmObject

open class RealmMovieDTO (
    var title: String? = null,
    var poster_path: String? = null,
    var release_date: String? = null,
    var vote_average: Double? = null,
    var isFavorite: Boolean? = null,
    var overview: String? = null,
) : RealmObject() {
    fun toMovieResultDTO() : MovieDTO {
        return MovieDTO(
            title ?: "New movie",
            poster_path,
            release_date,
            vote_average ?: 10.0,
            isFavorite ?: false,
            overview
        )
    }
}
