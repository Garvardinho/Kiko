package com.garvardinho.kiko.model

import io.realm.RealmObject

open class MovieResultDTOManaged (
    var title: String? = null,
    var poster_path: String? = null,
    var release_date: String? = null,
    var vote_average: Double? = null,
    var isFavorite: Boolean? = null,
    var overview: String? = null,
) : RealmObject() {
    fun toMovieResultDTO() : MovieResultDTO {
        return MovieResultDTO(
            title ?: "New movie",
            poster_path,
            release_date,
            vote_average ?: 10.0,
            isFavorite ?: false,
            overview
        )
    }
}
