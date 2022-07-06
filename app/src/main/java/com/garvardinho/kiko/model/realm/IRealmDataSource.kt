package com.garvardinho.kiko.model.realm

import com.garvardinho.kiko.model.MovieResultDTO

interface IRealmDataSource {

    fun putMovieIntoRealm(movie: MovieResultDTO)
    fun deleteMovieFromRealm(movie: MovieResultDTO)
    fun loadFavoriteMovies(): List<MovieResultDTO> {
        return listOf()
    }
}